package com.timeflowsystem.security.controller;

import com.timeflowsystem.security.constants.ApplicationConstants;
import com.timeflowsystem.security.dto.LoginRequestDto;
import com.timeflowsystem.security.dto.LoginResponseDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final Environment env;

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

    @GetMapping(value = "/logoutSuccessfully")
    public String returnLogoutSuccessfully() {
        return "Logout with success! (Message from backend)";
    }

    @GetMapping(value = "/invalidSession")
    public String returnInvalidSession() {
        return "Invalid session! (Message from backend)";
    }

    @GetMapping(value = "/expiredSession")
    public String returnExpiredSession() {
        return "Expired session! (Message from backend)";
    }

    @PostMapping("/apiLogin")
    public ResponseEntity<LoginResponseDto> apiLogin (@RequestBody LoginRequestDto loginRequest) {
        String jwt = "";
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(),
                loginRequest.password());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);

                // Bellow, authenticationResponse.isAuthenticated()) says if the user is authenticated
        if((null != env) && null != authenticationResponse && authenticationResponse.isAuthenticated()) {
            String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                    ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            jwt = Jwts.builder().issuer("Timeflow").subject("JWT Token")
                    .claim("username", authenticationResponse.getName())
                    .claim("authorities", authenticationResponse.getAuthorities().stream().map(
                            GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                    .issuedAt(new java.util.Date())
                    .expiration(new java.util.Date((new java.util.Date()).getTime() + 30000000))
                    .signWith(secretKey).compact();
        }
        return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstants.JWT_HEADER,jwt) // We are sending the
                                                                                            // token here in the HEADER
                .body(new LoginResponseDto(HttpStatus.OK.getReasonPhrase(), jwt)); // We are also sending the same token
                                                                                    // in the body here. In a production
                                                                                    // project, we can send it in just
                                                                                    // one place
    }

}
