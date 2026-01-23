import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = '/api/';

@Injectable({
    providedIn: 'root'
})
export class TerrainService {
    constructor(private http: HttpClient) { }

    getAllTerrains(): Observable<any> {
        return this.http.get(API_URL + 'public/terrains');
    }

    getTerrainById(id: number): Observable<any> {
        return this.http.get(API_URL + 'public/terrains/' + id);
    }

    createTerrain(data: any): Observable<any> {
        return this.http.post(API_URL + 'admin/terrains', data);
    }

    updateTerrain(id: number, data: any): Observable<any> {
        return this.http.put(API_URL + 'admin/terrains/' + id, data);
    }

    deleteTerrain(id: number): Observable<any> {
        return this.http.delete(API_URL + 'admin/terrains/' + id);
    }

    getSlots(terrainId: number): Observable<any> {
        return this.http.get(API_URL + 'public/terrains/' + terrainId + '/slots');
    }

    createSlot(data: any): Observable<any> {
        return this.http.post(API_URL + 'admin/slots', data);
    }
}
