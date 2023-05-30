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
  const role = this.authService.getRole()
  this.isAdmin = (role === 'ADMIN')
  this.isEngineer = (role === 'ENGINEER')
  this.isManager = (role === 'HR_MANAGER' || role === 'PROJECT_MANAGER')
  this.username = this.authService.getUsername()
}
}
