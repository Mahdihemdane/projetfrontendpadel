import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_PUBLIC = 'http://localhost:8081/api/public';
const API_ADMIN = 'http://localhost:8081/api/admin';

@Injectable({
    providedIn: 'root'
})
export class TerrainService {
    constructor(private http: HttpClient) { }

    getAllTerrains(): Observable<any> {
        return this.http.get(`${API_PUBLIC}/terrains`);
    }

    getTerrainById(id: number): Observable<any> {
        return this.http.get(`${API_PUBLIC}/terrains/${id}`);
    }

    createTerrain(data: any): Observable<any> {
        return this.http.post(`${API_ADMIN}/terrains`, data);
    }

    updateTerrain(id: number, data: any): Observable<any> {
        return this.http.put(`${API_ADMIN}/terrains/${id}`, data);
    }

    deleteTerrain(id: number): Observable<any> {
        return this.http.delete(`${API_ADMIN}/terrains/${id}`);
    }

    getSlotsByTerrain(terrainId: number): Observable<any> {
        return this.http.get(`${API_PUBLIC}/terrains/${terrainId}/slots`);
    }

    createSlot(slot: any): Observable<any> {
        return this.http.post(`${API_ADMIN}/slots`, slot);
    }

    updateSlot(id: number, slot: any): Observable<any> {
        return this.http.put(`${API_ADMIN}/slots/${id}`, slot);
    }

    deleteSlot(slotId: number): Observable<any> {
        return this.http.delete(`${API_ADMIN}/slots/${slotId}`);
    }
}
