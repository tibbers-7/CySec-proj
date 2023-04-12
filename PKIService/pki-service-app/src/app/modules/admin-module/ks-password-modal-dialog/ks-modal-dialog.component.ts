import { Component, Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";

@Component({
    selector: 'ks-modal-dialog',
    templateUrl: './ks-modal-dialog.component.html',
    styleUrls: ['./ks-modal-dialog.component.css']
  })
  export class KSPasswordModalDialog {

    constructor(
      public dialogRef: MatDialogRef<KSPasswordModalDialog>,
      @Inject(MAT_DIALOG_DATA) public data: {keyStorePass: string},
    ) {}
  
    onNoClick(): void {
      this.dialogRef.close();
    }
  }