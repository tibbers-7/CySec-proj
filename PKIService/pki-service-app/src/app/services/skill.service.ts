import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SkillDTO } from '../model/skill-dto.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SkillService {

  apiHost: string = 'http://localhost:8082/skill/'
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' })

  constructor(private http: HttpClient) { }

  getAllSkillsByEngineerID(id: number): Observable<SkillDTO[]>{
    return this.http.get<SkillDTO[]>(this.apiHost + 'findByEngineerID/' + id, {headers: this.headers});
  }

  getSkillById(id: number): Observable<SkillDTO>{
    return this.http.get<SkillDTO>(this.apiHost + id, {headers: this.headers});
  }

  createSkill(skill: SkillDTO): Observable<SkillDTO>{
    return this.http.post<SkillDTO>(this.apiHost, skill, {headers: this.headers});
  }

  updateSkill(skill: SkillDTO): Observable<SkillDTO>{
    return this.http.put<SkillDTO>(this.apiHost, skill, {headers: this.headers});
  }

  deleteSkill(id: any): Observable<any>{
    return this.http.delete<any>(this.apiHost + id, {headers: this.headers});
  }
}
