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
  chosenForEngineer = new Permission()
  chosenForHR = new Permission()
  chosenForPR = new Permission()


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
    let unusedPrivileges = this.allPermissions.filter(x => !this.engineerPermissions.includes(x))
    const dialogRef = this.dialog.open(PrivilegeDialogComponent, {
      data:{ unusedPrivileges : unusedPrivileges },
    })
    dialogRef.afterClosed().subscribe(result => {
      console.log(result)
        if(!result) return
        let dto = new PrivilegeRoleDTO()
        dto.roleName = 'ROLE_ENGINEER'
        dto.privilege = result.chosenPrivilege
        this.privilegeService.addPrivilegeToRole(dto).subscribe(res =>{
          this.loadInfo()
        })

       
      })
  }

  addDialogForHR(){
    let unusedPrivileges = this.allPermissions.filter(x => !this.HRPermissions.includes(x))
    const dialogRef = this.dialog.open(PrivilegeDialogComponent, {
      data:{  unusedPrivileges : unusedPrivileges },
    })
    dialogRef.afterClosed().subscribe(result => {
        if(!result) return
        let dto = new PrivilegeRoleDTO()
        dto.roleName = 'ROLE_HR_MANAGER'
        dto.privilege = result.chosenPrivilege
        this.privilegeService.addPrivilegeToRole(dto).subscribe(res =>{
          this.loadInfo()
        })

       
      })
  }

  addDialogForPR(){
    let unusedPrivileges = this.allPermissions.filter( x => !this.PRPermissions.includes(x))
    const dialogRef = this.dialog.open(PrivilegeDialogComponent, {
      data:{ unusedPrivileges : unusedPrivileges },
    })
    dialogRef.afterClosed().subscribe(result => {
        if(!result.newPrivilege) return
        let dto = new PrivilegeRoleDTO()
        dto.roleName = 'ROLE_PROJECT_MANAGER'
        dto.privilege = result.chosenPrivilege
        this.privilegeService.addPrivilegeToRole(dto).subscribe(res =>{
          this.loadInfo()
        })

       
      })
  }


  removeForEngineer(){
    let dto = new PrivilegeRoleDTO()
    dto.roleName = 'ROLE_ENGINEER'
    dto.privilege = this.chosenForEngineer
    this.privilegeService.removePrivilegeFromRole(dto).subscribe(
      res=>
       {
        console.log(res)
       }
    )
  }
  
  removeForHR(){
    let dto = new PrivilegeRoleDTO()
    dto.roleName = 'ROLE_HR_MANAGER'
    dto.privilege = this.chosenForHR
    this.privilegeService.removePrivilegeFromRole(dto).subscribe( res=>
      {
       console.log(res)
      }
   )
  }

  removeForPR(){
    let dto = new PrivilegeRoleDTO()
    dto.roleName = 'ROLE_PROJECT_MANAGER'
    dto.privilege = this.chosenForPR
    this.privilegeService.removePrivilegeFromRole(dto).subscribe( res=>
      {
       console.log(res)
      }
   )
  }
 


}
