import { Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth-guard';
import { RoleGuard } from './core/guards/role-guard';
import { ChangePasswordComponent } from './features/change-password/change-password.component'; // ← ajustez le chemin si nécessaire

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'change-password',            // ← route déplacée avant le wildcard
    component: ChangePasswordComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'admin',
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'admin' },
    loadChildren: () => import('./features/admin/admin.routes').then(m => m.ADMIN_ROUTES)
  },
  {
    path: 'professeur',
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'professeur' },
    loadChildren: () => import('./features/professeurs/professeurs.routes').then(m => m.PROFESSEUR_ROUTES)
  },
  {
    path: 'etudiant',
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'etudiant' },
    loadChildren: () => import('./features/etudiant/etudiant.routes').then(m => m.ETUDIANT_ROUTES)
  },
  { path: '**', redirectTo: '/login' }  // ← wildcard toujours en dernier
];