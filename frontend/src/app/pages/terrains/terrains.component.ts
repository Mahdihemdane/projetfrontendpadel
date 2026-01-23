import { Component, OnInit } from '@angular/core';
import { TerrainService } from '../../_services/terrain.service';

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

    constructor(private terrainService: TerrainService) { }

    ngOnInit() {
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
                },
                err => {
                    this.errorMessage = err.error.message;
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
}
