import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EmployeeDTO } from 'src/app/model/employee-dto';
import { Project } from 'src/app/model/project.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProjectService } from 'src/app/services/project.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-hr-manager-project-manager-view',
  templateUrl: './hr-manager-project-manager-view.component.html',
  styleUrls: ['./hr-manager-project-manager-view.component.css']
})
export class HrManagerProjectManagerViewComponent implements OnInit{

  public dataSource = new MatTableDataSource<Project>()
  public displayedColumns = ['id','name','startDate','endDate']
  public projects: Project[] = [];
  public managerID: any
  public manager: EmployeeDTO = new EmployeeDTO()

  public constructor(private projectService: ProjectService, private toast: ToastrService, private router: Router, private route: ActivatedRoute, private authService: AuthenticationService, private userService: UserService){}

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) =>{
      this.managerID = params['id']

      this.projectService.getAllProjectsByProjectManagerID(this.managerID).subscribe(res=>{
        this.projects = res;
        this.dataSource.data = this.projects;
      })

      this.userService.getEmployeeById(this.managerID).subscribe(res=>{
        this.manager = res;
      })

    })

    
  }

  

}
