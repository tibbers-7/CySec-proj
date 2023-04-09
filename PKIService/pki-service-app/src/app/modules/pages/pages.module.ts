import { NgModule } from '@angular/core';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';

const routes: Routes = [
    {path:'login', component: LoginPageComponent},
  ];
  

@NgModule({
  declarations: [
    LoginPageComponent
  ],
  imports: [
    AppRoutingModule,
    RouterModule.forChild(routes)
  ],
  providers: []
})
export class PagesModule { }