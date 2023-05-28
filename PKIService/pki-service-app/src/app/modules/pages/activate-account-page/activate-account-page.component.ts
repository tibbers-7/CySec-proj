import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-activate-account-page',
  templateUrl: './activate-account-page.component.html',
  styleUrls: ['./activate-account-page.component.css']
})
export class ActivateAccountPageComponent implements OnInit{

  isEnabled = false;
 
  public constructor(private route: ActivatedRoute, private toast : ToastrService, private authService: AuthenticationService, private router: Router){}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
        console.log(params);
        if(!params['hmac'] || !params['username'] || !params['token']) this.toast.error("Link is not valid!")
        this.authService.activateAccount(params['token'], params['username'], params['hmac']).subscribe( res =>{
          this.toast.success('Account is activated, you can now log in!');
          this.isEnabled = true;
        })
      })
    }

    redirectToLogin(){
      this.router.navigate([''])
    }
}
