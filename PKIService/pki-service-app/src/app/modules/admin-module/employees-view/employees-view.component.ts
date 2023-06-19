import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EmployeeDTO } from 'src/app/model/employee-dto';
import { UserService } from 'src/app/services/user.service';
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialog } from '@angular/material/dialog';
import { ControlUserDialogComponent } from '../control-user-dialog/control-user-dialog.component';

@Component({
  selector: 'app-employees-view',
  templateUrl: './employees-view.component.html',
  styleUrls: ['./employees-view.component.css']
})
export class EmployeesViewComponent implements OnInit{

  public dataSource = new MatTableDataSource<EmployeeDTO>()
  public displayedColumns = ['id','name', 'surname', 'username', 'phoneNumber', 'userType','click']
  public employees: EmployeeDTO[] = [];
  public selection: SelectionModel<EmployeeDTO>=new SelectionModel();
  public checkedRows: EmployeeDTO[] = [];

  isResolved = false
  isBlocked = false

  public constructor(private userService: UserService, private toast: ToastrService, private router: Router, private route: ActivatedRoute,public dialog: MatDialog){}


  ngOnInit(): void {
    this.userService.getAllEmployees().subscribe(res=>{
      this.employees = res;
      this.dataSource.data = this.employees;
      this.selection = new SelectionModel<EmployeeDTO>(true, []);    })
  }

  addEmployee(){
    this.router.navigate(['/employees/create']);
  }

  

  getDetailsOfRequest(request: EmployeeDTO): void{
    const dialogRef = this.dialog.open(ControlUserDialogComponent, {
      data:{ employee: request, isBlocked : this.isBlocked, isResolved: this.isResolved},
    })
    dialogRef.afterClosed().subscribe(result => {
      console.log(result)
        if(result.isResolved){
          if(result.isBlocked){
            this.blockUser(request)
           
          } else  this.blockRefreshToken(request)
        }
      this.isResolved = false
      }
    )
  }

  blockUser(dto:EmployeeDTO){
    this.userService.blockUser(dto).subscribe(res=>{
      this.toast.success("Succesfully blocked user!")   
     },error=>{
      this.toast.error("Something went wrong!")
     })
  }

  blockRefreshToken(dto:EmployeeDTO){
    this.userService.blockRefreshToken(dto).subscribe(res=>{
      this.toast.success("Succesfully blocked refresh token!")   
     },error=>{
      this.toast.error("Something went wrong!")
     })
  }



  

}

