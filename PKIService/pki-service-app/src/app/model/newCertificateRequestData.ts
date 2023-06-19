import { DigitalEntity } from "./digitalEntity"

export class NewCertificateRequestData {
    issuer : DigitalEntity = new DigitalEntity()
    subject : DigitalEntity = new DigitalEntity()
    certificateRole : string = ''
    extensions : string[] = []

    public constructor(obj? : NewCertificateRequestData){
        if(obj){
            this.issuer = obj.issuer
            this.subject = obj.subject
            this.certificateRole = obj.certificateRole
            this.extensions = obj.extensions
        }
    }
}