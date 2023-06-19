import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Address } from 'src/app/model/address';
import { EmployeeDTO } from 'src/app/model/employee-dto';
import { AddressService } from 'src/app/services/address.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add-employees',
  templateUrl: './add-employees.component.html',
  styleUrls: ['./add-employees.component.css']
})
export class AddEmployeesComponent implements OnInit{


  createEmployeeForm = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.pattern('[a-zA-Z]+$')]),
    surname: new FormControl('', [Validators.required, Validators.pattern('[a-zA-Z]+$')]),
    username: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required),
    phoneNumber: new FormControl('', Validators.required),
    country: new FormControl('', [Validators.required, Validators.pattern('[a-zA-Z]+$')]),
    city: new FormControl('', [Validators.required, Validators.pattern('[a-zA-Z]+$')]),
    streetAddress: new FormControl('', Validators.required),
  })

  addressID: any

  selectedRole: string | undefined

  allRoles: string[] = ['ROLE_ADMIN', 'ROLE_PROJECT_MANAGER', 'ROLE_HR_MANAGER', 'ROLE_ENGINEER']

  error:any={isError:false,errorMessage:''};
  isSelected:any;

  constructor(private router: Router, private route: ActivatedRoute, private addressService: AddressService, private userService: UserService) { }

  get name() {
    return this.createEmployeeForm.get('name');
  }

  get surname() {
    return this.createEmployeeForm.get('surname');
  }

  get username() {
    return this.createEmployeeForm.get('username');
  }

  get password() {
    return this.createEmployeeForm.get('password');
  }

  get phoneNumber() {
    return this.createEmployeeForm.get('phoneNumber');
  }

  get country() {
    return this.createEmployeeForm.get('country');
  }

  get city() {
    return this.createEmployeeForm.get('city');
  }

  get streetAddress() {
    return this.createEmployeeForm.get('streetAddress');
  }

  ngOnInit(): void {
  }

  createEmployee(){
    let name = this.createEmployeeForm.get("name")?.value
    let surname = this.createEmployeeForm.get("surname")?.value
    let username = this.createEmployeeForm.get("username")?.value
    let password = this.createEmployeeForm.get("password")?.value
    let phoneNumber = this.createEmployeeForm.get("phoneNumber")?.value
    let country = this.createEmployeeForm.get("country")?.value
    let city = this.createEmployeeForm.get("city")?.value
    let streetAddress = this.createEmployeeForm.get("streetAddress")?.value


    let address: Address ={
      id: 0,
      country: country ? country : '',
      city: city ? city : '',
      streetAddress: streetAddress ? streetAddress : ''
    }

    console.log(address)

    this.addressService.createAddress(address).subscribe(res=>{
      this.addressID = res.id;

      let employee: EmployeeDTO={
        id: 0,
        name: name ? name : '',
        surname: surname ? surname : '',
        username: username ? username : '',
        password: password ? password : '',
        addressID: this.addressID,
        phoneNumber: phoneNumber ? phoneNumber : '',
        role: String(this.selectedRole),
        dateOfEmployment : new Date().toString()
      }

      console.log(employee)

      this.isSelected = this.isRoleSelected(this.selectedRole)

      if(this.isSelected){
        this.userService.createEmployee(employee).subscribe(res=>{
          console.log(res)
          this.router.navigate(['employees']);
        }, error =>
        {
          alert("Cannot add employee, username already exists!")
          this.addressService.deleteAddress(this.addressID).subscribe(res=>{

          })
        })
      }


    })


  }

  isRoleSelected(id: any){
    this.isSelected = true;

    if(id === undefined){
      this.error={isError:true,errorMessage:'Select Work Title!'};
      this.isSelected = false;
    }

    return this.isSelected;
  }

  goBack = () => {
    this.router.navigate(['employees']);
  };

}
