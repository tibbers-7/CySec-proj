import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Permission } from 'src/app/model/permission';
import { PermissionsService } from 'src/app/services/permissions.service';
import { PrivilegeDialogComponent } from '../privilege-dialog/privilege-dialog.component';
import { PrivilegeRoleDTO } from 'src/app/model/privilegeRoleDTO';
import { ToastrService } from 'ngx-toastr';

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
  chosenForEngineer : Permission[] = []
  chosenForHR : Permission[] = []
  chosenForPR : Permission[] = []


  public constructor(public dialog: MatDialog, private toast: ToastrService, private privilegeService : PermissionsService){}

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
      data:{ unusedPrivileges : this.removeDuplicates(this.engineerPermissions) },
    })
    dialogRef.afterClosed().subscribe(result => {
      console.log(result)
        if(!result) return
        let dto = new PrivilegeRoleDTO()
        dto.roleName = 'ROLE_ENGINEER'
        dto.privilege = result[0]
        this.privilegeService.addPrivilegeToRole(dto).subscribe(res =>{
          this.engineerPermissions.push(dto.privilege)
          this.toast.success('Added permission!')
        })

       
      })
  }

  addDialogForHR(){
    const dialogRef = this.dialog.open(PrivilegeDialogComponent, {
      data:{  unusedPrivileges : this.removeDuplicates(this.HRPermissions) },
    })
    dialogRef.afterClosed().subscribe(result => {
        if(!result) return
        let dto = new PrivilegeRoleDTO()
        dto.roleName = 'ROLE_HR_MANAGER'
        dto.privilege = result[0]
        this.privilegeService.addPrivilegeToRole(dto).subscribe(res =>{
          this.HRPermissions.push(dto.privilege)
          this.toast.success('Added permission!')
        })

       
      })
  }

  addDialogForPR(){
    let unusedPrivileges = this.allPermissions.filter( x => !this.PRPermissions.includes(x))
    const dialogRef = this.dialog.open(PrivilegeDialogComponent, {
      data:{ unusedPrivileges : this.removeDuplicates(this.PRPermissions) },
    })

    dialogRef.afterClosed().subscribe(result => {
        if(!result.newPrivilege) return
        let dto = new PrivilegeRoleDTO()
        dto.roleName = 'ROLE_PROJECT_MANAGER'
        dto.privilege = result[0]
        this.privilegeService.addPrivilegeToRole(dto).subscribe(res =>{
          this.PRPermissions.push(dto.privilege)
          this.toast.success('Added permission!')
        })

       
      })
  }


  removeForEngineer(){
    let dto = new PrivilegeRoleDTO()
    dto.roleName = 'ROLE_ENGINEER'
    dto.privilege = this.chosenForEngineer[0]
    this.privilegeService.removePrivilegeFromRole(dto).subscribe(
      res=>
       {
        this.toast.success('successfully removed permission')
        this.engineerPermissions = this.engineerPermissions.filter(x => x !== dto.privilege)
       }
    )
  }
  
  removeForHR(){
    let dto = new PrivilegeRoleDTO()
    dto.roleName = 'ROLE_HR_MANAGER'
    dto.privilege = this.chosenForHR[0]
    this.privilegeService.removePrivilegeFromRole(dto).subscribe( res=>
      {
       this.toast.success('successfully removed permission')
       this.HRPermissions = this.HRPermissions.filter(x => x !== dto.privilege)
      }
   )
  }

  removeForPR(){
    let dto = new PrivilegeRoleDTO()
    dto.roleName = 'ROLE_PROJECT_MANAGER'
    console.log(this.chosenForPR)
    dto.privilege = this.chosenForPR[0]
    this.privilegeService.removePrivilegeFromRole(dto).subscribe( res=>
      {
        this.toast.success('successfully removed permission')
        this.PRPermissions = this.PRPermissions.filter(x=> x!==dto.privilege)
      }
   )
  }
 
removeDuplicates(arr2: Permission[]){
  return this.allPermissions.filter(obj =>
    !arr2.some(obj2 => obj.id == obj2.id))
}

}
