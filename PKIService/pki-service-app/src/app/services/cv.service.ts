import { HttpHeaders, HttpClient, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CvService {
  apiHost: string = 'http://localhost:8082/cv'
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' })

  constructor(private http: HttpClient) { }

  uploadCV(file: File, engineerID: number): Observable<any> {
    const formData = new FormData();
    formData.append('pdfFile', file, file.name);
    console.log(formData)

    return this.http.post<any>(this.apiHost + '/uploadFile/' + engineerID, formData);
  }
  
  downloadCV(engineerID: number): Observable<any> {
    return this.http.get<any>(this.apiHost + '/downloadFile/' + engineerID)
  }

}
