import { NgModule } from '@angular/core';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { MaterialModule } from '../material/material.module';
import { RouterModule, Routes } from '@angular/router';
import { EngineerToolbarComponent } from './engineer-toolbar/engineer-toolbar.component';
import { EngineerProfileComponent } from './engineer-profile/engineer-profile.component';
import { EngineerProjectsComponent } from './engineer-projects/engineer-projects.component';
import { EngineerUpdateProjectComponent } from './engineer-update-project/engineer-update-project.component';
import { EngineerSkillsComponent } from './engineer-skills/engineer-skills.component';
import { AddSkillComponent } from './add-skill/add-skill.component';
import { EditSkillComponent } from './edit-skill/edit-skill.component';

const routes: Routes = [
  {path: 'engineer/editProfile', component: EngineerProfileComponent},
  {path: 'engineer/projects', component: EngineerProjectsComponent},
  {path: 'engineer/projects/:idw/update', component: EngineerUpdateProjectComponent},
  {path: 'engineer/skills', component: EngineerSkillsComponent},
  {path: 'engineer/skills/add', component: AddSkillComponent},
  {path: 'engineer/skills/:id/update', component: EditSkillComponent}

];

@NgModule({
  declarations: [
    EngineerToolbarComponent,
    EngineerProfileComponent,
    EngineerProjectsComponent,
    EngineerUpdateProjectComponent,
    EngineerSkillsComponent,
    AddSkillComponent,
    EditSkillComponent
  ],
  imports: [
    AppRoutingModule,
    MaterialModule,
    RouterModule.forChild(routes)
  ],
  exports: [
    EngineerToolbarComponent
  ]
})
export class EngineerModule { }
