import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Note } from '../../models/note.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class NotesService {
  private api = `${environment.apiUrl}/notes`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Note[]> {
    return this.http.get<Note[]>(this.api);
  }

  getById(id: number): Observable<Note> {
    return this.http.get<Note>(`${this.api}/${id}`);
  }

  getByEtudiant(etudiantId: number): Observable<Note[]> {
    return this.http.get<Note[]>(`${this.api}/etudiant/${etudiantId}`);
  }

  getByElement(elementId: number): Observable<Note[]> {
    return this.http.get<Note[]>(`${this.api}/element/${elementId}`);
  }

  create(data: Note): Observable<Note> {
    return this.http.post<Note>(this.api, data);
  }

  update(id: number, data: Note): Observable<Note> {
    return this.http.put<Note>(`${this.api}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  getMoyenneModule(etudiantId: number, moduleId: number, noteMinModule: number): Observable<any> {
    const params = new HttpParams().set('noteMinModule', noteMinModule);
    return this.http.get<any>(`${this.api}/moyenne/module/${moduleId}/etudiant/${etudiantId}`, { params });
  }

  importNotes(elementId: number, notes: any[]): Observable<any> {
    return this.http.post<any>(`${this.api}/import/${elementId}`, notes);
  }
}