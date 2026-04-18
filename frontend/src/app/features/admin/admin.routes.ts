import { Routes } from '@angular/router';
import { AdminLayoutComponent } from './layout/admin-layout/admin-layout.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { FilieresListComponent } from './structure/filieres/filieres-list/filieres-list.component';
import { FilieresFormComponent } from './structure/filieres/filieres-form/filieres-form.component';
import { ModulesListComponent } from './structure/modules/modules-list/modules-list.component';
import { ModulesFormComponent } from './structure/modules/modules-form/modules-form.component';
import { ElementsListComponent } from './structure/elements/elements-list/elements-list.component';
import { ElementsFormComponent } from './structure/elements/elements-form/elements-form.component';
import { SemestresListComponent } from './structure/semestres/semestres-list/semestres-list.component';
import { SemestresFormComponent } from './structure/semestres/semestres-form/semestres-form.component';
import { EtudiantsListComponent } from '../etudiant/etudiants-list/etudiants-list.component';
import { EtudiantsFormComponent } from '../etudiant/etudiants-form/etudiants-form.component';
import { ImportComponent } from '../import/import.component';
import { AnneesListComponent } from './structure/annees/annees-list/annees-list.component';
import { AnneesFormComponent } from './structure/annees/annees-form/annees-form.component';
import { ParametresComponent } from './parametres/parametres.component';
import { ProfesseursFormComponent } from './structure/professeurs/professeurs-form/professeurs-form.component';
import { ProfesseursListComponent } from './structure/professeurs/professeurs-list/professeurs-list.component';
import { DepartementsListComponent } from './structure/departements/departements-list/departements-list.component';
import { DepartementsFormComponent } from './structure/departements/departements-form/departements-form.component';
import { DeliberationListComponent } from '../deliberation/deliberation-list/deliberation-list.component';
import { PredictionsComponent } from '../professeurs/predictions/predictions.component';

export const ADMIN_ROUTES: Routes = [
  {
    path: '',
    component: AdminLayoutComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'filieres', component: FilieresListComponent },
      { path: 'filieres/nouveau', component: FilieresFormComponent },
      { path: 'filieres/:id', component: FilieresFormComponent },
      { path: 'modules', component: ModulesListComponent },
      { path: 'modules/nouveau', component: ModulesFormComponent },
      { path: 'modules/:id', component: ModulesFormComponent },
      { path: 'elements', component: ElementsListComponent },
      { path: 'elements/nouveau', component: ElementsFormComponent },
      { path: 'elements/:id', component: ElementsFormComponent },
      { path: 'semestres', component: SemestresListComponent },
      { path: 'semestres/nouveau', component: SemestresFormComponent },
      { path: 'semestres/:id', component: SemestresFormComponent },
      { path: 'professeurs', component: ProfesseursListComponent },
      { path: 'professeurs/nouveau', component: ProfesseursFormComponent },
      { path: 'professeurs/:id', component: ProfesseursFormComponent },
      { path: 'etudiants', component: EtudiantsListComponent },
      { path: 'etudiants/nouveau', component: EtudiantsFormComponent },
      { path: 'etudiants/:id', component: EtudiantsFormComponent },
      { path: 'import', component: ImportComponent },
      { path: 'annees', component: AnneesListComponent },
      { path: 'annees/nouveau', component: AnneesFormComponent },
      { path: 'annees/:id', component: AnneesFormComponent },
      { path: 'parametres', component: ParametresComponent },
      { path: 'departements', component: DepartementsListComponent },
      { path: 'departements/nouveau', component: DepartementsFormComponent },
      { path: 'departements/:id', component: DepartementsFormComponent },
      { path: 'deliberation', component: DeliberationListComponent },
      { path: 'predictions', component: PredictionsComponent }
    ]
  }
];