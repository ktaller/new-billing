package com.billing_ktaller.water_billing.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.billing_ktaller.water_billing.dto.AuthenticationRequestDao;
import com.billing_ktaller.water_billing.dto.PingRequestDao;
import com.billing_ktaller.water_billing.dto.RegistrationRequestDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegistrationRequestDao requestDao) {
        User user = User.builder()
                .firstName(requestDao.getFirstName())
                .lastName(requestDao.getLastName())
                .email(requestDao.getEmail())
                .password(passwordEncoder.encode(requestDao.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequestDao requestDao) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(requestDao.getEmail(), requestDao.getPassword()));

        User user = userRepository.findByEmail(requestDao.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public String logout() {
        // TODO: look at this later
        return "Logged out";
    }

    public PingResponse ping(PingRequestDao requestDao) {
        // get user from token
        String userName = jwtService.extractUsername(requestDao.getToken());
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return PingResponse.builder()
                .message("Pong")
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

}
