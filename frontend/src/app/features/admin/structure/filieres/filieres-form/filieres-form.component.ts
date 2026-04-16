import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FiliereService } from '../../../../../core/services/filiere.service';
import { DepartementService } from '../../../../../core/services/departement.service';
import { ToastService } from '../../../../../shared/toast/toast.service';

@Component({
  selector: 'app-filieres-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './filieres-form.component.html'
})
export class FilieresFormComponent implements OnInit {
  form: FormGroup;
  id?: number;
  departements: any[] = [];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private filiereService: FiliereService,
    private departementService: DepartementService,
    private toastService: ToastService
  ) {
    this.form = this.fb.group({
      nom: ['', Validators.required],
      code: [''],
      departementId: [null]
    });
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    // Charger les départements
    this.departementService.getAll().subscribe(data => this.departements = data);
    if (this.id) {
      this.filiereService.getById(this.id).subscribe(data => {
        this.form.patchValue({
          nom: data.nom,
          code: data.code,
          departementId: data.departementId
        });
      });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) return;

    const payload = {
      nom: this.form.value.nom,
      code: this.form.value.code,
      departement: this.form.value.departementId ? { id: this.form.value.departementId } : null
    };

    const obs = this.id
      ? this.filiereService.update(this.id, payload)
      : this.filiereService.create(payload);

    obs.subscribe({
      next: () => {
        this.toastService.show('Filière enregistrée', 'success');
        this.router.navigate(['/admin/filieres']);
      },
      error: (err) => this.toastService.show('Erreur', 'error')
    });
  }

  annuler(): void {
    this.router.navigate(['/admin/filieres']);
  }
}