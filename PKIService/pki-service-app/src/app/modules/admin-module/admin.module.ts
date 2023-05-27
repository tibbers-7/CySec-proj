import { NgModule } from '@angular/core';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { MaterialModule } from '../material/material.module';
import { RouterModule, Routes } from '@angular/router';
import { AddCertificateComponent } from './add-certificate/add-certificate.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { FormsModule } from '@angular/forms';
import { KSPasswordModalDialog } from './ks-password-modal-dialog/ks-modal-dialog.component';
import { AdminToolbarComponent } from './admin-toolbar/admin-toolbar.component';
import { RegistrationRequestsComponent } from './registration-requests/registration-requests.component';

const routes: Routes = [
    {path:'admin-home', component: AdminHomeComponent},
    {path:'add-certificate', component: AddCertificateComponent},
    {path:'requests', component: RegistrationRequestsComponent}
  ];
  

@NgModule({
  declarations: [
    AddCertificateComponent,
    AdminHomeComponent,
    KSPasswordModalDialog,
    AdminToolbarComponent,
    RegistrationRequestsComponent
  ],
  imports: [
    AppRoutingModule,
    MaterialModule,
    FormsModule,
    RouterModule.forChild(routes)
  ]
})
export class AdminModule { }