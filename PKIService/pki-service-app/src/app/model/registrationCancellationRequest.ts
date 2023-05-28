export class RegistrationCancellationRequest{
    cancellationDescription = ""
    idOfRequest = 0

    public constructor(obj? :RegistrationCancellationRequest){
        if(obj){
            this.cancellationDescription = obj.cancellationDescription
            this.idOfRequest = obj.idOfRequest
        }
    }
}
