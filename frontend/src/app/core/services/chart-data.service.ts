import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ChartDataService {
  private api = `${environment.apiUrl}/statistiques`;

  constructor(private http: HttpClient) {}

  getMoyennesParModule(): Observable<any> {
    return this.http.get<any>(`${this.api}/moyennes-modules`);
  }

  getDecisionsParFiliere(): Observable<any> {
    return this.http.get<any>(`${this.api}/decisions`);
  }
}