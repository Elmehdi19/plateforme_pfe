import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Inscription } from '../../models/inscription.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class InscriptionService {
  private api = `${environment.apiUrl}/inscriptions`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Inscription[]> {
    return this.http.get<Inscription[]>(this.api);
  }

  getByEtudiant(etudiantId: number): Observable<Inscription[]> {
    return this.http.get<Inscription[]>(`${this.api}/etudiant/${etudiantId}`);
  }

  getBySemestre(semestreId: number): Observable<Inscription[]> {
    return this.http.get<Inscription[]>(`${this.api}/semestre/${semestreId}`);
  }

  inscriptionEnLigne(data: any): Observable<Inscription> {
    return this.http.post<Inscription>(`${this.api}/en-ligne`, data);
  }

  inscriptionAdministrative(etudiantId: number, anneeUnivId: number, data: any): Observable<Inscription> {
    return this.http.post<Inscription>(`${this.api}/administrative/${etudiantId}/${anneeUnivId}`, data);
  }

  inscrireAuSemestre(etudiantId: number, semestreId: number, anneeUnivId: number): Observable<string> {
    return this.http.post<string>(`${this.api}/semestre/${etudiantId}/${semestreId}/${anneeUnivId}`, {});
  }

  desinscrireDuSemestre(etudiantId: number, semestreId: number): Observable<string> {
    return this.http.delete<string>(`${this.api}/semestre/${etudiantId}/${semestreId}`);
  }
}