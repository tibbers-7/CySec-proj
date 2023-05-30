import { NgModule } from '@angular/core';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { MaterialModule } from '../material/material.module';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
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
    RouterModule.forChild(routes)
  ],
  exports: [ 
    ManagerToolbarComponent
  ]
})
export class ManagerModule { }
