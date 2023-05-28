export class RegularLogInRequestData {
    username: string  = ''
    password: string = ''

    public constructor(obj? : RegularLogInRequestData){
        if(obj){
            this.username = obj?.username
            this.password = obj?.password
        }
    }
}