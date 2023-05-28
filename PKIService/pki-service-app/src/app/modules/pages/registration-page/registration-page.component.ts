import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Address } from 'src/app/model/address';
import { RegistrationFormData } from 'src/app/model/registrationFormData';
import { RegistrationRequestData } from 'src/app/model/registrationRequestData';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent {

registrationData = new RegistrationFormData()
registratonRequest = new RegistrationRequestData()
passwordTooltip = "Password has to have at least one letter, one number and 8 characters at least"
usernameTooltip = "Username should be a valid email address"

public constructor(private authService : AuthenticationService, private toast : ToastrService, private router: Router){}
submitRegistrationRequest() {
  if(this.validityChecked()){
    this.createRequest()
    console.log(this.registratonRequest)
    this.authService.sendRegistrationRequest(this.registratonRequest).subscribe(res => {
      this.toast.success("Successful registration!")
    })
  }
  }

  validityChecked(){
    if(this.fieldsAreEmpty(this.registrationData)){
      this.toast.error("All fields have to be filled!")
      return false
    }
    else if(this.registrationData.password !== this.registrationData.confirmPassword){
      this.toast.error("Passwords have to be matching!")
      return false
    }
    else if(!this.registrationData.password.match(/^(?=.*[a-zA-Z])(?=.*[0-9])/)){
      this.toast.error("Password has to contain at least one letter and one number, and 8 characters total!")
      return false
    }else if(!this.registrationData.username.match(/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i)){
      this.toast.error("Username has to be a valid email address!")
      return false
    }
    return true
  }

  createRequest(){
    this.registratonRequest.address = this.createAddress()
    this.registratonRequest.name = this.registrationData.name
    this.registratonRequest.surname = this.registrationData.surname
    this.registratonRequest.username = this.registrationData.username
    this.registratonRequest.password = this.registrationData.password
    this.registratonRequest.phoneNumber = this.registrationData.phoneNumber
    this.registratonRequest.role = this.registrationData.role
    this.registratonRequest.workTitle = this.registrationData.workTitle
  }

  createAddress(){
    let address = new Address()
    address.country = this.registrationData.country
    address.city = this.registrationData.city
    address.streetAddress = this.registrationData.streetAddress
    return address
  }

  fieldsAreEmpty(object: Object) { 
    return Object.values(object).some(
        value => {
        if (value === null || value === '')  return true
        return false
      })
  }

  clickedOnLogin(){
    this.router.navigate([''])
  }
}
