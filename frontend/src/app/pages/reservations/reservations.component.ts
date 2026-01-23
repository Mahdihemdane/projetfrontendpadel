import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../../_services/reservation.service';
import { StorageService } from '../../_services/storage.service';

@Component({
    selector: 'app-reservations',
    templateUrl: './reservations.component.html',
    styleUrls: ['./reservations.component.scss']
})
export class ReservationsComponent implements OnInit {

    reservations: any[] = [];
    isAdmin = false;
    errorMessage = '';

    constructor(private reservationService: ReservationService, private storageService: StorageService) { }

    ngOnInit() {
        const user = this.storageService.getUser();
        if (user && user.roles.includes('ADMIN')) {
            this.isAdmin = true;
            this.loadAllReservations();
        } else {
            this.isAdmin = false;
            this.loadMyReservations();
        }
    }

    loadAllReservations() {
        this.reservationService.getAllReservations().subscribe(
            data => {
                this.reservations = data;
            },
            err => {
                console.error(err);
            }
        );
    }

    loadMyReservations() {
        this.reservationService.getMyReservations().subscribe(
            data => {
                this.reservations = data;
            },
            err => {
                console.error(err);
            }
        );
    }

    updateStatus(id: number, status: string) {
        if (confirm("Confirmer le changement de statut?")) {
            this.reservationService.updateStatus(id, status).subscribe(
                data => {
                    this.loadAllReservations();
                },
                err => {
                    console.error(err);
                }
            );
        }
    }
}
