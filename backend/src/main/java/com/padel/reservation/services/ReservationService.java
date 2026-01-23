package com.padel.reservation.services;

import com.padel.reservation.entity.*;
import com.padel.reservation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    SlotRepository slotRepository;

    @Autowired
    UserRepository userRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getUserReservations(Long userId) {
        return reservationRepository.findByClientId(userId);
    }

    @Transactional
    public Reservation createReservation(Long userId, Long slotId) throws Exception {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new Exception("Slot not found"));

        if (slot.isBooked()) {
            throw new Exception("Slot already booked");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));

        Reservation reservation = new Reservation();
        reservation.setClient(user);
        reservation.setSlot(slot);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.PENDING); // Or CONFIRMED immediately

        slot.setBooked(true);
        slotRepository.save(slot);

        return reservationRepository.save(reservation);
    }

    public Reservation updateStatus(Long reservationId, ReservationStatus status) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation != null) {
            reservation.setStatus(status);
            if (status == ReservationStatus.CANCELLED || status == ReservationStatus.REJECTED) {
                Slot slot = reservation.getSlot();
                slot.setBooked(false);
                slotRepository.save(slot);
            }
            return reservationRepository.save(reservation);
        }
        return null;
    }
}
