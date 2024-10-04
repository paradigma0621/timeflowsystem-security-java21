package com.timeflowsystem.security.config;

import com.timeflowsystem.security.model.UserAccount;
import com.timeflowsystem.security.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeflowUserDetailsService implements UserDetailsService {

        private final UserAccountRepository userAccountRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            UserAccount userAccount = userAccountRepository.findByEmail(username).orElseThrow(() -> new
                    UsernameNotFoundException("User details not found for the user: " + username));
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userAccount.getRole()));
            return new org.springframework.security.core.userdetails.User(userAccount.getEmail(), userAccount.getPwd(), authorities);
        }

}
