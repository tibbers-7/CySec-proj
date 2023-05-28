import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ProjectWorkDTO } from 'src/app/model/project-work-dto.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProjectWorkService } from 'src/app/services/project-work.service';
import { ProjectService } from 'src/app/services/project.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-engineer-projects',
  templateUrl: './engineer-projects.component.html',
  styleUrls: ['./engineer-projects.component.css']
})
export class EngineerProjectsComponent implements OnInit{

  public dataSource = new MatTableDataSource<ProjectWorkDTO>()
  public displayedColumns = ['id','projectID', 'engineerID','startDate','endDate','responsibility', 'update-button']
  public projectWorks: ProjectWorkDTO[] = [];
  public projectWorkID: any
  public engineerID: any

  public constructor(private projectService: ProjectService, private projectWorkService: ProjectWorkService, private userService: UserService, private toast: ToastrService, private router: Router, private route: ActivatedRoute, private authService: AuthenticationService){}
  
  
  ngOnInit(): void {
    this.engineerID = Number(this.authService.getUserId());

    this.projectWorkService.getAllByEngineerId(this.engineerID).subscribe(res=>{
      this.projectWorks = res;
      this.dataSource.data = this.projectWorks;
    })
  }

  updateProjectWork(id: number){

  }

}
