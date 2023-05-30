import { Component } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';
import { Certificate } from 'src/app/model/certificate';
import { CertificateService } from 'src/app/services/certificate.service';

@Component({
  selector: 'app-certificates',
  templateUrl: './certificates.component.html',
  styleUrls: ['./certificates.component.css']
})
export class CertificatesComponent {
  dataSource = new MatTableDataSource<Certificate>()
  displayedColumns = ['issuer','subject','type','status','expiration','revoke-button']
  
  public constructor(private certificateService: CertificateService, private toast: ToastrService){}
 
  ngOnInit(): void{
    this.loadCertificates()
  }

  loadCertificates(): void {
    this.certificateService.getAllCertificates().subscribe(res=>{
      console.log(res)
      this.dataSource = res
    })
  }

  revokeCertificate(certificate: Certificate){
  this.certificateService.revokeCertificate(certificate.id).subscribe(res=>{
  console.log(res)
  this.toast.success('Certificate successfully revoked!')
  this.loadCertificates()
  })
  }
}
