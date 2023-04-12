import { NgModule } from '@angular/core';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';
import { MaterialModule } from '../material/material.module';
import { FormsModule } from '@angular/forms';

const routes: Routes = [
    {path:'', component: LoginPageComponent},
  ];
  

@NgModule({
  declarations: [
    LoginPageComponent
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