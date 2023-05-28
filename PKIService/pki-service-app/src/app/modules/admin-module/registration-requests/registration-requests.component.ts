import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RegistrationRequest } from 'src/app/model/registrationRequest';
import { RegistrationRequestDialogComponent } from '../registration-request-dialog/registration-request-dialog.component';
import { MatTableDataSource } from '@angular/material/table';
import { UserService } from 'src/app/services/user.service';
import { RegistrationCancellationRequest } from 'src/app/model/registrationCancellationRequest';
import { RegistrationApprovalRequest } from 'src/app/model/registrationApprovalRequest';
@Component({
  selector: 'app-registration-requests',
  templateUrl: './registration-requests.component.html',
  styleUrls: ['./registration-requests.component.css']
})
export class RegistrationRequestsComponent implements OnInit{
  
  displayedColumns: string[] = ['username', 'status', 'click'];
  isDeclined = false
  isResolved = false
  reasoning = ""
  requestsDataSource = new MatTableDataSource<RegistrationRequest>()
  requests :RegistrationRequest[] = []
 
  public constructor(public dialog: MatDialog, private userService : UserService){}
 
  ngOnInit(){
    this.loadRequests()
  }

    loadRequests(){
      this.userService.getRegistrationRequests().subscribe(res => {
        this.requests = res;
        this.requestsDataSource.data = this.requests;
        console.log(this.requests)
      })
    }
  getDetailsOfRequest(request: RegistrationRequest): void{
    const dialogRef = this.dialog.open(RegistrationRequestDialogComponent, {
      data:{ registrationRequest: request, isDeclined : this.isDeclined, isResolved: this.isResolved, reasoning : this.reasoning},
    })
    dialogRef.afterClosed().subscribe(result => {
      console.log(result)
        if(result.isResolved){
          if(result.isDeclined){
            this.createCancellationRequest(request.id,result.reasoning)
           
          } else  this.createApprovalRequest(request.id, result.reasoning)
        }
      this.reasoning = ""
      this.isResolved = false
      }
    )
  }

  createCancellationRequest(requestId :number, reasoning :string){
    let requestCancel = new RegistrationCancellationRequest()
    requestCancel.idOfRequest = requestId
    requestCancel.cancellationDescription = reasoning
    this.userService.declineRegistrationRequest(requestCancel).subscribe(res=>{
      this.loadRequests()
      
    })
  }

  createApprovalRequest(requestId: number, reasoning: string){
    let requestApprove = new RegistrationApprovalRequest()
    requestApprove.approvalDescription = reasoning
    requestApprove.idOfRequest = requestId
    this.userService.acceptRegistrationRequest(requestApprove).subscribe(res=>{
        this.loadRequests()
    })
  }

}
