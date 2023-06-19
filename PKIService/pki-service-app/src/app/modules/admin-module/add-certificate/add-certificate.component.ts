import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { DigitalEntity } from 'src/app/model/digitalEntity';
import { NewCertificateRequestData } from 'src/app/model/newCertificateRequestData';
import { CertificateService } from 'src/app/services/certificate.service';
import { KSPasswordModalDialog } from '../ks-password-modal-dialog/ks-modal-dialog.component';

@Component({
  selector: 'app-add-certificate',
  templateUrl: './add-certificate.component.html',
  styleUrls: ['./add-certificate.component.css']
})
export class AddCertificateComponent implements OnInit {

  selectedCertificateType : string = ''
  allIssuers : DigitalEntity[] = []
  selectedIssuer : DigitalEntity | undefined
  SSFormData : NewCertificateRequestData = new NewCertificateRequestData()
  EEAndCAFormData : NewCertificateRequestData = new NewCertificateRequestData()
  keyStorePassword : string = ''
  selectedExtensions : string[] = []
  allExtensions : string[] = ['Digital Signature', 'Key Encipherment', 'Data Encipherment',
  'Key Agreement', 'Certificate Signing']

  public constructor(private certificateService: CertificateService, private toast: ToastrService, public dialog: MatDialog){}

  ngOnInit(){
  this.loadIssuers()
  }

  loadIssuers(){
    this.certificateService.getIssuers().subscribe(res=>{
      console.log(res)
      this.allIssuers = res
    })
  }

  submitCAEEForm(){
    console.log(this.EEAndCAFormData)
    if(this.prepareCAEEForm()){
      this.openDialog()
    }
  }

  prepareCAEEForm(){
    if(!this.selectedIssuer){
      this.toast.error('You have to select an issuer for this certificate!')
      return false
    }
    if(this.fieldsAreEmpty(this.EEAndCAFormData.subject)){
      this.toast.error('You have to fill all fields to continue!')
      return false
    }
    if(this.selectedExtensions.length = 0){
      this.toast.error('You have to choose extensions for the certificate!')
      return false
    }
    this.EEAndCAFormData.extensions = this.selectedExtensions
    this.EEAndCAFormData.certificateRole = this.selectedCertificateType
    this.EEAndCAFormData.issuer = this.selectedIssuer
    return true
  }
 
  submitSSForm(){
    console.log(this.SSFormData)
    if(this.prepareSSForm()){
      this.openDialog()
    }
  }

  prepareSSForm(){
    if(this.fieldsAreEmpty(this.SSFormData.subject)){
      this.toast.error('You have to fill all fields to continue!')
      return false
    }
    
    this.SSFormData.extensions = this.selectedExtensions
    this.SSFormData.issuer = this.SSFormData.subject
    this.SSFormData.certificateRole = this.selectedCertificateType
    
    return true
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(KSPasswordModalDialog, {
      data:{ keyStorePass: this.keyStorePassword},
    })

    dialogRef.afterClosed().subscribe(result => {
      this.keyStorePassword = result
      if(this.keyStorePassword !== 'sifra') this.toast.error('You entered the wrong password.')
      else{
        if(this.selectedCertificateType === 'SELF_SIGNED') 
          this.certificateService.issueCertificate(this.SSFormData, this.keyStorePassword).subscribe(res=>{
           this.toast.success('Successfully issued certificate!')
           this.loadIssuers()
        })
        else this.certificateService.issueCertificate(this.EEAndCAFormData, this.keyStorePassword).subscribe(res=>{
          this.toast.success('Successfully issued certificate!')
          this.loadIssuers()
        })
      }
    })
  }

  fieldsAreEmpty(object: Object) { 
    return Object.values(object).some(
        value => {
        if (value === null || value === '')  return true
        return false
      })
  }

}
