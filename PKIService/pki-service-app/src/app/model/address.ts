export class Address {
    id = 0
    country = ""
    city = ""
    streetAddress = ""

    public constructor(obj? : Address){
        if(obj){
            this.id = obj.id
            this.country = obj.country
            this.city = obj.city
            this.streetAddress = obj.streetAddress
        }
    }
}