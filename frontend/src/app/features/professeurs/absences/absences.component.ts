import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AbsenceService } from '../../../core/services/absence.service';
import { EtudiantsService } from '../../../core/services/etudiants.service';
import { ElementService } from '../../../core/services/element.service';
import { ToastService } from '../../../shared/toast/toast.service';

@Component({
  selector: 'app-absences',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './absences.component.html'
})
export class AbsencesComponent implements OnInit {
  etudiants: any[] = [];
  elements: any[] = [];
  selectedEtudiantId?: number;
  selectedElementId?: number;
  dateAbsence: string = '';
  justifiee: boolean = false;
  motif: string = '';

  constructor(
    private absenceService: AbsenceService,
    private etudiantService: EtudiantsService,
    private elementService: ElementService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.etudiantService.getAll().subscribe(data => this.etudiants = data);
    this.elementService.getAll().subscribe(data => this.elements = data);
  }

  onSubmit(): void {
    if (!this.selectedEtudiantId || !this.selectedElementId || !this.dateAbsence) {
      this.toastService.show('Veuillez remplir tous les champs', 'error');
      return;
    }
    const payload = {
      etudiant: { id: this.selectedEtudiantId },
      element: { id: this.selectedElementId },
      dateAbsence: this.dateAbsence,
      justifiee: this.justifiee,
      motif: this.motif
    };
    this.absenceService.create(payload).subscribe({
      next: () => {
        this.toastService.show('Absence enregistrée', 'success');
        this.selectedEtudiantId = undefined;
        this.selectedElementId = undefined;
        this.dateAbsence = '';
        this.justifiee = false;
        this.motif = '';
      },
      error: () => this.toastService.show('Erreur', 'error')
    });
  }
}