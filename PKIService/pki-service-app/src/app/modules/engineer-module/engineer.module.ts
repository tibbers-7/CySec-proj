import { NgModule } from '@angular/core';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { MaterialModule } from '../material/material.module';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { RoleGuard } from 'src/app/auth/role-guard';
import { EngineerToolbarComponent } from './engineer-toolbar/engineer-toolbar.component';
import { EngineerProfileComponent } from './engineer-profile/engineer-profile.component';
import { EngineerProjectsComponent } from './engineer-projects/engineer-projects.component';
import { EngineerUpdateProjectComponent } from './engineer-update-project/engineer-update-project.component';

const routes: Routes = [
  {path: 'engineer/editProfile', component: EngineerProfileComponent},
  {path: 'engineer/projects', component: EngineerProjectsComponent},
  {path: 'engineer/projects/:idw/update', component: EngineerUpdateProjectComponent}

];

@NgModule({
  declarations: [
    EngineerToolbarComponent,
    EngineerProfileComponent,
    EngineerProjectsComponent,
    EngineerUpdateProjectComponent
  ],
  imports: [
    AppRoutingModule,
    MaterialModule,
    FormsModule,
    RouterModule.forChild(routes)
  ]
})
export class EngineerModule { }
