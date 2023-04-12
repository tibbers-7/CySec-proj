import { Component, OnInit } from '@angular/core';
import { Certificate } from 'src/app/model/certificate';
import { CertificateService } from 'src/app/services/certificate.service';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {

  dataSource : Certificate[] = []
  displayedColumns = ['issuer','subject','type','status','revoke-button']
  
  public constructor(private certificateService: CertificateService){}
 
  ngOnInit(): void{
    this.certificateService.getAllCertificates().subscribe(res=>{
      this.dataSource = res.data
    })
  }

  revokeCertificate(certificate: Certificate){
  this.certificateService.revokeCertificate(certificate.id).subscribe(res=>{
  console.log(res)
  })
  }
}
