export class PrivilegeRoleDTO{
    roleName = ''
    privilegeName = ''

    public constructor(obj? : PrivilegeRoleDTO){
        if(obj){
            this.roleName = obj.roleName
            this.privilegeName = obj.privilegeName
        }
    }
}