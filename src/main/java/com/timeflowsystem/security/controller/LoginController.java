package com.timeflowsystem.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @GetMapping(value = "/denied")
    public String displayLoginPage() {
        return "Access denied";
    }

    @GetMapping(value = "/success")
    public String returnSuccessMessage() {
        return "Logged with success!";
    }

    @GetMapping(value = "/requestLogin")
    public RedirectView redirectToFrontend() {
        return new RedirectView("http://localhost:3000/start"); // TODO Necessary to correct CORS error
    }
}
