import { DigitalEntity } from "./digitalEntity"

export class NewCertificateRequestData {
    startDate : string = ''
    endDate : string = ''
    issuer : DigitalEntity = new DigitalEntity()
    subject : DigitalEntity = new DigitalEntity()
    
}