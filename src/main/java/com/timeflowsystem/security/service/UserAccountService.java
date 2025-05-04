package com.timeflowsystem.security.service;

import com.timeflowsystem.security.model.UserAccount;
import com.timeflowsystem.security.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    // @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PreAuthorize("#username == authentication.principal")
    public List<UserAccount> findAllIfAllowedPreAuthorizeWhereUsernameIsTheUserLogged(String username) {
        return userAccountRepository.findAll();
    }
}
