import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'admin-toolbar',
  templateUrl: './admin-toolbar.component.html',
  styleUrls: ['./admin-toolbar.component.css']
})
export class AdminToolbarComponent {
  constructor(private router:Router) { }

  HomeClick(){
    this.router.navigate(['/admin-home']);
  }

  CreateCertificateClick(){
    this.router.navigate(['/add-certificate']);
  }

  LogoutClick(){
    this.router.navigate(['/'])
  }
}
