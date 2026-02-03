package com.padel.reservation.services;

import com.padel.reservation.entity.Slot;
import com.padel.reservation.entity.Terrain;
import com.padel.reservation.repository.SlotRepository;
import com.padel.reservation.repository.TerrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TerrainService {
    @Autowired
    TerrainRepository terrainRepository;

    @Autowired
    SlotRepository slotRepository;

    public List<Terrain> getAllTerrains() {
        return terrainRepository.findAll();
    }

    public Optional<Terrain> getTerrainById(Long id) {
        return terrainRepository.findById(id);
    }

    public Terrain saveTerrain(Terrain terrain) {
        return terrainRepository.save(terrain);
    }

    public void deleteTerrain(Long id) {
        // Also delete slots associated? Or cascade. For now simple delete.
        terrainRepository.deleteById(id);
    }

    // Slot Management
    public Slot saveSlot(Slot slot) {
        return slotRepository.save(slot);
    }

    public Optional<Slot> getSlotById(Long id) {
        return slotRepository.findById(id);
    }

    public Slot updateSlot(Long id, Slot slotDetails) {
        return slotRepository.findById(id)
                .map(slot -> {
                    slot.setDate(slotDetails.getDate());
                    slot.setStartTime(slotDetails.getStartTime());
                    slot.setEndTime(slotDetails.getEndTime());
                    if (slotDetails.getTerrain() != null) {
                        slot.setTerrain(slotDetails.getTerrain());
                    }
                    return slotRepository.save(slot);
                })
                .orElseThrow(() -> new RuntimeException("Slot not found with id: " + id));
    }

    public List<Slot> getSlotsByTerrain(Long terrainId) {
        return slotRepository.findByTerrainId(terrainId);
    }

    public void deleteSlot(Long id) {
        slotRepository.deleteById(id);
    }
}
