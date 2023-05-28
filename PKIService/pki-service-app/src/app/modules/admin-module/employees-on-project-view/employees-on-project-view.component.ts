import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EmployeeDTO } from 'src/app/model/employee-dto';
import { ProjectWorkDTO } from 'src/app/model/project-work-dto.model';
import { Project } from 'src/app/model/project.model';
import { ProjectWorkService } from 'src/app/services/project-work.service';
import { ProjectService } from 'src/app/services/project.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-employees-on-project-view',
  templateUrl: './employees-on-project-view.component.html',
  styleUrls: ['./employees-on-project-view.component.css']
})
export class EmployeesOnProjectViewComponent implements OnInit{

  public project: Project = new Project();
  public projectManager: EmployeeDTO = new EmployeeDTO();

  public dataSource = new MatTableDataSource<ProjectWorkDTO>()
  public displayedColumns = ['id','projectID', 'engineerID','startDate','endDate','responsibility', 'update-button', 'delete-button']
  public projectWorks: ProjectWorkDTO[] = [];
  public projectID: any

  public constructor(private projectService: ProjectService, private projectWorkService: ProjectWorkService, private userService: UserService, private toast: ToastrService, private router: Router, private route: ActivatedRoute){}

  ngOnInit(): void {
    
    this.route.params.subscribe((params: Params) =>{
      this.projectID = params['id']

      this.projectService.getProjectById(this.projectID).subscribe(res=>{
        this.project = res;

        this.userService.getEmployeeById(this.project.projectManagerID).subscribe(res=>{
          this.projectManager = res;
        })
        
      })

      

      this.projectWorkService.getAllByProjectId(this.projectID).subscribe(res=>{
        this.projectWorks = res;
        this.dataSource.data = this.projectWorks;
       })

    })
  
  }

  addEngineer(){
    this.router.navigate(['projects', this.projectID, 'employees', 'add']);
  }

  updateProjectWork(id: number){
    this.router.navigate(['projects', this.projectID, 'employees', 'update', id]);
  }

  deleteProjectWork(id: number){

    this.projectWorkService.deleteProjectWork(id).subscribe(res =>{
      this.projectWorkService.getAllByProjectId(this.projectID).subscribe(res=>{
        this.projectWorks = res;
        this.dataSource.data = this.projectWorks;
      })
    })
  }

}
