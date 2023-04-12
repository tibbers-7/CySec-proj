import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent {

  dataSource = []
  displayedColumns = ['issuer','subject','type','status','revoke-button']

  revokeCertificate(certificate: any){

  }
}
