package com.padel.reservation.controllers;

import com.padel.reservation.entity.User;
import com.padel.reservation.payload.response.MessageResponse;
import com.padel.reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PutMapping("/profile/{id}")
    @PreAuthorize("hasAuthority('MEMBER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            // Email update is usually restricted, but for this project we'll allow it or
            // keep it read-only in UI
            user.setEmail(userDetails.getEmail());

            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Profile updated successfully!"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
