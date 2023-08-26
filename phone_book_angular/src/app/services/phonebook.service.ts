import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { PhoneRecord } from '../models/record.model';
import { Observable } from 'rxjs';

const baseUrl = 'http://localhost:8080/api/records';

@Injectable({
  providedIn: 'root'
})

export class PhonebookService
{
  constructor(private http: HttpClient) { }

  //----------------------------------------------------------------------------
  getAll(): Observable<PhoneRecord[]> 
  {
    return this.http.get<PhoneRecord[]>(baseUrl);
  }

  //----------------------------------------------------------------------------
  get(id: any): Observable<PhoneRecord> 
  {
    return this.http.get(`${baseUrl}/${id}`);
  }

  //----------------------------------------------------------------------------
  create(data: PhoneRecord): Observable<any>
  {
    return this.http.post(`${baseUrl}/add`, data);
  }

  //----------------------------------------------------------------------------
  delete(id: number): Observable<PhoneRecord>
  {
    return this.http.post(`${baseUrl}/delete`, id);
  }

  //----------------------------------------------------------------------------
  update(record: PhoneRecord): Observable<PhoneRecord>
  {
    return this.http.post(`${baseUrl}/update`, record);
  }
}
