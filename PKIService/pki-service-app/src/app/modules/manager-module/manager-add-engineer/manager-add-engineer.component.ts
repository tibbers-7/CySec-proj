import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { EmployeeDTO } from 'src/app/model/employee-dto';
import { ProjectWorkDTO } from 'src/app/model/project-work-dto.model';
import { Project } from 'src/app/model/project.model';
import { ProjectWorkService } from 'src/app/services/project-work.service';
import { ProjectService } from 'src/app/services/project.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-manager-add-engineer',
  templateUrl: './manager-add-engineer.component.html',
  styleUrls: ['./manager-add-engineer.component.css']
})
export class ManagerAddEngineerComponent implements OnInit{

  projectID: any
  project: Project = new Project()

  createProjectWorkForm = new FormGroup({
    startDate: new FormControl('', Validators.required),
    endDate: new FormControl('', Validators.required),
    responsibility: new FormControl('', Validators.required),
  })

  error:any={isError:false,errorMessage:''};
  isValidDate:any;
  isSelected:any;
  isValidStart:any;
  isValidEnd:any;

  selectedEngineer: EmployeeDTO | undefined

  allEngineers: EmployeeDTO[] = []

  constructor(private router: Router, private route: ActivatedRoute, private projectService: ProjectService, private userService: UserService, private projectWorkService: ProjectWorkService) { }

  get startDate() {
    return this.createProjectWorkForm.get('starDate');
  }
  get endDate() {
    return this.createProjectWorkForm.get('endDate');
  }

  get responsibility() {
    return this.createProjectWorkForm.get('responsibility');
  }

  ngOnInit(): void {
    
    this.userService.getAllEngineers().subscribe(res=>{
      this.allEngineers = res;
    })

    this.route.params.subscribe((params: Params) =>{
      this.projectID = params['id']
      
      this.projectService.getProjectById(this.projectID).subscribe(res=>{
        this.project = res;
      })


    })
  }

  createProjectWork(){

    let responsibility = this.createProjectWorkForm.get("responsibility")?.value

    let startDate = this.createProjectWorkForm.get("startDate")?.value
    let endDate = this.createProjectWorkForm.get("endDate")?.value

    let startDateProject = new Date(this.project.startDate+ "T" + "00:00" + ":00");
    let endDateProject = new Date(this.project.endDate+ "T" + "00:00" + ":00");

    console.log(startDate)
    console.log(typeof(startDate))
    console.log(endDate)
    console.log(typeof(endDate))

    let start = new Date(startDate + "T" + "00:00" + ":00");
    let end = new Date(endDate + "T" + "00:00" + ":00");

    this.isValidStart = this.validateStart(startDateProject, start);

    this.isValidEnd = this.validateEnd(end, endDateProject);

    this.isValidDate = this.validateDates(start, end);

    let projectWork: ProjectWorkDTO = {
      id: 0,
      projectID: this.projectID,
      engineerID: Number(this.selectedEngineer?.id), 
      startDate: startDate ? startDate : '',
      endDate: endDate ? endDate : '',
      responsibility: responsibility ? responsibility : ''
    }

    console.log(projectWork)

    this.isSelected = this.isEngineerSelected(this.selectedEngineer?.id)

    if(this.isValidDate && this.isValidStart && this.isValidEnd && this.isSelected){
      this.projectWorkService.createProjectWork(projectWork).subscribe(res=>{
        console.log(res);
        this.router.navigate(['manager/projects', this.projectID, 'employees']);
      }, error => 
      {
        alert("Cannot add engineer, already on the project!")
      });
    }

  }

  isEngineerSelected(id: any){
    this.isSelected = true;

    if(id === undefined){
      this.error={isError:true,errorMessage:'Select Engineer!'};
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

  validateStart(sDate: Date, eDate: Date){
    this.isValidStart = true;
  
    if((sDate != null && eDate !=null) && (eDate) <= (sDate)){
      this.error={isError:true,errorMessage:'Start date for engineer should be greater than start date of project!'};
      this.isValidStart = false;
    }
    return this.isValidStart;
  }

  validateEnd(sDate: Date, eDate: Date){
    this.isValidEnd = true;
  
    if((sDate != null && eDate !=null) && (eDate) <= (sDate)){
      this.error={isError:true,errorMessage:'End date of project should be greater than end date for engineer!'};
      this.isValidEnd = false;
    }
    return this.isValidEnd;
  }


  goBack = () => {
    this.router.navigate(['manager/projects', this.projectID, 'employees']);
  };

}
