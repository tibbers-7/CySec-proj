export class LogInResponseData{
    accessToken = ""
    refreshToken = ""

    public constructor(obj?:LogInResponseData){
        if(obj){
            this.accessToken = obj.accessToken
            this.refreshToken = obj.refreshToken
        }
    }
}