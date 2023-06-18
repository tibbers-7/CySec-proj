import { NgModule } from '@angular/core';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';
import { MaterialModule } from '../material/material.module';
import { RegistrationPageComponent } from './registration-page/registration-page.component';
import { ActivateAccountPageComponent } from './activate-account-page/activate-account-page.component';
import { PasswordlessLoginPageComponent } from './passwordless-login-page/passwordless-login-page.component';
import { HomePageComponent } from './home-page/home-page.component';
import { ManagerModule } from '../manager-module/manager.module';
import { AdminModule } from '../admin-module/admin.module';
import { EngineerModule } from '../engineer-module/engineer.module';
import { AuthGuard } from 'src/app/auth/auth-guard';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { RecoveryPageComponent } from './recovery-page/recovery-page.component';
import { HrManagerModule } from '../hr-manager-module/hr-manager.module';

const routes: Routes = [
    {path:'', component: LoginPageComponent},
    {path:'registration', component: RegistrationPageComponent},
    {path:'activate-account', component: ActivateAccountPageComponent},
    {path:'login/link', component: PasswordlessLoginPageComponent},
    {path:'login/recovery', component: RecoveryPageComponent},
    {path:'home', component: HomePageComponent,
    canActivate: [AuthGuard]}
  ];

@NgModule({
    declarations: [
        LoginPageComponent,
        RegistrationPageComponent,
        ActivateAccountPageComponent,
        HomePageComponent,
        ChangePasswordComponent,
        RecoveryPageComponent
    ],
    providers: [],
    imports: [
        AppRoutingModule,
        RouterModule.forChild(routes),
        MaterialModule,
        AdminModule,
        ManagerModule,
        EngineerModule,
        HrManagerModule
    ]
})
export class PagesModule { }