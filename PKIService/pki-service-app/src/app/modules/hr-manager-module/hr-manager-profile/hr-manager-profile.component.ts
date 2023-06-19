import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Address } from 'src/app/model/address';
import { EmployeeDTO } from 'src/app/model/employee-dto';
import { AddressService } from 'src/app/services/address.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-hr-manager-profile',
  templateUrl: './hr-manager-profile.component.html',
  styleUrls: ['./hr-manager-profile.component.css']
})
export class HrManagerProfileComponent implements OnInit{

  updateEmployeeForm = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.pattern('[a-zA-Z]+$')]),
    surname: new FormControl('', [Validators.required, Validators.pattern('[a-zA-Z]+$')]),
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    phoneNumber: new FormControl('', Validators.required),
    country: new FormControl('', Validators.required),
    city: new FormControl('', Validators.required),
    streetAddress: new FormControl('', Validators.required),
  })

  addressID: any
  employeeID: any
  employee: EmployeeDTO = new EmployeeDTO()
  address: Address = new Address()


  constructor(private router: Router, private route: ActivatedRoute, private addressService: AddressService, private userService: UserService, private authService: AuthenticationService) { }

  get name() {
    return this.updateEmployeeForm.get('name');
  }

  get surname() {
    return this.updateEmployeeForm.get('surname');
  }

  get username() {
    return this.updateEmployeeForm.get('username');
  }

  get password() {
    return this.updateEmployeeForm.get('password');
  }

  get phoneNumber() {
    return this.updateEmployeeForm.get('phoneNumber');
  }

  get country() {
    return this.updateEmployeeForm.get('country');
  }

  get city() {
    return this.updateEmployeeForm.get('city');
  }

  get streetAddress() {
    return this.updateEmployeeForm.get('streetAddress');
  }

  ngOnInit(): void {

      this.employeeID = Number(this.authService.getUserId());

      console.log(this.employeeID)

      this.userService.getEmployeeById(this.employeeID).subscribe(res=>{
        this.employee = res;
        console.log(this.employee)
        this.addressID = this.employee.addressID

        console.log(this.addressID)

        this.addressService.getAddressById(this.addressID).subscribe(res=>{
          this.address = res;

          console.log(this.address)

          this.updateEmployeeForm.patchValue({
            name: this.employee.name,
            surname: this.employee.surname,
            username: this.employee.username,
            password: this.employee.password,
            phoneNumber: this.employee.phoneNumber,
            country: this.address.country,
            city: this.address.city,
            streetAddress: this.address.streetAddress,
          })
        })
      })
  }

  updateEmployee(){

    let name = this.updateEmployeeForm.get("name")?.value
    let surname = this.updateEmployeeForm.get("surname")?.value
    let username = this.updateEmployeeForm.get("username")?.value
    let password = this.updateEmployeeForm.get("password")?.value
    let phoneNumber = this.updateEmployeeForm.get("phoneNumber")?.value
    let country = this.updateEmployeeForm.get("country")?.value
    let city = this.updateEmployeeForm.get("city")?.value
    let streetAddress = this.updateEmployeeForm.get("streetAddress")?.value


    let address: Address ={
      id: this.addressID,
      country: country ? country : '',
      city: city ? city : '',
      streetAddress: streetAddress ? streetAddress : ''
    }

    console.log(address)

    this.addressService.updateAddress(address).subscribe(res=>{

      let employee: EmployeeDTO={
        id: this.employeeID,
        name: name ? name : '',
        surname: surname ? surname : '',
        username: username ? username : '',
        password: password ? password : '',
        addressID: this.addressID,
        phoneNumber: phoneNumber ? phoneNumber : '',
        role: this.employee.role,
        isBlocked:false,
        allowRefreshToken:true
      }

      console.log(employee)

      this.userService.updateEmployee(employee).subscribe(res=>{
        this.router.navigate(['engineer/projects']);
      })

    })


  }

  goBack = () => {
    this.router.navigate(['engineer/projects']);
  };

}

