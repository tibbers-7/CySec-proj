export class RegistrationFormData{
    name = ""
    surname = ""
    username = ""
    password = ""
    confirmPassword = ""
    streetAddress = ""
    city = ""
    country = ""
    phoneNumber = ""
    userType = ""
    workTitle = ""

    public constructor(obj? : RegistrationFormData){
        if(obj){
            this.name = obj.name
            this.surname = obj.surname
            this.username = obj.username
            this.password = obj.password
            this.confirmPassword = obj.confirmPassword
            this.streetAddress = obj.streetAddress
            this.city = obj.city
            this.country = obj.country
            this.phoneNumber = obj.phoneNumber
            this.userType = obj.userType
            this.workTitle = obj.workTitle
        }
    }
}
