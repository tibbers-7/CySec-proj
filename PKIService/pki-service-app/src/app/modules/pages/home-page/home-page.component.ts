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
username = ''

public constructor(private authService : AuthenticationService){}

ngOnInit(): void {
  const userType = this.authService.getRole()
  this.isAdmin = (userType === 'ADMIN')
  this.isEngineer = (userType === 'ENGINEER')
  this.isManager = (userType === 'HR_MANAGER' || userType === 'PROJECT_MANAGER')
  this.username = this.authService.getUsername()
}
}
