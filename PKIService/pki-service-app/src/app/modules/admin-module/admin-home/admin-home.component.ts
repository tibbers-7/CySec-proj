import { Component } from '@angular/core';
import { Certificate } from 'src/app/model/certificate';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent {

  dataSource : Certificate[] = []
  displayedColumns = ['issuer','subject','type','status','revoke-button']

  revokeCertificate(certificate: any){

  }
}
