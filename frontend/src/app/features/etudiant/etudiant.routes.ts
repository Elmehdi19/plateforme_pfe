import { Routes } from '@angular/router';
import { EtudiantLayoutComponent } from './layout/etudiant-layout/etudiant-layout.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NotesListComponent } from '../notes/notes-list/notes-list.component';
import { ProfileComponent } from '../auth/profile/profile';
import { EtudiantsListComponent } from './etudiants-list/etudiants-list.component';
import { EtudiantsFormComponent } from './etudiants-form/etudiants-form.component';

export const ETUDIANT_ROUTES: Routes = [
  {
    path: '',
    component: EtudiantLayoutComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'notes', component: NotesListComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'list', component: EtudiantsListComponent },
      { path: 'form', component: EtudiantsFormComponent },
      { path: 'form/:id', component: EtudiantsFormComponent }
    ]
  }
];