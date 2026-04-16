import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Etudiant } from '../../models/etudiant.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class EtudiantsService {
  private api = `${environment.apiUrl}/etudiants`;

  constructor(private http: HttpClient) {}

  getAll(filters?: any): Observable<Etudiant[]> {
    let params = new HttpParams();
    if (filters?.filiereId) params = params.set('filiereId', filters.filiereId);
    if (filters?.search) params = params.set('keyword', filters.search);
    return this.http.get<Etudiant[]>(this.api, { params });
  }

  getById(id: number): Observable<Etudiant> {
    return this.http.get<Etudiant>(`${this.api}/${id}`);
  }

  create(data: any): Observable<Etudiant> {       // ← modifié
    return this.http.post<Etudiant>(this.api, data);
  }

  update(id: number, data: any): Observable<Etudiant> { // ← modifié
    return this.http.put<Etudiant>(`${this.api}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  getNotes(id: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.api}/${id}/notes`);
  }

  getInscriptions(id: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.api}/${id}/inscriptions`);
  }
}