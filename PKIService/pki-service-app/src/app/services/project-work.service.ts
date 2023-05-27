import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProjectWorkDTO } from '../model/project-work-dto.model';

@Injectable({
  providedIn: 'root'
})
export class ProjectWorkService {
  apiHost: string = 'http://localhost:8082/projectWork/'
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' })

  constructor(private http: HttpClient) { }

  getAllByProjectId(id:number): Observable<ProjectWorkDTO[]>{
    return this.http.get<ProjectWorkDTO[]>(this.apiHost + 'findByProjectID/' + id, { headers: this.headers });
  }

  deleteProjectWork(id: any): Observable<any>{
    return this.http.delete<any>(this.apiHost + id, {headers: this.headers});
  }
}
