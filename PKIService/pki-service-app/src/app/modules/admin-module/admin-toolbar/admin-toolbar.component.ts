import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'admin-toolbar',
  templateUrl: './admin-toolbar.component.html',
  styleUrls: ['./admin-toolbar.component.css']
})
export class AdminToolbarComponent {
  constructor(private router:Router, private authService: AuthenticationService) { }

  HomeClick(){
    this.router.navigate(['/admin-home']);
  }

  CreateCertificateClick(){
    this.router.navigate(['/add-certificate']);
  }

  ProjectsClick(){
    this.router.navigate(['/projects']);
  }

  EmployeesClick(){
    this.router.navigate(['/employees']);
  }

  EditProfileClick(){
    this.router.navigate(['/editProfile']);
  }
  
  RequestsClick(){
    this.router.navigate(['requests'])
  }

  LogoutClick(){
    this.authService.logout()
    this.router.navigate([''])
  }
}
