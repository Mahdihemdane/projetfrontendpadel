import { TerrainService } from "../../_services/terrain.service";
import { ReservationService } from "../../_services/reservation.service";
import { StorageService } from "../../_services/storage.service";
import { Component, OnInit } from '@angular/core';
import Chart from 'chart.js';
import {
  chartOptions,
  parseOptions,
  chartExample1,
  chartExample2
} from "../../variables/charts";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  public datasets: any;
  public data: any;
  public salesChart;
  public clicked: boolean = true;
  public clicked1: boolean = false;

  public terrains: any[] = [];
  public selectedTerrain: any = null;
  public terrainSlots: any[] = [];
  public isAdmin = false;

  constructor(
    private terrainService: TerrainService,
    private reservationService: ReservationService,
    private storageService: StorageService
  ) { }

  ngOnInit() {
    const user = this.storageService.getUser();
    // Robust check for ADMIN role
    this.isAdmin = user && user.roles && user.roles.some(r =>
      r.toUpperCase() === 'ADMIN' || r.toUpperCase() === 'ROLE_ADMIN'
    );

    // Always load terrains so everyone can see the courts on the dashboard
    this.loadTerrains();

    this.datasets = [
      [0, 20, 10, 30, 15, 40, 20, 60, 60],
      [0, 20, 5, 25, 10, 30, 15, 40, 40]
    ];
    this.data = this.datasets[0];


    if (this.isAdmin) {
      // Parse options only if admin
      parseOptions(Chart, chartOptions());

      var chartOrders = document.getElementById('chart-orders');
      if (chartOrders) {
        var ordersChart = new Chart(chartOrders, {
          type: 'bar',
          options: chartExample2.options,
          data: chartExample2.data
        });
      }

      var chartSales = document.getElementById('chart-sales');
      if (chartSales) {
        this.salesChart = new Chart(chartSales, {
          type: 'line',
          options: chartExample1.options,
          data: chartExample1.data
        });
      }
    }
  }


  public updateOptions() {
    this.salesChart.data.datasets[0].data = this.data;
    this.salesChart.update();
  }

  loadTerrains() {
    this.terrainService.getAllTerrains().subscribe(data => this.terrains = data);
  }

  viewSlots(terrain: any) {
    this.selectedTerrain = terrain;
    this.terrainService.getSlotsByTerrain(terrain.id).subscribe(data => {
      this.terrainSlots = data;
    });
  }

  bookSlot(slotId: number) {
    this.reservationService.createBooking(slotId).subscribe({
      next: () => {
        alert('Réservation effectuée avec succès !');
        this.viewSlots(this.selectedTerrain);
      },
      error: err => alert('Erreur: ' + (err.error.message || 'Le créneau est peut-être déjà pris.'))
    });
  }

}
