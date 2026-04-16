import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Filiere } from '../../models/filiere.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class FiliereService {
  private api = `${environment.apiUrl}/filieres`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Filiere[]> {
    return this.http.get<Filiere[]>(this.api);
  }

  getById(id: number): Observable<Filiere> {
    return this.http.get<Filiere>(`${this.api}/${id}`);
  }

  create(data: Filiere): Observable<Filiere> {
    return this.http.post<Filiere>(this.api, data);
  }

  update(id: number, data: Filiere): Observable<Filiere> {
    return this.http.put<Filiere>(`${this.api}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }
}