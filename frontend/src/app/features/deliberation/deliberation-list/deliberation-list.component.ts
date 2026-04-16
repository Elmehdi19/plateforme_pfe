import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DeliberationService } from '../../../core/services/deliberation.service';
import { FiliereService } from '../../../core/services/filiere.service';
import { SemestreService } from '../../../core/services/semestre.service';
import { LoaderService } from '../../../shared/loader/loader.service';
import { ToastService } from '../../../shared/toast/toast.service';

@Component({
  selector: 'app-deliberation-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './deliberation-list.component.html',
  styleUrls: ['./deliberation-list.component.scss']
})
export class DeliberationListComponent implements OnInit {
  filieres: any[] = [];
  semestres: any[] = [];
  selectedFiliereId?: number;
  selectedSemestreId?: number;
  parametres: any = {};
  resultats: any = null;
  detailsEtudiants: any[] = [];
  showDetails = false;

  constructor(
    private deliberationService: DeliberationService,
    private filiereService: FiliereService,
    private semestreService: SemestreService,
    private loaderService: LoaderService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.loaderService.show();
    this.filiereService.getAll().subscribe({
      next: (data) => {
        this.filieres = data;
        this.loaderService.hide();
      },
      error: () => this.loaderService.hide()
    });
  }

  onFiliereChange(): void {
    if (!this.selectedFiliereId) return;
    this.loaderService.show();
    this.deliberationService.parametresFiliere(this.selectedFiliereId).subscribe({
      next: (data) => {
        this.parametres = data;
        this.loaderService.hide();
        this.loadSemestres();
      },
      error: () => this.loaderService.hide()
    });
  }

  loadSemestres(): void {
    if (!this.selectedFiliereId) return;
    this.loaderService.show();
    this.semestreService.getByFiliere(this.selectedFiliereId).subscribe({
      next: (data) => {
        this.semestres = data;
        this.loaderService.hide();
      },
      error: () => this.loaderService.hide()
    });
  }

  calculer(): void {
    if (!this.selectedFiliereId || !this.selectedSemestreId) {
      this.toastService.show('Veuillez sélectionner une filière et un semestre', 'error');
      return;
    }
    this.loaderService.show();
    this.deliberationService.statistiquesSemestre(
      this.selectedSemestreId,
      this.selectedFiliereId,
      this.parametres.moyenneMinSemestre || 10,
      this.parametres.noteMinModule || 10,
      this.parametres.noteEliminatoire || 5
    ).subscribe({
      next: (stats) => {
        this.resultats = stats;
        this.loaderService.hide();
        this.showDetails = false;
      },
      error: () => this.loaderService.hide()
    });
  }

  voirDetails(): void {
    if (!this.selectedFiliereId || !this.selectedSemestreId) return;
    this.loaderService.show();
    this.deliberationService.genererPVSemestre(
      this.selectedSemestreId,
      this.selectedFiliereId,
      {
        moyenneMinSemestre: this.parametres.moyenneMinSemestre,
        noteMinModule: this.parametres.noteMinModule,
        noteEliminatoire: this.parametres.noteEliminatoire
      }
    ).subscribe({
      next: (data) => {
        this.detailsEtudiants = data.resultats || [];
        this.showDetails = true;
        this.loaderService.hide();
      },
      error: () => this.loaderService.hide()
    });
  }

  imprimer(): void {
    window.print();
  }
}