import { NgModule } from '@angular/core';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { MaterialModule } from '../material/material.module';
import { RouterModule, Routes } from '@angular/router';
import { AddCertificateComponent } from './add-certificate/add-certificate.component';
import { CertificatesComponent } from './certificates/certificates.component';
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
import { PrivilegeDialogComponent } from './privilege-dialog/privilege-dialog.component';
import { CombinedSearchComponent } from './combined-search/combined-search.component';

const routes: Routes = [
    {path:'certificates', component: CertificatesComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ROLE_ADMIN' }},
    {path:'add-certificate', component: AddCertificateComponent,
    canActivate:[RoleGuard], data: {expectedRole: 'ROLE_ADMIN'}},
    {path:'requests', component: RegistrationRequestsComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ROLE_ADMIN' }},
    {path:'projects', component: ProjectsViewComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ROLE_ADMIN' }},
    {path: 'projects/create', component: AddProjectComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ROLE_ADMIN' }},
    {path: 'projects/:id/update', component: EditProjectComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ROLE_ADMIN' }},
    {path: 'projects/:id/employees', component: EmployeesOnProjectViewComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ROLE_ADMIN' }},
    {path: 'projects/:id/employees/add', component: AddEngineerOnProjectComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ROLE_ADMIN' }},
    {path: 'projects/:id/employees/update/:idw', component: EditEngineerOnProjectComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ROLE_ADMIN' }},
    {path:'permissions', component: RolesAndPermissionsComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ROLE_ADMIN' }},
    {path:'employees', component: EmployeesViewComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ROLE_ADMIN' }},
    {path:'employees/create', component: AddEmployeesComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ROLE_ADMIN' }},
    {path: 'editProfile', component: EditProfileComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ROLE_ADMIN' }},
    {path: 'engineers', component:CombinedSearchComponent}

  ];


@NgModule({
  declarations: [
    AddCertificateComponent,
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
    EditProfileComponent,
    CertificatesComponent,
    PrivilegeDialogComponent,
    CombinedSearchComponent
  ],
  imports: [
    AppRoutingModule,
    MaterialModule,
    RouterModule.forChild(routes)
  ],
  exports: [
    AdminToolbarComponent
  ]
})
export class AdminModule { }
