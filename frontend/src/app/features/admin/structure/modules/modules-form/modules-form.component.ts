import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ModuleService } from '../../../../../core/services/module.service';
import { FiliereService } from '../../../../../core/services/filiere.service';
import { SemestreService } from '../../../../../core/services/semestre.service';
import { ProfesseursService } from '../../../../../core/services/professeurs.service';
import { ToastService } from '../../../../../shared/toast/toast.service';

@Component({
  selector: 'app-modules-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './modules-form.component.html',
  styleUrls: ['./modules-form.component.scss']
})
export class ModulesFormComponent implements OnInit {
  form: FormGroup;
  id?: number;
  filieres: any[] = [];
  semestres: any[] = [];
  professeurs: any[] = [];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private moduleService: ModuleService,
    private filiereService: FiliereService,
    private semestreService: SemestreService,
    private professeurService: ProfesseursService,
    private toastService: ToastService
  ) {
    this.form = this.fb.group({
      code: ['', Validators.required],
      intitule: ['', Validators.required],
      credits: [null],
      coefficient: [1.0],
      filiereId: [null],
      semestreId: [null],
      professeurId: [null]
    });
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    // Charger les listes
    this.filiereService.getAll().subscribe({
      next: (data) => this.filieres = data,
      error: () => this.toastService.show('Erreur chargement filières', 'error')
    });
    this.semestreService.getAll().subscribe({
      next: (data) => this.semestres = data,
      error: () => this.toastService.show('Erreur chargement semestres', 'error')
    });
    this.professeurService.getAll().subscribe({
      next: (data) => this.professeurs = data,
      error: () => this.toastService.show('Erreur chargement professeurs', 'error')
    });

    if (this.id) {
      this.moduleService.getById(this.id).subscribe({
        next: (module) => {
          this.form.patchValue({
            code: module.code,
            intitule: module.intitule,
            credits: module.credits,
            coefficient: module.coefficient,
            filiereId: module.filiereId,
            semestreId: module.semestreId,
            professeurId: module.professeurId
          });
        },
        error: () => this.toastService.show('Erreur chargement module', 'error')
      });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) return;

    const payload = {
      code: this.form.value.code,
      intitule: this.form.value.intitule,
      credits: this.form.value.credits,
      coefficient: this.form.value.coefficient,
      filiere: this.form.value.filiereId ? { id: this.form.value.filiereId } : null,
      semestre: this.form.value.semestreId ? { id: this.form.value.semestreId } : null,
      professeur: this.form.value.professeurId ? { id: this.form.value.professeurId } : null
    };

    const obs = this.id
      ? this.moduleService.update(this.id, payload)
      : this.moduleService.create(payload);

    obs.subscribe({
      next: () => {
        this.toastService.show('Module enregistré', 'success');
        this.router.navigate(['/admin/modules']);
      },
      error: (err) => {
        console.error(err);
        this.toastService.show('Erreur', 'error');
      }
    });
  }

  annuler(): void {
    this.router.navigate(['/admin/modules']);
  }
}