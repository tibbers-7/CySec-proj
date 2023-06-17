import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Project } from 'src/app/model/project.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProjectService } from 'src/app/services/project.service';

@Component({
  selector: 'app-manager-projects',
  templateUrl: './manager-projects.component.html',
  styleUrls: ['./manager-projects.component.css']
})
export class ManagerProjectsComponent implements OnInit{

  public dataSource = new MatTableDataSource<Project>()
  public displayedColumns = ['id','name','startDate','endDate', 'view-button']
  public projects: Project[] = [];
  public managerID: any

  public constructor(private projectService: ProjectService, private toast: ToastrService, private router: Router, private route: ActivatedRoute, private authService: AuthenticationService){}

  ngOnInit(): void {
    this.managerID = Number(this.authService.getUserId());

    this.projectService.getAllProjectsByProjectManagerID(this.managerID).subscribe(res=>{
      this.projects = res;
      this.dataSource.data = this.projects;
    })
  }

  viewProject(id: number){
    this.router.navigate(['manager/projects', id, 'employees']);
  }

}
