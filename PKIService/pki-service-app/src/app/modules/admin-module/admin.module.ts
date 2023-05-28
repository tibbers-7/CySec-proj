import { NgModule } from '@angular/core';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { MaterialModule } from '../material/material.module';
import { RouterModule, Routes } from '@angular/router';
import { AddCertificateComponent } from './add-certificate/add-certificate.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { FormsModule } from '@angular/forms';
import { KSPasswordModalDialog } from './ks-password-modal-dialog/ks-modal-dialog.component';
import { AdminToolbarComponent } from './admin-toolbar/admin-toolbar.component';
import { ProjectsViewComponent } from './projects-view/projects-view.component';
import { AddProjectComponent } from './add-project/add-project.component';
import { EditProjectComponent } from './edit-project/edit-project.component';
import { EmployeesOnProjectViewComponent } from './employees-on-project-view/employees-on-project-view.component';
import { AddEngineerOnProjectComponent } from './add-engineer-on-project/add-engineer-on-project.component';
import { EditEngineerOnProjectComponent } from './edit-engineer-on-project/edit-engineer-on-project.component';
import { RegistrationRequestsComponent } from './registration-requests/registration-requests.component';
import { RoleGuard } from 'src/app/auth/role-guard';
import { RolesAndPermissionsComponent } from './roles-and-permissions/roles-and-permissions.component';
import { RegistrationRequestDialogComponent } from './registration-request-dialog/registration-request-dialog.component';
import { EmployeesViewComponent } from './employees-view/employees-view.component';
import { AddEmployeesComponent } from './add-employees/add-employees.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';

const routes: Routes = [
    {path:'admin-home', component: AdminHomeComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ADMIN' }},
    {path:'add-certificate', component: AddCertificateComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ADMIN' }},
    {path:'requests', component: RegistrationRequestsComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ADMIN' }},
    {path:'projects', component: ProjectsViewComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ADMIN' }},
    {path: 'projects/create', component: AddProjectComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ADMIN' }},
    {path: 'projects/:id/update', component: EditProjectComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ADMIN' }},
    {path: 'projects/:id/employees', component: EmployeesOnProjectViewComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ADMIN' }},
    {path: 'projects/:id/employees/add', component: AddEngineerOnProjectComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ADMIN' }},
    {path: 'projects/:id/employees/update/:idw', component: EditEngineerOnProjectComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ADMIN' }},
    {path:'permissions', component: RolesAndPermissionsComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ADMIN' }},
    {path:'employees', component: EmployeesViewComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ADMIN' }},
    {path:'employees/create', component: AddEmployeesComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ADMIN' }},
    {path: 'editProfile', component: EditProfileComponent}

  ];
  

@NgModule({
  declarations: [
    AddCertificateComponent,
    AdminHomeComponent,
    KSPasswordModalDialog,
    AdminToolbarComponent,
    ProjectsViewComponent,
    AddProjectComponent,
    EditProjectComponent,
    EmployeesOnProjectViewComponent,
    AddEngineerOnProjectComponent,
    EditEngineerOnProjectComponent,
    RegistrationRequestsComponent,
    RolesAndPermissionsComponent,
    RegistrationRequestDialogComponent,
    EmployeesViewComponent,
    AddEmployeesComponent,
    EditProfileComponent
  ],
  imports: [
    AppRoutingModule,
    MaterialModule,
    FormsModule,
    RouterModule.forChild(routes)
  ]
})
export class AdminModule { }