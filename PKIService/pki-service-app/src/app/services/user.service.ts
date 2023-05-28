import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EmployeeDTO } from '../model/employee-dto';
import { RegistrationRequest } from '../model/registrationRequest';
import { RegistrationApprovalRequest } from '../model/registrationApprovalRequest';
import { RegistrationCancellationRequest } from '../model/registrationCancellationRequest';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  apiHost: string = 'http://localhost:8082/user/'
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' })

  constructor(private http: HttpClient) { }

  getAllProjectManagers(): Observable<EmployeeDTO[]>{
    return this.http.get<EmployeeDTO[]>(this.apiHost + 'projectManagers', {headers: this.headers});
  }

  getAllEngineers(): Observable<EmployeeDTO[]>{
    return this.http.get<EmployeeDTO[]>(this.apiHost + 'engineers', {headers: this.headers});
  }

  getEmployeeById(id: number): Observable<EmployeeDTO>{
    return this.http.get<EmployeeDTO>(this.apiHost + id, {headers: this.headers});
  }

  acceptRegistrationRequest(registrationApproval : RegistrationApprovalRequest): Observable<any>{
    return this.http.post(this.apiHost+'register/approve', registrationApproval, {headers: this.headers})
  }

  declineRegistrationRequest(registrationCancellation : RegistrationCancellationRequest): Observable<any>{
    return this.http.post(this.apiHost+'register/cancel',registrationCancellation, {headers: this.headers})
  }

  getRegistrationRequests(): Observable<RegistrationRequest[]>{
    return this.http.get<RegistrationRequest[]>(this.apiHost+'registration-requests', {headers: this.headers})
  }

}
