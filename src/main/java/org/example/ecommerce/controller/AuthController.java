package org.example.ecommerce.controller;


import lombok.RequiredArgsConstructor;
import org.example.ecommerce.dto.AuthRequest;
import org.example.ecommerce.dto.RegisterRequest;
import org.example.ecommerce.dto.TokenResponse;
import org.example.ecommerce.enums.Role;
import org.example.ecommerce.jwt.JwtService;
import org.example.ecommerce.model.User;
import org.example.ecommerce.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER);
        return ResponseEntity.ok("Ro'yxatdan o'tildi");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException("Foydalanuvchi topilmadi!"));

        String token = jwtService.generateToken(
               new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                       List.of(new SimpleGrantedAuthority(user.getRole().name()))
               )
        );
        return ResponseEntity.ok(new TokenResponse(token));
    }
}

