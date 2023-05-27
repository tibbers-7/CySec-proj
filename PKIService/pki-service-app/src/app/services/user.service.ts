import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EmployeeDTO } from '../model/employee-dto';

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

}
