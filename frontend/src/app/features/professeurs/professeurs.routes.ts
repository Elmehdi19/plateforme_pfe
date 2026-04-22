import { Routes } from '@angular/router';
import { ProfesseurLayoutComponent } from './layout/professeur-layout/professeur-layout.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NotesListComponent } from '../notes/notes-list/notes-list.component';
import { NotesFormComponent } from '../notes/notes-form/notes-form.component';
import { DeliberationListComponent } from '../deliberation/deliberation-list/deliberation-list.component';
import { PredictionsComponent } from './predictions/predictions.component';
import { AbsencesComponent } from './absences/absences.component';


export const PROFESSEUR_ROUTES: Routes = [
  {
    path: '',
    component: ProfesseurLayoutComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'deliberation', component: DeliberationListComponent },
      { path: 'notes', component: NotesListComponent },
      { path: 'notes/nouveau', component: NotesFormComponent },
      { path: 'notes/edit/:id', component: NotesFormComponent },
      { path: 'predictions', component: PredictionsComponent },
      {path: 'absences', component: AbsencesComponent }
    ]
  }
];