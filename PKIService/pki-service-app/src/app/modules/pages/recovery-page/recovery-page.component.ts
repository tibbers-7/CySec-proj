import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-recovery-page',
  templateUrl: './recovery-page.component.html',
  styleUrls: ['./recovery-page.component.css']
})
export class RecoveryPageComponent {
  public constructor(private route: ActivatedRoute, private toast : ToastrService, private authService: AuthenticationService, private router : Router){}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
        console.log(params); 
        if(!params['username'] || !params['token']) this.toast.error("Link is not valid!")
        this.authService.validateRecoveryToken(params['token'], params['username']).subscribe(res=>{
          this.authService.setSession(res);
          if(this.authService.isLoggedIn()) this.router.navigate(['change-password'])
          else this.toast.error('Something went wrong, try again!')
        
      })
      })
    }
 
}
