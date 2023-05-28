import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { ProjectWorkDTO } from 'src/app/model/project-work-dto.model';
import { Project } from 'src/app/model/project.model';
import { ProjectWorkService } from 'src/app/services/project-work.service';
import { ProjectService } from 'src/app/services/project.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-engineer-update-project',
  templateUrl: './engineer-update-project.component.html',
  styleUrls: ['./engineer-update-project.component.css']
})
export class EngineerUpdateProjectComponent {

  projectID: any
  projectWorkID: any
  engineerID: any
  project: Project = new Project()
  projectWork: ProjectWorkDTO = new ProjectWorkDTO()

  updateProjectWorkForm = new FormGroup({
    name: new FormControl('', Validators.required),
    startDate: new FormControl('', Validators.required),
    endDate: new FormControl('', Validators.required),
    responsibility: new FormControl('', Validators.required),
  })


  constructor(private router: Router, private route: ActivatedRoute, private projectService: ProjectService, private userService: UserService, private projectWorkService: ProjectWorkService) { }
  
  get name() {
    return this.updateProjectWorkForm.get('name');
  }

  get startDate() {
    return this.updateProjectWorkForm.get('starDate');
  }
  get endDate() {
    return this.updateProjectWorkForm.get('endDate');
  }

  get responsibility() {
    return this.updateProjectWorkForm.get('responsibility');
  }

  ngOnInit(): void {

    this.route.params.subscribe((params: Params) =>{
      this.projectWorkID = params['idw'];

      this.projectWorkService.getProjectWorkById(this.projectWorkID).subscribe(res=>{
        this.projectWork = res;

        this.projectID = this.projectWork.projectID;

        this.projectService.getProjectById(this.projectID).subscribe(res=>{
          this.project = res;

          this.updateProjectWorkForm.patchValue({
            name: this.project.name,
            startDate: this.projectWork.startDate,
            endDate: this.projectWork.endDate,
            responsibility: this.projectWork.responsibility,

          })
        })
      })
    })

  }

  updateProjectWork(){
  
    let responsibility = this.updateProjectWorkForm.get("responsibility")?.value

    let projectWork: ProjectWorkDTO = {
      id: this.projectWorkID,
      projectID: this.projectWork.projectID,
      engineerID: this.projectWork.engineerID, 
      startDate: this.projectWork.startDate,
      endDate: this.projectWork.endDate,
      responsibility: responsibility ? responsibility : ''
    }

    this.projectWorkService.updateProjectWork(projectWork).subscribe(res=>{
      console.log(res);
      this.router.navigate(['engineer/projects']);
    })

  }

  goBack(){
    this.router.navigate(['engineer/projects']);
  }

}
