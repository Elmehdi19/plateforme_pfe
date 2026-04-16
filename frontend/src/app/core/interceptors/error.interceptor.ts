import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);
  return next(req).pipe(
    catchError((error) => {
      if (error.status === 401) {
        // Non authentifié
        localStorage.clear();
        router.navigate(['/login']);
      } else if (error.status === 403) {
        alert('Accès interdit');
      } else if (error.status === 404) {
        alert('Ressource non trouvée');
      } else if (error.status >= 500) {
        alert('Erreur serveur');
      }
      return throwError(() => error);
    })
  );
};