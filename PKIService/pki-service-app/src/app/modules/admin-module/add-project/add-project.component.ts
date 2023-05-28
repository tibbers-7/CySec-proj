import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeDTO } from 'src/app/model/employee-dto';
import { Project } from 'src/app/model/project.model';
import { ProjectService } from 'src/app/services/project.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css']
})
export class AddProjectComponent implements OnInit {

  createProjectForm = new FormGroup({
    name: new FormControl('', Validators.required),
    startDate: new FormControl('', Validators.required),
    endDate: new FormControl('', Validators.required),
  })

  error:any={isError:false,errorMessage:''};
  isValidDate:any;
  isSelected:any;

  selectedProjectManager: EmployeeDTO | undefined

  allProjectManagers: EmployeeDTO[] = []

  constructor(private router: Router, private route: ActivatedRoute, private projectService: ProjectService, private userService: UserService) { }

  get name() {
    return this.createProjectForm.get('name');
  }

  get startDate() {
    return this.createProjectForm.get('starDate');
  }
  get endDate() {
    return this.createProjectForm.get('endDate');
  }

  ngOnInit(): void {
    this.userService.getAllProjectManagers().subscribe(res=>{
      this.allProjectManagers = res;
    })
  }

  createProject(){

    let name = this.createProjectForm.get("name")?.value
    
    let startDate = this.createProjectForm.get("startDate")?.value
    let endDate = this.createProjectForm.get("endDate")?.value

    console.log(startDate)
    console.log(typeof(startDate))
    console.log(endDate)
    console.log(typeof(endDate))

    let start = new Date(startDate + "T" + "00:00" + ":00");
    let end = new Date(endDate + "T" + "00:00" + ":00");

    this.isValidDate = this.validateDates(start, end);

    let project: Project ={
      id: 0,
      name: name ? name : '',
      startDate: startDate ? startDate : '',
      endDate: endDate ? endDate : '',
      projectManagerID: Number(this.selectedProjectManager?.id) 
    }

    console.log(project)

    this.isSelected = this.isManagerSelected(this.selectedProjectManager?.id)

    if(this.isValidDate && this.isSelected){
      this.projectService.createProject(project).subscribe(res=>{
        console.log(res);
        this.router.navigate(['projects']);
      });
    }

  }

  isManagerSelected(id: any){
    this.isSelected = true;

    if(id === undefined){
      this.error={isError:true,errorMessage:'Select Project Manager!'};
      this.isSelected = false;
    }

    return this.isSelected;
  }

  validateDates(sDate: Date, eDate: Date){
    this.isValidDate = true;
  
    if((sDate != null && eDate !=null) && (eDate) <= (sDate)){
      this.error={isError:true,errorMessage:'End date should be greater than start date!'};
      this.isValidDate = false;
    }

    let today = new Date();
    if((sDate <= today) || (eDate <= today)){
      this.error={isError:true,errorMessage:'Cannot create interval in past!'};
      this.isValidDate = false;
    }

    return this.isValidDate;
  }

  goBack = () => {
    this.router.navigate(['projects']);
  };

}
