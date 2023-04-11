export class LogInRequestData {
    username: string  = ''
    password: string = ''

    public constructor(obj? : LogInRequestData){
        if(obj){
            this.username = obj?.username
            this.password = obj?.password
        }
    }
}