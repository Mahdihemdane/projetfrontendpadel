package com.padel.reservation.repository;

import com.padel.reservation.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByTerrainId(Long terrainId);

    List<Slot> findByIsBookedFalse();
}
