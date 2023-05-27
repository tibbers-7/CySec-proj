import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Project } from 'src/app/model/project.model';
import { ProjectService } from 'src/app/services/project.service';

@Component({
  selector: 'app-projects-view',
  templateUrl: './projects-view.component.html',
  styleUrls: ['./projects-view.component.css']
})
export class ProjectsViewComponent implements OnInit{
  
  public dataSource = new MatTableDataSource<Project>()
  public displayedColumns = ['id','name','startDate','endDate','projectManagerID', 'update-button', 'view-button', 'delete-button']
  public projects: Project[] = [];

  public constructor(private projectService: ProjectService, private toast: ToastrService, private router: Router, private route: ActivatedRoute){}

  ngOnInit(): void {
    this.projectService.getAllProjects().subscribe(res=>{
      this.projects = res;
      this.dataSource.data = this.projects;
    })
  }

  addProject(): void{
    this.router.navigate(['projects/create']);
  }

  updateProject(id: number){

  }

  viewProject(id: number){

  }

  deleteProject(id: number){
    this.projectService.deleteProject(id).subscribe(res=>{
      this.projectService.getAllProjects().subscribe(res=>{
        this.projects = res;
        this.dataSource.data = this.projects;
      })
    })
  }

}
