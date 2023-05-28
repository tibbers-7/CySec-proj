import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { RegularLogInRequestData } from 'src/app/model/regularLogInRequestData';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {

  isPasswordless = false
  logInData : RegularLogInRequestData = new RegularLogInRequestData()
  checkboxTooltip = "If you choose this, an email will be sent for you to log in"
  constructor(private router: Router, private toast: ToastrService, private authService: AuthenticationService) { }

  signIn(){
    if(!this.validityCheck()) return
    if(this.isPasswordless) {
      this.authService.passwordlessLoginRequest(this.logInData.username) 
    }else {
      this.authService.logInUserwithCredentials(this.logInData).subscribe(res =>{
      this.authService.setSession(res)
    })
  }
  }

  validityCheck(){
    if(this.logInData.username === ''){
      this.toast.error("Username has to be filled")
      return false
    } 
    if(this.logInData.password === '' && !this.isPasswordless){
      this.toast.error("The password field is missing!")
      return false
    }
    return true
  }

  clickedOnRegistration(){
    this.router.navigate(['registration'])
  }
}
