import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-passwordless-login-page',
  templateUrl: './passwordless-login-page.component.html',
  styleUrls: ['./passwordless-login-page.component.css']
})
export class PasswordlessLoginPageComponent {
  token = ""
  username = ""
  hmac = ""

  public constructor(private route: ActivatedRoute){}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
        console.log(params); 
      })
    }
}
