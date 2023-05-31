import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Permission } from 'src/app/model/permission';
import { PermissionsService } from 'src/app/services/permissions.service';
import { PrivilegeDialogComponent } from '../privilege-dialog/privilege-dialog.component';
import { PrivilegeRoleDTO } from 'src/app/model/privilegeRoleDTO';

@Component({
  selector: 'app-roles-and-permissions',
  templateUrl: './roles-and-permissions.component.html',
  styleUrls: ['./roles-and-permissions.component.css']
})
export class RolesAndPermissionsComponent implements OnInit {

  allPermissions : Permission[] = []
  engineerPermissions : Permission[] = []
  HRPermissions : Permission[] = []
  PRPermissions : Permission[] = []


  public constructor(public dialog: MatDialog, private privilegeService : PermissionsService){}

  ngOnInit(): void {

    this.loadInfo()
     }

  loadInfo(){
    this.privilegeService.getAllPrivileges().subscribe(res => this.allPermissions = res)
    this.privilegeService.getPrivilegesForEngineers().subscribe( res => this.engineerPermissions = res)
    this.privilegeService.getPrivilegesForHRManagers().subscribe( res => this.HRPermissions = res)
    this.privilegeService.getPrivilegesForPRManagers().subscribe( res => this.PRPermissions = res)
 
  }


  addDialogForEngineer(){
    const dialogRef = this.dialog.open(PrivilegeDialogComponent, {
      data:{ oldPrivilege: '', newPrivilege: ''},
    })
    dialogRef.afterClosed().subscribe(result => {
        if(result.newPrivilege ==='') return
        let dto = new PrivilegeRoleDTO()
        dto.roleName = 'ENGINEER'
        dto.privilegeName = result.newPrivilege
        this.privilegeService.addPrivilegeToRole(dto).subscribe(res =>{
          this.loadInfo()
        })

       
      })
  }

  addDialogForHR(){
    const dialogRef = this.dialog.open(PrivilegeDialogComponent, {
      data:{ oldPrivilege: '', newPrivilege: ''},
    })
    dialogRef.afterClosed().subscribe(result => {
        if(result.newPrivilege ==='') return
        let dto = new PrivilegeRoleDTO()
        dto.roleName = 'HR_MANAGER'
        dto.privilegeName = result.newPrivilege
        this.privilegeService.addPrivilegeToRole(dto).subscribe(res =>{
          this.loadInfo()
        })

       
      })
  }

  addDialogForPR(){
    const dialogRef = this.dialog.open(PrivilegeDialogComponent, {
      data:{ oldPrivilege: '', newPrivilege: ''},
    })
    dialogRef.afterClosed().subscribe(result => {
        if(result.newPrivilege ==='') return
        let dto = new PrivilegeRoleDTO()
        dto.roleName = 'PROJECT_MANAGER'
        dto.privilegeName = result.newPrivilege
        this.privilegeService.addPrivilegeToRole(dto).subscribe(res =>{
          this.loadInfo()
        })

       
      })
  }

  editDialogForEngineer(privilege : Permission){
    const dialogRef = this.dialog.open(PrivilegeDialogComponent, {
      data:{ oldPrivilege: privilege.name , newPrivilege: ''},
    })
    dialogRef.afterClosed().subscribe(result => {
        if(result.newPrivilege ==='') return
          privilege.name = result.newPrivilege
        this.privilegeService.editPrivilege(privilege).subscribe(res =>{
          this.loadInfo()
        })

       
      })
  }

  editDialogForHR(privilege : Permission){
    const dialogRef = this.dialog.open(PrivilegeDialogComponent, {
      data:{ oldPrivilege: privilege.name, newPrivilege: ''},
    })
    dialogRef.afterClosed().subscribe(result => {
        if(result.newPrivilege ==='') return
          privilege.name = result.newPrivilege
        this.privilegeService.editPrivilege(privilege).subscribe(res =>{
          this.loadInfo()
        })

       
      })
  }

  editDialogForPR(privilege : Permission){
    const dialogRef = this.dialog.open(PrivilegeDialogComponent, {
      data:{ oldPrivilege: privilege.name, newPrivilege: ''},
    })
    dialogRef.afterClosed().subscribe(result => {
        if(result.newPrivilege ==='') return
          privilege.name = result.newPrivilege
        this.privilegeService.editPrivilege(privilege).subscribe(res =>{
          this.loadInfo()
        })

       
      })
  }

  removeForEngineer(privilege : Permission){
    let dto = new PrivilegeRoleDTO()
    dto.roleName = 'ENGINEER'
    dto.privilegeName = privilege.name
    this.privilegeService.removePrivilegeFromRole(dto).subscribe(
      res=>
       {
        console.log(res)
       }
    )
  }
  
  removeForHR(privilege : Permission){
    let dto = new PrivilegeRoleDTO()
    dto.roleName = 'HR_MANAGER'
    dto.privilegeName = privilege.name
    this.privilegeService.removePrivilegeFromRole(dto).subscribe( res=>
      {
       console.log(res)
      }
   )
  }

  removeForPR(privilege : Permission){
    let dto = new PrivilegeRoleDTO()
    dto.roleName = 'PROJECT_MANAGER'
    dto.privilegeName = privilege.name
    this.privilegeService.removePrivilegeFromRole(dto).subscribe( res=>
      {
       console.log(res)
      }
   )
  }
 


}
