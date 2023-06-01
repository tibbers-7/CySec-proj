import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-passwordless-login-page',
  templateUrl: './passwordless-login-page.component.html',
  styleUrls: ['./passwordless-login-page.component.css']
})
export class PasswordlessLoginPageComponent {

  public constructor(private route: ActivatedRoute, private toast : ToastrService, private authService: AuthenticationService, private router : Router){}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
        console.log(params); 
        if(!params['username'] || !params['token']) this.toast.error("Link is not valid!")
        this.authService.activateMagicLoginLink(params['token'], params['username']).subscribe(res=>{
          this.authService.setSession(res);
          if(this.authService.isLoggedIn()) this.router.navigate(['home'])
          else this.toast.error('Something went wrong, try again!')
        
      })
      })
    }
 
}
