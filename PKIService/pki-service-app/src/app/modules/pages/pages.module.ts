import { NgModule } from '@angular/core';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';
import { MaterialModule } from '../material/material.module';
import { FormsModule } from '@angular/forms';
import { RegistrationPageComponent } from './registration-page/registration-page.component';

const routes: Routes = [
    {path:'', component: LoginPageComponent},
    {path:'registration', component: RegistrationPageComponent}
  ];
  

@NgModule({
  declarations: [
    LoginPageComponent,
    RegistrationPageComponent
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