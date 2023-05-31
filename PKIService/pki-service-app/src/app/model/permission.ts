export class Permission{
    id = 0
    name = ''

    public constructor(obj? : Permission){
      if(obj){
        this.id = obj.id
        this.name = obj.name
      }
    }
}