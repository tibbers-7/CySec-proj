import { NgModule } from '@angular/core';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { MaterialModule } from '../material/material.module';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { RoleGuard } from 'src/app/auth/role-guard';
import { ManagerToolbarComponent } from './manager-toolbar/manager-toolbar.component';
import { ManagerProfileComponent } from './manager-profile/manager-profile.component';

const routes: Routes = [
  {path: 'manager/editProfile', component: ManagerProfileComponent}
];

@NgModule({
  declarations: [
    ManagerToolbarComponent,
    ManagerProfileComponent
  ],
  imports: [
    AppRoutingModule,
    MaterialModule,
    FormsModule,
    RouterModule.forChild(routes)
  ]
})
export class ManagerModule { }
