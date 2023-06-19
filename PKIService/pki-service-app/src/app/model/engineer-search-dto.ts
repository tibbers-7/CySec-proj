export class EngineerSearchDTO{
    username = ''
    name = ''
    surname = ''
    numberOfMonthsEmployed = 0
    public constructor(obj? :EngineerSearchDTO){
        if(obj){
            this.username = obj.username
            this.name = obj.name
            this.surname = obj.surname
            this.numberOfMonthsEmployed = obj.numberOfMonthsEmployed
        }
    }
}