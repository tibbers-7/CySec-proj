import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-hr-manager-toolbar',
  templateUrl: './hr-manager-toolbar.component.html',
  styleUrls: ['./hr-manager-toolbar.component.css']
})
export class HrManagerToolbarComponent {
  constructor(private router:Router) { }
 
  EmployeesClick(){
    //this.router.navigate(['hrmanager/employees']);
  }

  EditProfileClick(){
    this.router.navigate(['hrmanager/editProfile']);
  }

  LogoutClick(){
    this.router.navigate(['/'])
  }

}
