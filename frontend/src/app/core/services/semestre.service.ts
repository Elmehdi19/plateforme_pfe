import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Semestre } from '../../models/semestre.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class SemestreService {
  private api = `${environment.apiUrl}/semestres`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Semestre[]> {
    return this.http.get<Semestre[]>(this.api);
  }

  getById(id: number): Observable<Semestre> {
    return this.http.get<Semestre>(`${this.api}/${id}`);
  }

  getByFiliere(filiereId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.api}/filiere/${filiereId}`);
}

  getByAnnee(anneeId: number): Observable<Semestre[]> {
    return this.http.get<Semestre[]>(`${this.api}/annee/${anneeId}`);
  }

  create(data: Semestre): Observable<Semestre> {
    return this.http.post<Semestre>(this.api, data);
  }

  update(id: number, data: Semestre): Observable<Semestre> {
    return this.http.put<Semestre>(`${this.api}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }
}