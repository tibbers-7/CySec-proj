import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-engineer-toolbar',
  templateUrl: './engineer-toolbar.component.html',
  styleUrls: ['./engineer-toolbar.component.css']
})
export class EngineerToolbarComponent {
  constructor(private router:Router) { }
 
  ProjectsClick(){
    //this.router.navigate(['/projects']);
  }

  EditProfileClick(){
    //this.router.navigate(['/editProfile']);
  }

  SkillsClick(){

  }

  LogoutClick(){
    this.router.navigate(['/'])
  }

}
