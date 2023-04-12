import { Component } from '@angular/core';
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
export class AddCertificateComponent {

  selectedCertificateType : string = ''
  selectedIssuer : DigitalEntity | undefined
  SSFormData : NewCertificateRequestData = new NewCertificateRequestData()
  EEAndCAFormData : NewCertificateRequestData = new NewCertificateRequestData()
  keyStorePassword : string = ''

  public constructor(private certificateService: CertificateService, private toast: ToastrService, public dialog: MatDialog){}

  onInit(){
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
    this.EEAndCAFormData.certificateRole = this.selectedCertificateType
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
            console.log(res)
        })
        else this.certificateService.issueCertificate(this.EEAndCAFormData, this.keyStorePassword).subscribe(res=>{
          console.log(res)
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
