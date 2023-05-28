import { NgModule } from '@angular/core';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';
import { MaterialModule } from '../material/material.module';
import { FormsModule } from '@angular/forms';
import { RegistrationPageComponent } from './registration-page/registration-page.component';
import { ActivateAccountPageComponent } from './activate-account-page/activate-account-page.component';
import { PasswordlessLoginPageComponent } from './passwordless-login-page/passwordless-login-page.component';

const routes: Routes = [
    {path:'', component: LoginPageComponent},
    {path:'registration', component: RegistrationPageComponent},
    {path:'activate-account', component: ActivateAccountPageComponent},
    {path:'login/link', component: PasswordlessLoginPageComponent}
  ];

@NgModule({
  declarations: [
    LoginPageComponent,
    RegistrationPageComponent,
    ActivateAccountPageComponent
  ],
  imports: [
    AppRoutingModule,
    RouterModule.forChild(routes),
    MaterialModule,
    FormsModule
  ],
  providers: []
})
export class PagesModule { }