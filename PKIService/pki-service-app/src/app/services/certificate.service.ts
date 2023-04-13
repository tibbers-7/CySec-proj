import { Injectable } from "@angular/core"
import { NewCertificateRequestData } from "../model/newCertificateRequestData"
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { Observable } from "rxjs"
import { Certificate } from "../model/certificate"

@Injectable({
  providedIn: 'root'
})
export class CertificateService {
    apiHost: string = 'http://localhost:8080/api/certificates/'
    headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' })
  
    constructor(private http: HttpClient) { }
  
    issueCertificate(certificateData: NewCertificateRequestData, keyStorePassword: string): Observable<any>{
      return this.http.post<any>(this.apiHost + "issueCertificate/" + keyStorePassword, certificateData ,{headers: this.headers})
    }

    getAllCertificates(): Observable<any>{
      return this.http.get<any>(this.apiHost + "getCertificates" ,{headers: this.headers})
    }

    getIssuers(): Observable<any>{
      return this.http.get<any>(this.apiHost + "getSSAndCA", {headers:this.headers})
    }

    revokeCertificate(certificateId : number): Observable<any>{
      return this.http.put<any>(this.apiHost + "withdraw/" + certificateId, {headers: this.headers})
    }
  }