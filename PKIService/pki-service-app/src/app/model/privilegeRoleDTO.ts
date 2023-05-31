import { Permission } from "./permission"

export class PrivilegeRoleDTO{
    roleName = ''
    privilege = new Permission()

    public constructor(obj? : PrivilegeRoleDTO){
        if(obj){
            this.roleName = obj.roleName
            this.privilege = obj.privilege
        }
    }
}