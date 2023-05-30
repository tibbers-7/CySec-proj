export class Permission{
    id = 0
    roles = ''

    public constructor(obj? : Permission){
      if(obj){
        this.id = obj.id
        this.roles = obj.roles
      }
    }
}