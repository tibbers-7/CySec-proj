import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Permission } from 'src/app/model/permission';

@Component({
  selector: 'app-privilege-dialog',
  templateUrl: './privilege-dialog.component.html',
  styleUrls: ['./privilege-dialog.component.css']
})
export class PrivilegeDialogComponent {

  chosenPrivilege = new Permission()
  constructor(
    public dialogRef: MatDialogRef<PrivilegeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {unusedPrivileges : Permission[]}
  ) {}

  cancel(){
   this.dialogRef.close()
  }

  submit(){
    this.dialogRef.close(this.chosenPrivilege)
  }
}
