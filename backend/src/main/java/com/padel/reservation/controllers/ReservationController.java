package com.padel.reservation.controllers;

import com.padel.reservation.entity.Reservation;
import com.padel.reservation.entity.ReservationStatus;
import com.padel.reservation.security.services.UserDetailsImpl;
import com.padel.reservation.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    // Member: Create Reservation
    @PostMapping("/bookings")
    @PreAuthorize("hasAuthority('MEMBER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> createReservation(@AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody Map<String, Long> payload) {
        try {
            Long slotId = payload.get("slotId");
            Reservation reservation = reservationService.createReservation(userDetails.getId(), slotId);
            return ResponseEntity.ok(reservation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Member: My Reservations
    @GetMapping("/bookings/my")
    @PreAuthorize("hasAuthority('MEMBER') or hasAuthority('ADMIN')")
    public List<Reservation> getMyReservations(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reservationService.getUserReservations(userDetails.getId());
    }

    // Admin: All Reservations
    @GetMapping("/admin/bookings")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    // Admin: Update Status
    @PutMapping("/admin/bookings/{id}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Reservation> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String statusStr = payload.get("status");
        try {
            ReservationStatus status = ReservationStatus.valueOf(statusStr);
            Reservation updated = reservationService.updateStatus(id, status);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
