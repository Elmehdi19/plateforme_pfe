import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class DeliberationService {
  private api = `${environment.apiUrl}/deliberation`;

  constructor(private http: HttpClient) {}

  parametresFiliere(filiereId: number): Observable<any> {
    return this.http.get(`${this.api}/parametres/${filiereId}`);
  }

  statistiquesSemestre(semestreId: number, filiereId: number, moyenneMinSemestre: number, noteMinModule: number, noteEliminatoire: number): Observable<any> {
    return this.http.get(`${this.api}/statistiques/semestre/${semestreId}`, {
      params: {
        filiereId: filiereId.toString(),
        moyenneMinSemestre: moyenneMinSemestre.toString(),
        noteMinModule: noteMinModule.toString(),
        noteEliminatoire: noteEliminatoire.toString()
      }
    });
  }

  resultatsSemestre(semestreId: number, filiereId: number, params?: any): Observable<any> {
    return this.http.get(`${this.api}/semestre/${semestreId}/filiere/${filiereId}`, { params });
  }

  genererPVModule(moduleId: number, session: string, noteMinModule?: number): Observable<any> {
    let url = `${this.api}/module/${moduleId}/pv?session=${session}`;
    if (noteMinModule) url += `&noteMinModule=${noteMinModule}`;
    return this.http.get(url);
  }

  genererPVSemestre(semestreId: number, filiereId: number, params?: any): Observable<any> {
  return this.http.get(`${this.api}/semestre/${semestreId}/pv`, { params: { filiereId, ...params } });
}

  etudiantsEnRisque(filiereId: number, semestreId: number, noteSeuil: number = 8): Observable<any> {
    return this.http.get(`${this.api}/risque`, { params: { filiereId, semestreId, noteSeuil } });
  }
}