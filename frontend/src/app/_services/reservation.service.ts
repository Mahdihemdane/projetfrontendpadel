import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8081/api/';

@Injectable({
    providedIn: 'root'
})
export class ReservationService {
    constructor(private http: HttpClient) { }

    getMyReservations(): Observable<any> {
        return this.http.get(API_URL + 'bookings/my');
    }

    getAllReservations(): Observable<any> {
        return this.http.get(API_URL + 'admin/bookings');
    }

    createBooking(slotId: number): Observable<any> {
        return this.http.post(API_URL + 'bookings', { slotId });
    }

    updateStatus(id: number, status: string): Observable<any> {
        return this.http.put(API_URL + 'admin/bookings/' + id + '/status', { status });
    }
}
