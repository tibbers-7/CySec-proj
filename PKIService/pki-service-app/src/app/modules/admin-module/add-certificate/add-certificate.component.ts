import { Component } from '@angular/core';
import * as moment from 'moment';
import { DigitalEntity } from 'src/app/model/digitalEntity';
import { NewCertificateRequestData } from 'src/app/model/newCertificateRequestData';
import { CertificateService } from 'src/app/services/certificate.service';

@Component({
  selector: 'app-add-certificate',
  templateUrl: './add-certificate.component.html',
  styleUrls: ['./add-certificate.component.css']
})
export class AddCertificateComponent {

  selectedCertificateType : string = ''
  selectedIssuer : DigitalEntity | undefined
  SSFormData : NewCertificateRequestData = new NewCertificateRequestData()
  EEAndCAFormData : NewCertificateRequestData = new NewCertificateRequestData()
  public constructor(private certificateService: CertificateService){}

  onInit(){
  }

  submitCAEEForm(){
  if(this.selectedIssuer) this.EEAndCAFormData.issuer = this.selectedIssuer
  else return
  this.EEAndCAFormData.certificateRole = this.selectedCertificateType
  //this.certificateService.issueCertificate()
  console.log(this.EEAndCAFormData)
  }
  submitSSForm(){
    this.SSFormData.issuer = this.SSFormData.subject
    this.SSFormData.certificateRole = this.selectedCertificateType
    //this.certificateService.issueCertificate()
    console.log(this.SSFormData)
  }

}
