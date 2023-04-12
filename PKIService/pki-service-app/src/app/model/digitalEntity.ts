export class DigitalEntity {
    id : number = 0
    firstName : string = ''
    lastName : string = ''
    organization : string = ''
    organizationUnit : string = ''
    country : string = ''
    email : string = ''

    public constructor(obj? : DigitalEntity){
        if(obj){
            this.id = obj.id
            this.firstName = obj.firstName
            this.lastName = obj.lastName
            this.organization = obj.organization
            this.organizationUnit = obj.organizationUnit
            this.country = obj.country
            this.email = obj.email
        }
    }
}