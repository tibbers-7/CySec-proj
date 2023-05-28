import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { SkillDTO } from 'src/app/model/skill-dto.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { SkillService } from 'src/app/services/skill.service';

@Component({
  selector: 'app-edit-skill',
  templateUrl: './edit-skill.component.html',
  styleUrls: ['./edit-skill.component.css']
})
export class EditSkillComponent implements OnInit{

  updateSkillForm = new FormGroup({
    name: new FormControl('', Validators.required),
    evaluation: new FormControl('', [Validators.required, Validators.pattern('[0-9]+$')]),
  })

  public skillID: any

  public engineerID: any


  constructor(private router: Router, private route: ActivatedRoute, private skillService: SkillService, private authService: AuthenticationService) { }

  get name() {
    return this.updateSkillForm.get('name');
  }

  get evaluation() {
    return this.updateSkillForm.get('evaluation');
  }
  

  ngOnInit(): void {

    this.route.params.subscribe((params: Params)=>{
      this.skillID = params['id'];

      this.skillService.getSkillById(this.skillID).subscribe(res=>{

        this.engineerID = res.engineerID

        this.updateSkillForm.patchValue({
          name: res.name,
          evaluation: String(res.evaluation),
        })
      })


    })
    
  }

  updateSkill(){

    let name = this.updateSkillForm.get("name")?.value
    let evaluation = this.updateSkillForm.get("evaluation")?.value

    let skill: SkillDTO = {
      id: this.skillID,
      engineerID: this.engineerID,
      name: name ? name : '',
      evaluation: Number(evaluation),
    }

    this.skillService.updateSkill(skill).subscribe(res=>{
      console.log(res)
      this.router.navigate(['engineer/skills']);
    })


  }

  goBack(){
    this.router.navigate(['engineer/skills']);
  }
}
