import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EmployeeDTO } from 'src/app/model/employee-dto';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-hr-manager-employees-view',
  templateUrl: './hr-manager-employees-view.component.html',
  styleUrls: ['./hr-manager-employees-view.component.css']
})
export class HrManagerEmployeesViewComponent implements OnInit{

  public dataSource = new MatTableDataSource<EmployeeDTO>()
  public dataSource2 = new MatTableDataSource<EmployeeDTO>()
  public displayedColumns = ['id','name', 'surname', 'username', 'phoneNumber', 'view-button']
  public managers: EmployeeDTO[] = [];
  public engineers: EmployeeDTO[] = [];

  public constructor(private userService: UserService, private toast: ToastrService, private router: Router, private route: ActivatedRoute){}


  ngOnInit(): void {
    this.userService.getAllProjectManagers().subscribe(res=>{
      this.managers = res;
      this.dataSource.data = this.managers;
    })

    this.userService.getAllEngineers().subscribe(res=>{
      this.engineers = res;
      this.dataSource2.data = this.engineers;
    })
  }

  viewManager(id: number){
    this.router.navigate(['hrmanager/employees/', id, 'manager']);
  }

  viewEngineer(id: number){
    this.router.navigate(['hrmanager/employees/', id, 'engineer']);
  }

}

