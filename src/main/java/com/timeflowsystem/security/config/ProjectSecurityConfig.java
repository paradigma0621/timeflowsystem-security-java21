package com.timeflowsystem.security.config;

import com.timeflowsystem.security.exceptionhandling.CustomAccessDeniedHandler;
import com.timeflowsystem.security.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import com.timeflowsystem.security.handler.CustomAuthenticationFailureHandler;
import com.timeflowsystem.security.handler.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class ProjectSecurityConfig {

    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Using the below config, the user can be redirected to the given URL when an invalid session is detected,
                // by, for example: Session Timeout
                .sessionManagement(sessionConfig -> sessionConfig.invalidSessionUrl("/invalidSession")

                // We can control the maximum sessions allowed for a user and what should happen in the case of
                // invalid session due to too many sessions for the current user
                // The following configurations restrict each user to a maximum of 3 active sessions.
                // If a user attempts to log in from a fourth device, the system will block the new session and redirect
                // the user to the "/expiredSession" page
                                         .maximumSessions(3).maxSessionsPreventsLogin(true).expiredUrl("/expiredSession"))

                .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure()) // Only HTTP (deny HTTPS requests)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/customers/**", "/invalidSession", "/expiredSession", "/login/**").permitAll()
                            .requestMatchers("/user-accounts/**").authenticated()
                )
                .formLogin(flc -> flc.loginPage("/login/requestLogin") // Endpoint executed or page to be loaded when
                                                                       // requires login
                                .usernameParameter("userid") // Field username to be received from frontend
                                .passwordParameter("pwd") // Field password to be received from frontend
                                .loginProcessingUrl("/backendLoginEndpoint") // Tells to frontend to send to this endpoint
                                                                             // the login data (userid, pwd)

                                //.defaultSuccessUrl("/login/success") // Endpoint executed or page to be loaded when login
                                                                       // successful
                                //.failureUrl("/login/denied")); // Endpoint executed or page to be
                                                                 // loaded when login failure

                                .successHandler(authenticationSuccessHandler)
                                .failureHandler(authenticationFailureHandler));

        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));

        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
        // In the example above, when a 403 error occurs during browser access, a JSON response is shown.
        // Using the example below, we can specify a URL to redirect the user to when they attempt to access a page without
        // the necessary permissions.
        // http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()).accessDeniedPage("/denied"));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
