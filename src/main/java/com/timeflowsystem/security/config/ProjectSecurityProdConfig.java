package com.timeflowsystem.security.config;

import com.timeflowsystem.security.exceptionhandling.CustomAccessDeniedHandler;
import com.timeflowsystem.security.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import com.timeflowsystem.security.filter.AuthoritiesLoggingAtFilter;
import com.timeflowsystem.security.filter.JWTTokenGeneratorFilter;
import com.timeflowsystem.security.filter.JWTTokenValidatorFilter;
import com.timeflowsystem.security.filter.RequestValidationBeforeFilter;
import com.timeflowsystem.security.handler.CustomAuthenticationFailureHandler;
import com.timeflowsystem.security.handler.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@Profile("prod")
@RequiredArgsConstructor
public class ProjectSecurityProdConfig {

    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Using the below config, the user can be redirected to the given URL when an invalid session is detected,
                // by, for example: Session Timeout
                // .sessionManagement(sessionConfig -> sessionConfig.invalidSessionUrl("/login/invalidSession")

                // We can control the maximum sessions allowed for a user and what should happen in the case of
                // invalid session due to too many sessions for the current user
                // The following configuration restricts each user to a single active session.
                // If a user attempts to log in from another device, the system will block the new session and redirect
                // the user to the "/login/expiredSession" page.
                //                         .maximumSessions(1).maxSessionsPreventsLogin(true).expiredUrl("/login/expiredSession")
                //)

                //.requiresChannel(rcc -> rcc.anyRequest().requiresSecure()) // Only HTTPS (deny HTTP requests)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class) // The filter
                                // RequestValidationBeforeFilter is going to be executed before the BasicAuthenticationFilter
                .addFilterAfter(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class) // The filter
                                // AuthoritiesLoggingAtFilter is going to be executed after the BasicAuthenticationFilter
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class) // The filter
                                 // JWTTokenGeneratorFilter is going to be executed after the BasicAuthenticationFilter
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class) // The filter
                                // JWTTokenValidatorFilter is going to be executed before the BasicAuthenticationFilter
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/user").authenticated()
                        //.requestMatchers("/user-accounts/**").hasAnyAuthority("USERACCOUNTACTIONS", "CUSTOMERACTIONS")
                        //.requestMatchers("/user-accounts/**").hasAnyAuthority("CUSTOMERACTIONS")
                        //.requestMatchers("/user-accounts/**").hasAnyAuthority("USERACCOUNTACTIONS")
                        .requestMatchers("/user-accounts/**").hasAnyRole("USER", "ADMIN")
                        //.requestMatchers("/user-accounts/**").hasRole("USER")
                        //.requestMatchers("/user-accounts/**").hasRole("USER")
                        .requestMatchers("/login/**").permitAll()
                )
                .formLogin(flc -> flc.loginPage("/login/requestLogin") // Endpoint executed or page to be loaded when
                                                                       // requires login
                                .usernameParameter("userid") // Field username to be received from frontend
                                .passwordParameter("pwd") // Field password to be received from frontend
                                .loginProcessingUrl("/backendLoginEndpoint") // Tells to frontend to send to this endpoint
                                                                             // the login data (userid, pwd)
                                //.defaultSuccessUrl("/login/success") // Endpoint executed or page to be loaded when login
                                                                       // successful
                                //.failureUrl("/login/denied") // Endpoint executed or page to be
                                                               // loaded when login failure

                                .successHandler(authenticationSuccessHandler)   // Alternative approach for handling
                                                                                // successful logins
                                .failureHandler(authenticationFailureHandler))  // Alternative approach for handling
                                                                                // failure logins
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutUrl("/logout") // Can be omitted, as the default value is "/logout"
                        .logoutSuccessUrl("/login/logoutSuccessfully")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID"));

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
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        TimeflowProdUsernamePwdAuthenticationProvider authenticationProvider =
                new TimeflowProdUsernamePwdAuthenticationProvider(userDetailsService, passwordEncoder);
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
