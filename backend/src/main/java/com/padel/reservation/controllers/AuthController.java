package com.padel.reservation.controllers;

import com.padel.reservation.entity.Role;
import com.padel.reservation.entity.User;
import com.padel.reservation.payload.request.LoginRequest;
import com.padel.reservation.payload.request.SignupRequest;
import com.padel.reservation.payload.response.JwtResponse;
import com.padel.reservation.payload.response.MessageResponse;
import com.padel.reservation.repository.UserRepository;
import com.padel.reservation.security.jwt.JwtUtils;
import com.padel.reservation.security.services.UserDetailsImpl;
import com.padel.reservation.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    EmailService emailService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());

        String rawPassword = signUpRequest.getPassword();
        if (rawPassword == null || rawPassword.isEmpty()) {
            // Generate a random 8-character password
            rawPassword = java.util.UUID.randomUUID().toString().substring(0, 8);
        }
        user.setPassword(encoder.encode(rawPassword));

        Set<String> strRoles = signUpRequest.getRole();
        Role role = Role.MEMBER;

        if (strRoles != null && strRoles.contains("admin")) {
            role = Role.ADMIN;
        }

        user.setRole(role);
        userRepository.save(user);

        // Send registration email
        try {
            emailService.sendRegistrationEmail(user.getEmail(), user.getFirstName(), rawPassword);
        } catch (Exception e) {
            // Log the error but don't fail the registration
            System.err.println("Failed to send registration email: " + e.getMessage());
        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
