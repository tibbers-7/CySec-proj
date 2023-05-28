import { Address } from "./address"

export class RegistrationRequest{
    id = 0
    name = ""
    surname = ""
    username = ""
    password = ""
    address : Address = new Address()
    phoneNumber = ""
    workTitle = ""
    role = ""

    public constructor(obj? : RegistrationRequest){
        if(obj){
            this.id = obj.id
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