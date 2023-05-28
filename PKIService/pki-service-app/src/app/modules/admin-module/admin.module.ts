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
import { RoleGuard } from 'src/app/auth/role-guard';
import { RolesAndPermissionsComponent } from './roles-and-permissions/roles-and-permissions.component';
import { RegistrationRequestDialogComponent } from './registration-request-dialog/registration-request-dialog.component';

const routes: Routes = [
    {path:'admin-home', component: AdminHomeComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ADMIN' } },
    {path:'add-certificate', component: AddCertificateComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ADMIN' }},
    {path:'requests', component: RegistrationRequestsComponent},
    //{path:'projects', component: ProjectsViewComponent},
    //{path: 'projects/create', component: AddProjectComponent},
    //{path: 'projects/:id/update', component: EditProjectComponent},
    //{path: 'projects/:id/employees', component: EmployeesOnProjectViewComponent},
    //{path: 'projects/:id/employees/add', component: AddEngineerOnProjectComponent},
    //{path: 'projects/:id/employees/update/:idw', component: EditEngineerOnProjectComponent},
    //{path:'permissions', component: RolesAndPermissionsComponent},
  ];
  

@NgModule({
  declarations: [
    AddCertificateComponent,
    AdminHomeComponent,
    KSPasswordModalDialog,
    AdminToolbarComponent,
    RegistrationRequestsComponent,
    RolesAndPermissionsComponent,
    RegistrationRequestDialogComponent
  ],
  imports: [
    AppRoutingModule,
    MaterialModule,
    FormsModule,
    RouterModule.forChild(routes)
  ]
})
export class AdminModule { }