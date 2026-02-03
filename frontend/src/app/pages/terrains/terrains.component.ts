import { Component, OnInit } from '@angular/core';
import { TerrainService } from '../../_services/terrain.service';
import { StorageService } from '../../_services/storage.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-terrains',
    templateUrl: './terrains.component.html',
    styleUrls: ['./terrains.component.scss']
})
export class TerrainsComponent implements OnInit {

    terrains: any[] = [];
    form: any = {};
    isEditMode = false;
    currentTerrainId: number = null;
    errorMessage = '';

    // Slot Management
    selectedTerrain: any = null;
    terrainSlots: any[] = [];
    slotForm: any = {};
    showSlotManager = false;
    isSlotEditMode = false;
    currentSlotId: number = null;

    constructor(private terrainService: TerrainService, private storageService: StorageService, private router: Router) { }

    ngOnInit() {
        const user = this.storageService.getUser();
        if (!user || !user.roles || !user.roles.includes('ADMIN')) {
            this.router.navigate(['/dashboard']);
            return;
        }
        this.loadTerrains();
    }

    loadTerrains() {
        this.terrainService.getAllTerrains().subscribe(
            data => {
                this.terrains = data;
            },
            err => {
                console.error(err);
            }
        );
    }

    onSubmit() {
        if (this.isEditMode) {
            this.terrainService.updateTerrain(this.currentTerrainId, this.form).subscribe(
                data => {
                    this.resetForm();
                    this.loadTerrains();
                },
                err => {
                    this.errorMessage = err.error.message;
                }
            );
        } else {
            this.terrainService.createTerrain(this.form).subscribe(
                data => {
                    this.resetForm();
                    this.loadTerrains();
                    alert('Terrain ajouté avec succès');
                },
                err => {
                    this.errorMessage = err.error?.message || "Erreur";
                    alert('Erreur: ' + (err.error?.message || err.message));
                }
            );
        }
    }

    editTerrain(terrain: any) {
        this.isEditMode = true;
        this.currentTerrainId = terrain.id;
        this.form = { ...terrain };
    }

    deleteTerrain(id: number) {
        if (confirm("Are you sure?")) {
            this.terrainService.deleteTerrain(id).subscribe(
                data => {
                    this.loadTerrains();
                },
                err => {
                    console.error(err);
                    alert("Erreur lors de la suppression");
                }
            );
        }
    }

    resetForm() {
        this.isEditMode = false;
        this.currentTerrainId = null;
        this.form = {};
        this.errorMessage = '';
    }

    // Slot Methods
    manageSlots(terrain: any) {
        this.selectedTerrain = terrain;
        this.showSlotManager = true;
        this.loadSlots(terrain.id);
        this.slotForm = { terrainId: terrain.id };
    }

    loadSlots(terrainId: number) {
        this.terrainService.getSlotsByTerrain(terrainId).subscribe({
            next: data => this.terrainSlots = data,
            error: err => console.error(err)
        });
    }

    onSlotSubmit() {
        const payload = {
            ...this.slotForm,
            terrain: { id: this.selectedTerrain.id }
        };

        if (this.isSlotEditMode) {
            this.terrainService.updateSlot(this.currentSlotId, payload).subscribe({
                next: data => {
                    this.loadSlots(this.selectedTerrain.id);
                    this.resetSlotForm();
                },
                error: err => alert('Erreur lors de la modification du créneau')
            });
        } else {
            this.terrainService.createSlot(payload).subscribe({
                next: data => {
                    this.loadSlots(this.selectedTerrain.id);
                    this.resetSlotForm();
                },
                error: err => alert('Erreur lors de l\'ajout du créneau: ' + (err.error?.message || err.message))
            });
        }
    }

    editSlot(slot: any) {
        this.isSlotEditMode = true;
        this.currentSlotId = slot.id;
        this.slotForm = {
            date: slot.date,
            startTime: slot.startTime,
            endTime: slot.endTime,
            terrainId: this.selectedTerrain.id
        };
    }

    deleteSlot(id: number) {
        if (confirm("Supprimer ce créneau ?")) {
            this.terrainService.deleteSlot(id).subscribe({
                next: () => this.loadSlots(this.selectedTerrain.id),
                error: err => console.error(err)
            });
        }
    }

    resetSlotForm() {
        this.isSlotEditMode = false;
        this.currentSlotId = null;
        this.slotForm = { terrainId: this.selectedTerrain.id };
    }

    closeSlotManager() {
        this.showSlotManager = false;
        this.selectedTerrain = null;
        this.resetSlotForm();
    }
}
