export class Address {
    id = 0
    country = ""
    city = ""
    streetAddresss = ""

    public constructor(obj? : Address){
        if(obj){
            this.id = obj.id
            this.country = obj.country
            this.city = obj.city
            this.streetAddresss = obj.streetAddresss
        }
    }
}