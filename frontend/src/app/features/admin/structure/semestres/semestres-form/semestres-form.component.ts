import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { SemestreService } from '../../../../../core/services/semestre.service';
import { AnneeService } from '../../../../../core/services/annee.service';
import { ToastService } from '../../../../../shared/toast/toast.service';

@Component({
  selector: 'app-semestres-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './semestres-form.component.html',
  styleUrls: ['./semestres-form.component.scss']
})
export class SemestresFormComponent implements OnInit {
  form: FormGroup;
  id: number | null = null;
  annees: any[] = [];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private semestreService: SemestreService,
    private anneeService: AnneeService,
    private toastService: ToastService
  ) {
    this.form = this.fb.group({
      nom: ['', Validators.required],
      ordre: [null, Validators.required],
      anneeId: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'] ? +this.route.snapshot.params['id'] : null;
    this.loadAnnees();
    if (this.id) this.loadSemestre();
  }

  loadAnnees(): void {
    this.anneeService.getAll().subscribe({
      next: (data) => this.annees = data,
      error: () => this.toastService.show('Erreur chargement années', 'error')
    });
  }

  loadSemestre(): void {
    this.semestreService.getById(this.id!).subscribe({
      next: (semestre) => {
        this.form.patchValue({
          nom: semestre.nom,
          ordre: semestre.ordre,
          anneeId: semestre.anneeId
        });
      },
      error: () => this.toastService.show('Erreur chargement', 'error')
    });
  }

  onSubmit(): void {
    if (this.form.invalid) return;

    // Construction du payload avec l'objet annee imbriqué
    const payload = {
      nom: this.form.value.nom,
      ordre: this.form.value.ordre,
      annee: { id: this.form.value.anneeId }
    };

    const obs = this.id
      ? this.semestreService.update(this.id, payload)
      : this.semestreService.create(payload);

    obs.subscribe({
      next: () => {
        this.toastService.show('Semestre enregistré', 'success');
        this.router.navigate(['/admin/semestres']);
      },
      error: (err) => this.toastService.show('Erreur', 'error')
    });
  }

  annuler(): void {
    this.router.navigate(['/admin/semestres']);
  }
}