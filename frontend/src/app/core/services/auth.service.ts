import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { JwtHelperService } from '@auth0/angular-jwt';

export interface LoginResponse {
  token: string;
  role: string;
  id: number;
  email: string;
  nom: string;
  etudiantId?: number; // Ajout
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private api = `${environment.apiUrl}/auth`;
  private jwtHelper = new JwtHelperService();

  constructor(private http: HttpClient, private router: Router) {}

  login(credentials: { email: string; password: string }): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.api}/login`, credentials).pipe(
      tap((response) => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('role', response.role);
        localStorage.setItem('userId', response.id.toString());
        localStorage.setItem('userName', response.nom);
        localStorage.setItem('userEmail', response.email);
        if (response.etudiantId) {
          localStorage.setItem('etudiantId', response.etudiantId.toString());
        } else {
          localStorage.removeItem('etudiantId'); // nettoyage
        }
      })
    );
  }

  register(user: { nom: string; email: string; password: string; role: string }): Observable<string> {
    return this.http.post<string>(`${this.api}/register`, user);
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRole(): string | null {
    return localStorage.getItem('role');
  }

  getUserId(): number | null {
    const id = localStorage.getItem('userId');
    return id ? parseInt(id, 10) : null;
  }

  getUserName(): string | null {
    return localStorage.getItem('userName');
  }

  getUserEmail(): string | null {
    return localStorage.getItem('userEmail');
  }

  getEtudiantId(): number | null {
    const id = localStorage.getItem('etudiantId');
    return id ? parseInt(id, 10) : null;
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    if (!token) return false;
    if (this.jwtHelper.isTokenExpired(token)) {
      this.logout();
      return false;
    }
    return true;
  }

  hasRole(role: string | string[]): boolean {
    const userRole = this.getRole();
    if (!userRole) return false;
    if (Array.isArray(role)) {
      return role.some(r => r.toUpperCase() === userRole.toUpperCase());
    }
    return role.toUpperCase() === userRole.toUpperCase();
  }

  changePassword(currentPassword: string, newPassword: string): Observable<any> {
    return this.http.put(`${this.api}/change-password`, { currentPassword, newPassword });
  }
}