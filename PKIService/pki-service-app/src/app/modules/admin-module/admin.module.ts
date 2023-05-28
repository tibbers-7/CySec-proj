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

const routes: Routes = [
    {path:'admin-home', component: AdminHomeComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ADMIN' }},
    {path:'add-certificate', component: AddCertificateComponent,
    canActivate: [RoleGuard], data: { expectedRole: 'ADMIN' }},
    {path:'requests', component: RegistrationRequestsComponent},
    {path:'projects', component: ProjectsViewComponent},
    {path: 'projects/create', component: AddProjectComponent},
    {path: 'projects/:id/update', component: EditProjectComponent},
    {path: 'projects/:id/employees', component: EmployeesOnProjectViewComponent},
    {path: 'projects/:id/employees/add', component: AddEngineerOnProjectComponent},
    {path: 'projects/:id/employees/update/:idw', component: EditEngineerOnProjectComponent}
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