import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InscriptionService } from '../../../../core/services/inscription.service';
import { Inscription } from '../../../../models/inscription.model';

@Component({
  selector: 'app-inscriptions-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container">
      <h2>Inscriptions</h2>
      <table class="table">
        <thead>
          <tr>
            <th>Étudiant</th>
            <th>Année univ.</th>
            <th>Semestre</th>
            <th>Date</th>
            <th>Type</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let i of inscriptions">
            <td>{{ i.etudiantNom }}</td>
            <td>{{ i.anneeUniversitaireCode }}</td>
            <td>{{ i.semestreNom }}</td>
            <td>{{ i.dateInscription | date }}</td>
            <td>{{ i.type }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class InscriptionsListComponent implements OnInit {
  inscriptions: Inscription[] = [];

  constructor(private inscriptionService: InscriptionService) {}

  ngOnInit(): void {
    this.inscriptionService.getAll().subscribe(data => this.inscriptions = data);
  }
}