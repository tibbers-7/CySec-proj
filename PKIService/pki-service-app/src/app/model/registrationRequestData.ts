import { Address } from "./address"

export class RegistrationRequestData{
    name = ""
    surname = ""
    username = ""
    password = ""
    address : Address = new Address()
    phoneNumber = ""
    workTitle = ""
    role = ""

    public constructor(obj? : RegistrationRequestData){
        if(obj){
            this.name = obj.name
            this.surname = obj.surname
            this.username = obj.username
            this.password = obj.password
            this.address = obj.address
            this.workTitle = obj.workTitle
            this.phoneNumber = obj.phoneNumber
            this.role = obj.role
        }
    }
}