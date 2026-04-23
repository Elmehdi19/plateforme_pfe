import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AbsenceService {
  private api = `${environment.apiUrl}/absences`;

  constructor(private http: HttpClient) {}

  countByEtudiant(etudiantId: number): Observable<number> {
    return this.http.get<number>(`${this.api}/etudiant/${etudiantId}/count`);
  }

  getByEtudiant(etudiantId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.api}/etudiant/${etudiantId}`);
  }

  create(data: any): Observable<any> {
    return this.http.post<any>(this.api, data);
  }
}