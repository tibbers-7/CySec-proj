import { NgModule } from '@angular/core';
import { HrManagerToolbarComponent } from './hr-manager-toolbar/hr-manager-toolbar.component';
import { HrManagerProfileComponent } from './hr-manager-profile/hr-manager-profile.component';
import { RouterModule, Routes } from '@angular/router';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { MaterialModule } from '../material/material.module';
import { RoleGuard } from 'src/app/auth/role-guard';
import { HrManagerEmployeesViewComponent } from './hr-manager-employees-view/hr-manager-employees-view.component';
import { HrManagerProjectManagerViewComponent } from './hr-manager-project-manager-view/hr-manager-project-manager-view.component';
import { HrManagerEngineerViewComponent } from './hr-manager-engineer-view/hr-manager-engineer-view.component';

const routes: Routes = [
  {path:'hrmanager/editProfile', component: HrManagerProfileComponent,
  canActivate: [RoleGuard], data: { expectedRole: 'ROLE_HR_MANAGER' }},
  {path:'hrmanager/employees', component: HrManagerEmployeesViewComponent,
  canActivate: [RoleGuard], data: { expectedRole: 'ROLE_HR_MANAGER' }},
  {path:'hrmanager/employees/:id/manager', component: HrManagerProjectManagerViewComponent,
  canActivate: [RoleGuard], data: { expectedRole: 'ROLE_HR_MANAGER' }},
  {path:'hrmanager/employees/:id/engineer', component: HrManagerEngineerViewComponent,
  canActivate: [RoleGuard], data: { expectedRole: 'ROLE_HR_MANAGER' }}

];

@NgModule({
  declarations: [
    HrManagerToolbarComponent,
    HrManagerProfileComponent,
    HrManagerEmployeesViewComponent,
    HrManagerProjectManagerViewComponent,
    HrManagerEngineerViewComponent
  ],
  imports: [
    AppRoutingModule,
    MaterialModule,
    RouterModule.forChild(routes)
  ],
  exports: [
    HrManagerToolbarComponent
  ]
})
export class HrManagerModule { }
