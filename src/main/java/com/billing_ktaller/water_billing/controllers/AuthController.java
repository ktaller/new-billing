package com.billing_ktaller.water_billing.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.billing_ktaller.water_billing.auth.AuthenticationResponse;
import com.billing_ktaller.water_billing.auth.AuthenticationService;
import com.billing_ktaller.water_billing.auth.PingResponse;
import com.billing_ktaller.water_billing.dto.AuthenticationRequestDao;
import com.billing_ktaller.water_billing.dto.PingRequestDao;
import com.billing_ktaller.water_billing.dto.RegistrationRequestDao;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authService;

    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequestDao requestDao) {
        return ResponseEntity.ok(authService.register(requestDao));
    }

    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequestDao requestDao) {
        return ResponseEntity.ok(authService.authenticate(requestDao));
    }

    @PostMapping("logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok(authService.logout());
    }

    @PostMapping("ping")
    public ResponseEntity<PingResponse> ping(@RequestBody PingRequestDao requestDao) {
        return ResponseEntity.ok(authService.ping(requestDao));
    }
}
