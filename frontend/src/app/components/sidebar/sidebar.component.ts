import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}
export const ROUTES: RouteInfo[] = [
  { path: '/dashboard', title: 'Dashboard', icon: 'ni-tv-2 text-primary', class: '' },
  { path: '/terrains', title: 'Gestion Terrains', icon: 'ni-building text-red', class: 'admin-only' },
  { path: '/reservations', title: 'Gestion Réservations', icon: 'ni-bullet-list-67 text-info', class: 'admin-only' },
  { path: '/reservations', title: 'Mes Réservations', icon: 'ni-calendar-grid-58 text-info', class: 'member-only' },
  { path: '/login', title: 'Login', icon: 'ni-key-25 text-info', class: '' },
  { path: '/register', title: 'Register', icon: 'ni-circle-08 text-pink', class: '' }
];

import { StorageService } from '../../_services/storage.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

  public menuItems: any[];
  public isCollapsed = true;

  constructor(private router: Router, private storageService: StorageService) { }

  ngOnInit() {
    const user = this.storageService.getUser();
    const roles = (user && user.roles) ? user.roles : [];
    const isAdmin = roles.includes('ADMIN');

    this.menuItems = ROUTES.filter(menuItem => {
      // Hide Login and Register from sidebar if logged in
      if (user && user.roles && (menuItem.path === '/login' || menuItem.path === '/register')) {
        return false;
      }

      // Admin only routes
      if (menuItem.class === 'admin-only') {
        return isAdmin;
      }

      // Member only routes
      if (menuItem.class === 'member-only') {
        return !isAdmin; // Members see this, Admins don't
      }

      return true;
    });

    this.router.events.subscribe((event) => {
      this.isCollapsed = true;
    });
  }

  logout() {
    this.storageService.signOut();
    this.router.navigate(['/login']);
  }
}
