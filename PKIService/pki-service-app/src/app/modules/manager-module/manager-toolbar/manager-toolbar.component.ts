import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-manager-toolbar',
  templateUrl: './manager-toolbar.component.html',
  styleUrls: ['./manager-toolbar.component.css']
})
export class ManagerToolbarComponent {
  constructor(private router:Router) { }
 
  ProjectsClick(){
    this.router.navigate(['manager/projects']);
  }

  EditProfileClick(){
    this.router.navigate(['manager/editProfile']);
  }


  LogoutClick(){
    this.router.navigate(['/'])
  }
}
