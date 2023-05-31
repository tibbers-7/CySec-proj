import { HttpHeaders, HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { PrivilegeRoleDTO } from "../model/privilegeRoleDTO";
import { Observable } from "rxjs";
import { Permission } from "../model/permission";

@Injectable({
    providedIn: 'root'
  })
export class PermissionsService{
    apiHost: string = 'http://localhost:8080/api/privilege'
    headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' })
  
    constructor(private http: HttpClient) { }

    addPrivilegeToRole(dto : PrivilegeRoleDTO) : Observable<any>{
        return this.http.post(this.apiHost+'/add', dto, {headers : this.headers})
    }

    removePrivilegeFromRole(dto: PrivilegeRoleDTO) : Observable<any>{
        return this.http.post(this.apiHost + '/remove', dto, {headers: this.headers})
    }

    getAllPrivileges() : Observable<Permission[]>{
        return this.http.get<Permission[]>(this.apiHost, {headers : this.headers})
    }

    getPrivilegesForEngineers() : Observable<Permission[]>{
        return this.http.get<Permission[]>(this.apiHost + '/engineer', {headers : this.headers})
    }

    getPrivilegesForHRManagers() : Observable<Permission[]>{
        return this.http.get<Permission[]>(this.apiHost + '/HR', {headers : this.headers})
    } 
    
    getPrivilegesForPRManagers() : Observable<Permission[]>{
        return this.http.get<Permission[]>(this.apiHost + '/PR', {headers : this.headers})
    } 
    
    editPrivilege(privilege : Permission) : Observable<any>{
        return this.http.put(this.apiHost, privilege, {headers : this.headers})
    }
}