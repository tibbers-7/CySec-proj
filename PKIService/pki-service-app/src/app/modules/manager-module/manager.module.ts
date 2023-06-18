import { NgModule } from '@angular/core';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { MaterialModule } from '../material/material.module';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ManagerToolbarComponent } from './manager-toolbar/manager-toolbar.component';
import { ManagerProfileComponent } from './manager-profile/manager-profile.component';
import { RoleGuard } from 'src/app/auth/role-guard';
import { ManagerProjectsComponent } from './manager-projects/manager-projects.component';
import { ManagerEngineersOnProjectViewComponent } from './manager-engineers-on-project-view/manager-engineers-on-project-view.component';
import { ManagerAddEngineerComponent } from './manager-add-engineer/manager-add-engineer.component';
import { ManagerEditEngineerComponent } from './manager-edit-engineer/manager-edit-engineer.component';

const routes: Routes = [
  {path: 'manager/editProfile', component: ManagerProfileComponent,
  canActivate: [RoleGuard], data: { expectedRole: 'ROLE_PROJECT_MANAGER' }},
  {path: 'manager/projects', component: ManagerProjectsComponent,
  canActivate: [RoleGuard], data: { expectedRole: 'ROLE_PROJECT_MANAGER' }},
  {path: 'manager/projects/:id/employees', component: ManagerEngineersOnProjectViewComponent,
  canActivate: [RoleGuard], data: { expectedRole: 'ROLE_PROJECT_MANAGER' }},
  {path:'manager/projects/:id/employees/add', component: ManagerAddEngineerComponent,
  canActivate: [RoleGuard], data: { expectedRole: 'ROLE_PROJECT_MANAGER' }},
  {path:'manager/projects/:id/employees/update/:idw', component: ManagerEditEngineerComponent,
  canActivate: [RoleGuard], data: { expectedRole: 'ROLE_PROJECT_MANAGER' }}

];

@NgModule({
  declarations: [
    ManagerToolbarComponent,
    ManagerProfileComponent,
    ManagerProjectsComponent,
    ManagerEngineersOnProjectViewComponent,
    ManagerAddEngineerComponent,
    ManagerEditEngineerComponent
  ],
  imports: [
    AppRoutingModule,
    MaterialModule,
    RouterModule.forChild(routes)
  ],
  exports: [ 
    ManagerToolbarComponent
  ]
})
export class ManagerModule { }
