import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { SkillDTO } from 'src/app/model/skill-dto.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { SkillService } from 'src/app/services/skill.service';

@Component({
  selector: 'app-add-skill',
  templateUrl: './add-skill.component.html',
  styleUrls: ['./add-skill.component.css']
})
export class AddSkillComponent implements OnInit{

  createSkillForm = new FormGroup({
    name: new FormControl('', Validators.required),
    evaluation: new FormControl('', Validators.required),
  })

  public engineerID: any


  constructor(private router: Router, private route: ActivatedRoute, private skillService: SkillService, private authService: AuthenticationService) { }

  get name() {
    return this.createSkillForm.get('name');
  }

  get evaluation() {
    return this.createSkillForm.get('evaluation');
  }
  

  ngOnInit(): void {
    this.engineerID = Number(this.authService.getUserId());
  }

  createSkill(){

    let name = this.createSkillForm.get("name")?.value
    let evaluation = this.createSkillForm.get("evaluation")?.value

    let skill: SkillDTO = {
      id: 0,
      engineerID: this.engineerID,
      name: name ? name : '',
      evaluation: Number(evaluation),
    }

    this.skillService.createSkill(skill).subscribe(res=>{
      console.log(res)
      this.router.navigate(['engineer/skills']);
    }, error => 
    {
      alert("Cannot add skill, already exists!")
    })


  }

  goBack(){
    this.router.navigate(['engineer/skills']);
  }

}
