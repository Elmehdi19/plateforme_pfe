import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FiliereService } from '../../../core/services/filiere.service';
import { DeliberationService } from '../../../core/services/deliberation.service';

@Component({
  selector: 'app-parametres',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container">
      <h2>Paramètres de délibération</h2>
      <div class="form-group">
        <label>Filière</label>
        <select class="form-control" [(ngModel)]="selectedFiliereId" (change)="loadParametres()">
          <option *ngFor="let f of filieres" [value]="f.id">{{ f.nom }}</option>
        </select>
      </div>
      <div *ngIf="parametres">
        <div class="form-group">
          <label>Note minimale pour valider un module (X)</label>
          <input type="number" class="form-control" [(ngModel)]="parametres.noteMinModule" />
        </div>
        <div class="form-group">
          <label>Moyenne minimale pour valider un semestre (Y)</label>
          <input type="number" class="form-control" [(ngModel)]="parametres.moyenneMinSemestre" />
        </div>
        <div class="form-group">
          <label>Note éliminatoire (Z)</label>
          <input type="number" class="form-control" [(ngModel)]="parametres.noteEliminatoire" />
        </div>
        <div class="form-group">
          <label>Moyenne minimale annuelle (k)</label>
          <input type="number" class="form-control" [(ngModel)]="parametres.moyenneMinAnnuelle" />
        </div>
        <button class="btn btn-primary" (click)="save()">Enregistrer</button>
      </div>
    </div>
  `
})
export class ParametresComponent implements OnInit {
  filieres: any[] = [];
  selectedFiliereId?: number;
  parametres: any = {};

  constructor(
    private filiereService: FiliereService,
    private deliberationService: DeliberationService
  ) {}

  ngOnInit(): void {
    this.filiereService.getAll().subscribe(data => this.filieres = data);
  }

  loadParametres(): void {
    if (this.selectedFiliereId) {
      this.deliberationService.parametresFiliere(this.selectedFiliereId).subscribe(data => {
        this.parametres = data;
      });
    }
  }

  save(): void {
    // Appeler un service pour mettre à jour les paramètres
    console.log('Sauvegarder', this.parametres);
  }
}