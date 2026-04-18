import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PredictionService } from '../../../core/services/prediction.service';
import { EtudiantsService } from '../../../core/services/etudiants.service';
import { LoaderService } from '../../../shared/loader/loader.service';
import { ToastService } from '../../../shared/toast/toast.service';

@Component({
  selector: 'app-prof-predictions',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './predictions.component.html',
  styleUrls: ['./predictions.component.scss']
})
export class PredictionsComponent implements OnInit {
  etudiants: any[] = [];
  selectedEtudiantId?: number;
  prediction: any = null;

  constructor(
    private etudiantService: EtudiantsService,
    private predictionService: PredictionService,
    private loaderService: LoaderService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.loadEtudiants();
  }

  loadEtudiants(): void {
    this.loaderService.show();
    this.etudiantService.getAll().subscribe({
      next: (data) => {
        this.etudiants = data;
        this.loaderService.hide();
      },
      error: () => this.loaderService.hide()
    });
  }

  onEtudiantChange(): void {
    if (!this.selectedEtudiantId) {
      this.prediction = null;
      return;
    }
    this.loaderService.show();
    this.predictionService.getLastByEtudiant(this.selectedEtudiantId).subscribe({
      next: (data) => {
        this.prediction = data;
        this.loaderService.hide();
      },
      error: () => {
        this.prediction = null;
        this.loaderService.hide();
      }
    });
  }

  genererPrediction(): void {
    if (!this.selectedEtudiantId) return;
    this.loaderService.show();
    this.predictionService.generer(this.selectedEtudiantId).subscribe({
      next: (data) => {
        this.prediction = data;
        this.toastService.show('Prédiction générée', 'success');
        this.loaderService.hide();
      },
      error: () => this.toastService.show('Erreur', 'error')
    });
  }
}