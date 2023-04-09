import { NgModule } from '@angular/core';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { MaterialModule } from '../material/material.module';
import { RouterModule, Routes } from '@angular/router';
import { AddCertificateComponent } from './add-certificate/add-certificate.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';

const routes: Routes = [
    {path:'', component: AdminHomeComponent},
    {path:'add-certificate', component: AddCertificateComponent}
  ];
  

@NgModule({
  declarations: [
    AddCertificateComponent,
    AdminHomeComponent
  ],
  imports: [
    AppRoutingModule,
    MaterialModule,
    RouterModule.forChild(routes)
  ]
})
export class AdminModule { }