import { Component } from '@angular/core';
import { Certificate } from 'src/app/model/certificate';
import { CertificateService } from 'src/app/services/certificate.service';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent {

  dataSource : Certificate[] = []
  displayedColumns = ['issuer','subject','type','status','revoke-button']
  public constructor(private certificateService: CertificateService){}
 
  onInit(){
    this.certificateService.getAllCertificates().subscribe(res=>{
      this.dataSource = res.data
    })
  }

  revokeCertificate(certificate: Certificate){
  this.certificateService.revokeCertificate().subscribe(res=>{
  console.log(res)
})
  }
}
