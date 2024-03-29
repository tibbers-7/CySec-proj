import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import jwt_decode from 'jwt-decode';
import { RegistrationRequestData } from '../model/registrationRequestData';
import { RegularLogInRequestData } from '../model/regularLogInRequestData';
import { LogInResponseData } from '../model/logInResponseData';
import { Response } from '../model/response';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  apiHost: string = 'http://localhost:8082/auth'
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' })

  constructor(private http: HttpClient) { }

  sendRegistrationRequest(registrationRequest: RegistrationRequestData): Observable<any> {
    return this.http.post(this.apiHost + '/register', registrationRequest, { headers: this.headers})}

  logInUserwithCredentials(credentials:RegularLogInRequestData): Observable<LogInResponseData> {
    return this.http.post<any>(this.apiHost + '/authenticate', credentials, { headers: this.headers})
  }

  recoverAccount(credentials:RegularLogInRequestData): Observable<LogInResponseData> {
    return this.http.post<any>(this.apiHost + '/authenticate/send-recovery-email', credentials, { headers: this.headers})
  }

  validateRecoveryToken(token:string, username:string): Observable<LogInResponseData> {
    return this.http.post<any>(this.apiHost + '/authenticate/recovery?token='+token+'&username='+username, { headers: this.headers})
  }

  passwordlessLoginRequest(username : String): Observable<any> {
    return this.http.post(this.apiHost + '/authenticate/passwordless', username, { headers: this.headers})
  }

  activateMagicLoginLink(token: String, username: String): Observable<LogInResponseData> {
    return this.http.post<any>(this.apiHost + '/authenticate/link?token='+token+'&username='+username, { headers: this.headers})
  }

  activateAccount(token: String, username: String): Observable<any> {
    return this.http.post(this.apiHost + '/activateAccount?token='+token+'&username='+username,{ headers: this.headers})
  }

  logOutUser(refreshToken: string): Observable<any> {
    return this.http.post(this.apiHost + '/logOut', refreshToken, { headers: this.headers})
  }

  public setSession(tokenData: LogInResponseData) {
     localStorage.setItem('accessToken', tokenData.accessToken)
     localStorage.setItem('refreshToken', tokenData.refreshToken)
   }

   decodeAccessToken() {
    const accessToken = localStorage.getItem('accessToken')
    if(accessToken) {
        return jwt_decode(accessToken);
    }
    return null
   }

   getExpiration() {
    const token : any = this.decodeAccessToken()
    console.log(token)
    if (!token) return null
    const timeStamp: number = Number(token['exp'])
    return new Date(timeStamp * 1000)
    }

   getRole() {
    const token : any = this.decodeAccessToken()
    if (!token) return null
    let roleToParse : string =  token['roleName']
    let roles = roleToParse.split(',')
    return roles[0]
   }

   getUserId() {
    const token : any = this.decodeAccessToken()
    if (!token) return null
    return token['userId']
    }

    getUsername() {
        const token : any = this.decodeAccessToken()
        if (!token) return null
        return token['username']
    }

    logout() {
     const refreshToken = localStorage.getItem('refreshToken')
     if(refreshToken) this.logOutUser(refreshToken).subscribe()
     localStorage.clear();
    }

    public isLoggedIn() {
      const expiration = this.getExpiration()
      if(expiration) return (new Date().toISOString()) < expiration.toISOString()
      return false
    }

    isLoggedOut() {
      return !this.isLoggedIn();
    }


}
