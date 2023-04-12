import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LogInRequestData } from 'src/app/model/logInRequestData';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {

  logInData : LogInRequestData = new LogInRequestData()
  constructor(private router: Router) { }

  signIn(){
    this.router.navigate(['/admin-home'])
  }
}
