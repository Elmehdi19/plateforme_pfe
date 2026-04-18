import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class PredictionService {
  private api = `${environment.apiUrl}/predictions`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<any[]> {
    return this.http.get<any[]>(this.api);
  }

  getByEtudiant(etudiantId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.api}/etudiant/${etudiantId}`);
  }

  generer(etudiantId: number): Observable<any> {
    return this.http.post(`${this.api}/generer/${etudiantId}`, {});
  }
  getLastByEtudiant(etudiantId: number): Observable<any> {
  return this.http.get<any>(`${this.api}/etudiant/${etudiantId}/last`);
  }

  getStatistiques(): Observable<any> {
    return this.http.get(`${this.api}/statistiques`);
  }
}