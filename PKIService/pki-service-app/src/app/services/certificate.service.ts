import { Injectable } from "@angular/core"
import { NewCertificateRequestData } from "../model/newCertificateRequestData"
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { Observable } from "rxjs"

@Injectable({
    providedIn: 'root'
  })
  
  export class CertificateService {
  //4200
    apiHost: string = 'http://localhost:4200/api'
    headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' })
  
    constructor(private http: HttpClient) { }
  
    issueCertificate(certificateData: NewCertificateRequestData, keyStorePassword: string): Observable<any>{
      return this.http.post<any>(this.apiHost + "/issueCertificate/" + keyStorePassword, certificateData ,{headers: this.headers})
    }
  }