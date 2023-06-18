import { NgModule } from '@angular/core';
import { HrManagerToolbarComponent } from './hr-manager-toolbar/hr-manager-toolbar.component';
import { HrManagerProfileComponent } from './hr-manager-profile/hr-manager-profile.component';
import { RouterModule, Routes } from '@angular/router';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { MaterialModule } from '../material/material.module';
import { RoleGuard } from 'src/app/auth/role-guard';

const routes: Routes = [
  {path:'hrmanager/editProfile', component: HrManagerProfileComponent,
  canActivate: [RoleGuard], data: { expectedRole: 'ROLE_HR_MANAGER' }}

];

@NgModule({
  declarations: [
    HrManagerToolbarComponent,
    HrManagerProfileComponent
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
