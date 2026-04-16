import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Professeur } from '../../models/professeur.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ProfesseursService {
  private api = `${environment.apiUrl}/professeurs`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Professeur[]> {
    return this.http.get<Professeur[]>(this.api);
  }

  getById(id: number): Observable<Professeur> {
    return this.http.get<Professeur>(`${this.api}/${id}`);
  }

  create(data: Professeur): Observable<Professeur> {
    return this.http.post<Professeur>(this.api, data);
  }

  update(id: number, data: Professeur): Observable<Professeur> {
    return this.http.put<Professeur>(`${this.api}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  getResponsables(): Observable<Professeur[]> {
    return this.http.get<Professeur[]>(`${this.api}/responsables`);
  }
}