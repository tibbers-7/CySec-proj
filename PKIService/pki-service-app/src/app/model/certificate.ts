export class Certificate{
    id: number = 0

    public constructor(obj? : Certificate){
        if(obj){
            this.id = obj.id
        }
    }
}