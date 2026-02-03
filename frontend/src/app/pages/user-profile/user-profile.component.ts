import { Component, OnInit } from '@angular/core';
import { StorageService } from 'src/app/_services/storage.service';
import { UserService } from 'src/app/_services/user.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  user: any;
  isEditMode = false;

  constructor(
    private storageService: StorageService,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.user = this.storageService.getUser();
  }

  toggleEdit() {
    this.isEditMode = !this.isEditMode;
  }

  updateProfile() {
    this.userService.updateProfile(this.user.id, this.user).subscribe({
      next: data => {
        console.log(data);
        this.storageService.saveUser(this.user);
        this.isEditMode = false;
        alert('Profil mis à jour avec succès !');
      },
      error: err => {
        console.error(err);
        alert('Erreur lors de la mise à jour du profil.');
      }
    });
  }

}
