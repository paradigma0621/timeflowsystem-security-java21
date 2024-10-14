package com.timeflowsystem.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("!prod")
public class TimeflowUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    // private final PasswordEncoder passwordEncoder; //Removed password validation

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);  // Here, as was declared
                                        // that "class TimeflowUserDetailsService implements UserDetailsService"
                                        // it will execute the method loadUserByUsername from that class when called

       // if (passwordEncoder.matches(pwd, userDetails.getPassword())) { //Removed password validation

            return new UsernamePasswordAuthenticationToken(username, pwd, userDetails.getAuthorities());

       /* } else {
            throw new BadCredentialsException("Invalid password!");
        */
       }


    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
