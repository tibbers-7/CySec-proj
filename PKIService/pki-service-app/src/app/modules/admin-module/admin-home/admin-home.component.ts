import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent {

  dataSource = [
    {issuer: 'vedgg', subject:'gsgsg',type: 'sgsgsg', status:' efafea'}
  ]
  displayedColumns = ['issuer','subject','type','status','revoke-button']

  revokeCertificate(certificate: any){

  }
}
