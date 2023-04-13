import { DigitalEntity } from "./digitalEntity";

export class Certificate{
    id: number = 0
    serialNumber: string = ''
    issuer : DigitalEntity = new DigitalEntity()
    subject: DigitalEntity = new DigitalEntity()
    certificateRole: string = ''
    certificateStatus: string = ''
    expiringDate : string = ''

    public constructor(obj? : Certificate){
        if(obj){
            this.id = obj.id
            this.serialNumber = obj.serialNumber
            this.issuer = obj.issuer
            this.subject =  obj.subject
            this.certificateRole = obj.certificateRole
            this.certificateStatus = obj.certificateStatus
            this.expiringDate = obj.expiringDate
        }
    }
}