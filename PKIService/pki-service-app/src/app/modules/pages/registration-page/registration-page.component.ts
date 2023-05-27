import { Component } from '@angular/core';
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

selectedRole = ""
registrationData = new RegistrationFormData()
registratonRequest = new RegistrationRequestData()
passwordTooltip = "Password has to have at least one letter, one number and 8 characters at least"
usernameTooltip = "Username should be a valid email address"

public constructor(private authService : AuthenticationService, private toast : ToastrService){}
submitRegistrationRequest() {
  if(this.validityChecked()){
    this.createRequest()
    this.authService.sendRegistrationRequest(this.registratonRequest).subscribe(res => {
      console.log(res)
    })
  }
  }

  validityChecked(){
    return this.fieldsAreEmpty(this.registrationData) && (this.registrationData.password === this.registrationData.confirmPassword) && (this.selectedRole !=="")
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
    address.streetAddresss = this.registrationData.streetAddress
    return address
  }

  fieldsAreEmpty(object: Object) { 
    return Object.values(object).some(
        value => {
        if (value === null || value === '')  return true
        return false
      })
  }
}
