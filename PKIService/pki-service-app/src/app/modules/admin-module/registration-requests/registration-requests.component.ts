import { DataSource } from '@angular/cdk/collections';
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RegistrationRequest } from 'src/app/model/registrationRequest';
import { KSPasswordModalDialog } from '../ks-password-modal-dialog/ks-modal-dialog.component';
import { RegistrationRequestDialogComponent } from '../registration-request-dialog/registration-request-dialog.component';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-registration-requests',
  templateUrl: './registration-requests.component.html',
  styleUrls: ['./registration-requests.component.css']
})
export class RegistrationRequestsComponent {
  
  displayedColumns: string[] = ['username', 'status', 'click'];
  isDeclined = false;
  isResolved = false;
  requestsDataSource = [
    {username: 'Hydrogen', status: 1.0079},
    {username: 'Helium', status: 4.0026},
    {username: 'Lithium', status: 6.941},
    {username: 'Beryllium', status: 9.0122},
    {username: 'Boron', status: 10.811},
    {username: 'Carbon', status: 12.0107},
    {username: 'Nitrogen', status: 14.0067},
    {username: 'Oxygen', status: 15.9994},
    {username: 'Fluorine', status: 18.9984},
    {username: 'Neon', status: 20.1797},
  ]

  public constructor(public dialog: MatDialog, private authService : AuthenticationService){}
 // nesto = new DataSource<RegistrationRequest>()
  getDetailsOfRequest(request: RegistrationRequest): void{
    const dialogRef = this.dialog.open(RegistrationRequestDialogComponent, {
      data:{ registrationRequest: request, isDeclined : this.isDeclined, isResolved: this.isResolved},
    })
    dialogRef.afterClosed().subscribe(result => {
      console.log(result)
        if(result.isResolved){
          if(result.isDeclined) console.log("afafafa")//this.authService.approveRequest(request.id)
          //else this.authService.cancelRequest(request.id)
        }
      
      }
    )
  }

}
