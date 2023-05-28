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

  getAllEngineers(): Observable<EmployeeDTO[]>{
    return this.http.get<EmployeeDTO[]>(this.apiHost + 'engineers', {headers: this.headers});
  }

  getEmployeeById(id: number): Observable<EmployeeDTO>{
    return this.http.get<EmployeeDTO>(this.apiHost + 'findById/' + id, {headers: this.headers});
  }

  getAllEmployees(): Observable<EmployeeDTO[]>{
    return this.http.get<EmployeeDTO[]>(this.apiHost, {headers: this.headers});
  }

  createEmployee(employee: EmployeeDTO): Observable<EmployeeDTO>{
    return this.http.post<EmployeeDTO>(this.apiHost, employee, {headers: this.headers});
  }

  updateEmployee(employee: EmployeeDTO): Observable<EmployeeDTO>{
    return this.http.put<EmployeeDTO>(this.apiHost, employee, {headers: this.headers});
  }

}
