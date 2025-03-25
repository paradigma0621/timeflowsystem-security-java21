package com.timeflowsystem.security.controller;

import com.timeflowsystem.security.model.UserAccount;
import com.timeflowsystem.security.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user-accounts")
public class UserAccountController {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/getUserAccount")
    public String findUser() {
        return "logged!!";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserAccount userAccount) {
        try {
            String hashPwd = passwordEncoder.encode(userAccount.getPwd());
            userAccount.setPwd(hashPwd);
            UserAccount savedUserAccount = userAccountRepository.save(userAccount);

            if(savedUserAccount.getId()>0) {
                return ResponseEntity.status(HttpStatus.CREATED).
                        body("Given user details are successfully registered");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                        body("User registration failed");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("An exception occurred: " + ex.getMessage());
        }
    }

    @GetMapping("/all")
    public List<UserAccount> findAll() {
        return List.of(userAccountRepository.findById(1L).orElse(null));
    }

    @GetMapping("/allWithProtectionPreAuthorize")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserAccount> findAllIfAllowedPreAuthorize() {
        return userAccountRepository.findAll();
    }

    @GetMapping("/allWithProtectionPostAuthorize")
    @PostAuthorize("hasRole('ADMIN')")
    public List<UserAccount> findAllIfAllowedPostAuthorize() {
        return userAccountRepository.findAll();
    }

    @GetMapping("/find-string-authenticated-by-security-context-instance")
    public String findAuthenticationBySecurityContextInstance() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String strategy = SecurityContextHolder.getContextHolderStrategy().getClass().getName();
        return authentication.toString() + " - with strategy: " + strategy;
    }

    @GetMapping("/find-string-authenticated-by-method-parameter")
    public String findUserAuthenticatedByMethodParameter(Authentication authentication) {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
        String strategy = SecurityContextHolder.getContextHolderStrategy().getClass().getName();
        return authentication.toString() + " - with strategy: " + strategy;
    }
}
