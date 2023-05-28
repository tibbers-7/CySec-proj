import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Address } from '../model/address';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  apiHost: string = 'http://localhost:8082/address/'
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' })

  constructor(private http: HttpClient) { }

  createAddress(address: Address): Observable<Address>{
    return this.http.post<Address>(this.apiHost, address, {headers: this.headers});
  }

  getAddressById(id:number): Observable<Address>{
    return this.http.get<Address>(this.apiHost + id, {headers: this.headers});
  }

  updateAddress(address: Address): Observable<Address>{
    return this.http.put<Address>(this.apiHost, address, {headers: this.headers});
  }

  deleteAddress(id: any): Observable<any>{
    return this.http.delete<any>(this.apiHost + id, {headers: this.headers});
  }
}
