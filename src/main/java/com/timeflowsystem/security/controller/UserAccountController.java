package com.timeflowsystem.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-accounts")
public class UserAccountController {

    @GetMapping("/getUserAccount")
    public String findUser() {
        return "logged!!";
    }
}
