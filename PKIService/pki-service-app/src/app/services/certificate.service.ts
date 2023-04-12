import { Injectable } from "@angular/core"
import { NewCertificateRequestData } from "../model/newCertificateRequestData"
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { Observable } from "rxjs"

@Injectable({
    providedIn: 'root'
  })
  
  export class CertificateService {
  
    apiHost: string = ''
    headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' })
  
    constructor(private http: HttpClient) { }
  
    issueCertificate(certificateData: NewCertificateRequestData): Observable<any>{
      return this.http.post<any>(this.apiHost + "api/Certificate/",certificateData ,{headers: this.headers})
    }
  }