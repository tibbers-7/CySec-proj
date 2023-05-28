import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RegistrationRequest } from 'src/app/model/registrationRequest';

@Component({
  selector: 'app-registration-request-dialog',
  templateUrl: './registration-request-dialog.component.html',
  styleUrls: ['./registration-request-dialog.component.css']
})
export class RegistrationRequestDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<RegistrationRequestDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {registrationRequest: RegistrationRequest},
  ) {}
  declineRequest(){

  }
  acceptRequest(){

  }
  
}
