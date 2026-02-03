import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8081/api/test/';
const API_URL_USERS = 'http://localhost:8081/api/users/';
// Or /api/public/

@Injectable({
    providedIn: 'root'
})
export class UserService {
    constructor(private http: HttpClient) { }

    getPublicContent(): Observable<any> {
        return this.http.get(API_URL + 'all', { responseType: 'text' });
    }

    getUserBoard(): Observable<any> {
        return this.http.get(API_URL + 'user', { responseType: 'text' });
    }

    getModeratorBoard(): Observable<any> {
        return this.http.get(API_URL + 'mod', { responseType: 'text' });
    }

    getAdminBoard(): Observable<any> {
        return this.http.get(API_URL + 'admin', { responseType: 'text' });
    }

    updateProfile(id: number, data: any): Observable<any> {
        return this.http.put(API_URL_USERS + 'profile/' + id, data);
    }
}
