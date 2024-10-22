package com.timeflowsystem.security.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j // From Lombok Library
public class AuthenticationEvents {

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent successEvent) {
        log.info("Login successful for the user (AuthenticationSuccessEvent) : {}", successEvent.getAuthentication().getName());
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failureEvent) {
        log.error("Login failed for the user (AbstractAuthenticationFailureEvent) : {} due to : {}", failureEvent.getAuthentication().getName(),
                failureEvent.getException().getMessage());
    }

}
