import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Project } from '../model/project.model';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  apiHost: string = 'http://localhost:8082/project/'
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' })

  constructor(private http: HttpClient) { }

  getAllProjects(): Observable<Project[]>{
    return this.http.get<Project[]>(this.apiHost, {headers: this.headers});
  }

  deleteProject(id: any): Observable<any>{
    return this.http.delete<any>(this.apiHost + id, {headers: this.headers});
  }

  createProject(project: Project): Observable<Project>{
    return this.http.post<Project>(this.apiHost, project, {headers: this.headers});
  }

  getProjectById(id:number): Observable<Project>{
    return this.http.get<Project>(this.apiHost + id, {headers: this.headers});
  }

  updateProject(project: Project): Observable<Project>{
    return this.http.put<Project>(this.apiHost, project, {headers: this.headers});
  }

  getAllProjectsByProjectManagerID(managerId: number): Observable<Project[]>{
    return this.http.get<Project[]>(this.apiHost + 'findByProjectManager/' + managerId, {headers: this.headers});
  }
}
