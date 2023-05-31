import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-privilege-dialog',
  templateUrl: './privilege-dialog.component.html',
  styleUrls: ['./privilege-dialog.component.css']
})
export class PrivilegeDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<PrivilegeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {oldPrivilege: String, newPrivilege : String}
  ) {}

  cancel(){
   this.data.newPrivilege=''
   this.dialogRef.close(this.data)
  }

  submit(){
    this.data.newPrivilege = this.data.oldPrivilege
    this.dialogRef.close(this.data)
  }
}
