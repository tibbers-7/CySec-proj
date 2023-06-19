import { Component , Inject} from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EmployeeDTO } from 'src/app/model/employee-dto';


@Component({
  selector: 'app-control-user-dialog',
  templateUrl: './control-user-dialog.component.html',
  styleUrls: ['./control-user-dialog.component.css']
})
export class ControlUserDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<ControlUserDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {employee: EmployeeDTO, isBlocked: boolean, isResolved: boolean, reasoning : string}
  ) {}
  cancel(){
    this.data.isResolved = false
    this.dialogRef.close(this.data)
  }

  blockUser(){
    this.data.isResolved = true
    this.data.isBlocked = true
    this.dialogRef.close(this.data)
  }
  blockRefreshTokens(){
    this.data.isResolved = true
    this.data.isBlocked = false
    this.dialogRef.close(this.data)
  }
  

}
