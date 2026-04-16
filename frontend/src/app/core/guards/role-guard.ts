import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({ providedIn: 'root' })
export class RoleGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole = route.data['role'];
    const userRole = this.authService.getRole();

    if (!userRole) {
      this.router.navigate(['/login']);
      return false;
    }

    // Comparaison insensible à la casse
    if (userRole.toLowerCase() === expectedRole.toLowerCase()) {
      return true;
    }

    // Redirection vers la page appropriée selon le rôle réel
    if (userRole === 'ADMIN') {
      this.router.navigate(['/admin']);
    } else if (userRole === 'PROFESSEUR') {
      this.router.navigate(['/professeur']);
    } else if (userRole === 'ETUDIANT') {
      this.router.navigate(['/etudiant']);
    } else {
      this.router.navigate(['/']);
    }
    return false;
  }
}