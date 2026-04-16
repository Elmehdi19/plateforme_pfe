import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PredictionService } from '../../../core/services/prediction.service';
import { Prediction } from '../../../models/prediction.model';

@Component({
  selector: 'app-predictions-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container">
      <h2>Prédictions</h2>
      <table class="table">
        <thead>
          <tr>
            <th>Étudiant</th>
            <th>Score risque</th>
            <th>Niveau</th>
            <th>Date</th>
            <th>Version</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let p of predictions">
            <td>{{ p.etudiantNom }}</td>
            <td>{{ p.scoreRisque }}</td>
            <td>{{ p.niveauRisque }}</td>
            <td>{{ p.datePrediction | date }}</td>
            <td>{{ p.modeleVersion }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class PredictionsListComponent implements OnInit {
  predictions: Prediction[] = [];

  constructor(private predictionService: PredictionService) {}

  ngOnInit(): void {
    this.predictionService.getAll().subscribe(data => this.predictions = data);
  }
}