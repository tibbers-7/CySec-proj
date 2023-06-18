import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit{
isAdmin = false
isManager = false
isEngineer = false
isHrManager = false
username = ''

public constructor(private authService : AuthenticationService){}

ngOnInit(): void {
  const userType = this.authService.getRole()
  this.isAdmin = userType === 'ROLE_ADMIN'
  this.isEngineer = userType === 'ROLE_ENGINEER'
  this.isManager = userType === 'ROLE_PROJECT_MANAGER'
  this.isHrManager = userType === 'ROLE_HR_MANAGER'
  this.username = this.authService.getUsername()
}
}
