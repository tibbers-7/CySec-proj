export class RegistrationApprovalRequest{
    approvalDescription = ""
    idOfRequest = 0

    public constructor(obj? :RegistrationApprovalRequest){
        if(obj){
            this.approvalDescription = obj.approvalDescription
            this.idOfRequest = obj.idOfRequest
        }
    }
}