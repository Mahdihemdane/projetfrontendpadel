package com.padel.reservation.controllers;

import com.padel.reservation.entity.Slot;
import com.padel.reservation.entity.Terrain;
import com.padel.reservation.services.TerrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TerrainController {
    @Autowired
    TerrainService terrainService;

    // Public: List Terrains
    @GetMapping("/public/terrains")
    public List<Terrain> getAllTerrains() {
        return terrainService.getAllTerrains();
    }

    // Public: Get Terrain by ID
    @GetMapping("/public/terrains/{id}")
    public ResponseEntity<Terrain> getTerrainById(@PathVariable Long id) {
        return terrainService.getTerrainById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Public: Get Slots for Terrain
    @GetMapping("/public/terrains/{id}/slots")
    public List<Slot> getSlotsByTerrain(@PathVariable Long id) {
        return terrainService.getSlotsByTerrain(id);
    }

    // Admin: Create Terrain
    @PostMapping("/admin/terrains")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Terrain createTerrain(@RequestBody Terrain terrain) {
        return terrainService.saveTerrain(terrain);
    }

    // Admin: Update Terrain
    @PutMapping("/admin/terrains/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Terrain> updateTerrain(@PathVariable Long id, @RequestBody Terrain terrainDetails) {
        return terrainService.getTerrainById(id)
                .map(terrain -> {
                    terrain.setName(terrainDetails.getName());
                    terrain.setDescription(terrainDetails.getDescription());
                    terrain.setLocation(terrainDetails.getLocation());
                    terrain.setPrice(terrainDetails.getPrice());
                    terrain.setState(terrainDetails.getState());
                    return ResponseEntity.ok(terrainService.saveTerrain(terrain));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Admin: Delete Terrain
    @DeleteMapping("/admin/terrains/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteTerrain(@PathVariable Long id) {
        terrainService.deleteTerrain(id);
        return ResponseEntity.ok().build();
    }

    // Admin: Add Slot
    @PostMapping("/admin/slots")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Slot createSlot(@RequestBody Slot slot) {
        return terrainService.saveSlot(slot);
    }

    // Admin: Delete Slot
    @DeleteMapping("/admin/slots/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteSlot(@PathVariable Long id) {
        terrainService.deleteSlot(id);
    }
}
