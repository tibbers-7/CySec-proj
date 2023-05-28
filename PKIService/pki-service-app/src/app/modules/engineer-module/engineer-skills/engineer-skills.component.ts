import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SkillDTO } from 'src/app/model/skill-dto.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { SkillService } from 'src/app/services/skill.service';

@Component({
  selector: 'app-engineer-skills',
  templateUrl: './engineer-skills.component.html',
  styleUrls: ['./engineer-skills.component.css']
})
export class EngineerSkillsComponent implements OnInit{

  public dataSource = new MatTableDataSource<SkillDTO>()
  public displayedColumns = ['id','name','evaluation', 'update-button', 'delete-button']
  public skills: SkillDTO[] = [];
  public engineerID: any

  public constructor(private skillService: SkillService, private toast: ToastrService, private router: Router, private route: ActivatedRoute, private authService: AuthenticationService){}


  ngOnInit(): void {
    this.engineerID = Number(this.authService.getUserId());

    this.skillService.getAllSkillsByEngineerID(this.engineerID).subscribe(res=>{
      this.skills = res;
      this.dataSource.data = this.skills;
    })
  }


  addSkill(){
    this.router.navigate(['engineer/skills/add']);
  }

  updateSkill(id: number){
    this.router.navigate(['engineer', 'skills', id, 'update']);
  }

  deleteSkill(id: number){
    this.skillService.deleteSkill(id).subscribe(res=>{
      this.skillService.getAllSkillsByEngineerID(this.engineerID).subscribe(res=>{
        this.skills = res;
        this.dataSource.data = this.skills;
      })
    })
  }

}
