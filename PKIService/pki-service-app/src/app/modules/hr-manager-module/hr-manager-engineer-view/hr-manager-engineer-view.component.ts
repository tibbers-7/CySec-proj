import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EmployeeDTO } from 'src/app/model/employee-dto';
import { ProjectWorkDTO } from 'src/app/model/project-work-dto.model';
import { SkillDTO } from 'src/app/model/skill-dto.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProjectWorkService } from 'src/app/services/project-work.service';
import { ProjectService } from 'src/app/services/project.service';
import { SkillService } from 'src/app/services/skill.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-hr-manager-engineer-view',
  templateUrl: './hr-manager-engineer-view.component.html',
  styleUrls: ['./hr-manager-engineer-view.component.css']
})
export class HrManagerEngineerViewComponent implements OnInit{

  public dataSource = new MatTableDataSource<ProjectWorkDTO>()
  public displayedColumns = ['id','projectID','startDate','endDate','responsibility']
  public projectWorks: ProjectWorkDTO[] = [];
  public engineerID: any
  public engineer: EmployeeDTO = new EmployeeDTO()
  public dataSource2 = new MatTableDataSource<SkillDTO>()
  public displayedColumns2 = ['id','name','evaluation']
  public skills: SkillDTO[] = [];

  public constructor(private projectService: ProjectService, private projectWorkService: ProjectWorkService, private userService: UserService, private toast: ToastrService, private router: Router, private route: ActivatedRoute, private authService: AuthenticationService, private skillService: SkillService){}
  
  
  ngOnInit(): void {
    this.route.params.subscribe((params: Params) =>{
      this.engineerID = params['id']
    
      this.projectWorkService.getAllByEngineerId(this.engineerID).subscribe(res=>{
        this.projectWorks = res;
        this.dataSource.data = this.projectWorks;
      })

      this.userService.getEmployeeById(this.engineerID).subscribe(res=>{
        this.engineer = res;
      })

      this.skillService.getAllSkillsByEngineerID(this.engineerID).subscribe(res=>{
        this.skills = res;
        this.dataSource2.data = this.skills;
      })
    
    })

    
  }


}
