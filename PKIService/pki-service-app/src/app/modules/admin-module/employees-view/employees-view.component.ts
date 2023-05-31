import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EmployeeDTO } from 'src/app/model/employee-dto';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-employees-view',
  templateUrl: './employees-view.component.html',
  styleUrls: ['./employees-view.component.css']
})
export class EmployeesViewComponent implements OnInit{

  public dataSource = new MatTableDataSource<EmployeeDTO>()
  public displayedColumns = ['id','name', 'surname', 'username', 'phoneNumber', 'userType']
  public employees: EmployeeDTO[] = [];

  public constructor(private userService: UserService, private toast: ToastrService, private router: Router, private route: ActivatedRoute){}


  ngOnInit(): void {
    this.userService.getAllEmployees().subscribe(res=>{
      this.employees = res;
      this.dataSource.data = this.employees;
    })
  }

  addEmployee(){
    this.router.navigate(['/employees/create']);
  }

}
