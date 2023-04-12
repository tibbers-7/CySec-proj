import { Component } from '@angular/core';
import { DigitalEntity } from 'src/app/model/digitalEntity';

@Component({
  selector: 'app-add-certificate',
  templateUrl: './add-certificate.component.html',
  styleUrls: ['./add-certificate.component.css']
})
export class AddCertificateComponent {

  selectedCertificateType : string = ''
  selectedIssuer : DigitalEntity | undefined
  public constructor(){}

  onInit(){
  }

}
