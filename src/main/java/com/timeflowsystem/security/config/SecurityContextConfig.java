package com.timeflowsystem.security.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class SecurityContextConfig {

    @PostConstruct  //  This class was needed because setting the strategy in SecurityContextHolder
                    // directly within the controller class was not working
    public void init() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }
}