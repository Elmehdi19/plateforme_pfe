import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DepartementService } from '../../../../../core/services/departement.service';
import { EtablissementService } from '../../../../../core/services/etablissement.service';
import { ToastService } from '../../../../../shared/toast/toast.service';

@Component({
  selector: 'app-departements-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './departements-form.component.html'
})
export class DepartementsFormComponent implements OnInit {
  form: FormGroup;
  id?: number;
  etablissements: any[] = [];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private departementService: DepartementService,
    private etablissementService: EtablissementService,
    private toastService: ToastService
  ) {
    this.form = this.fb.group({
      nom: ['', Validators.required],
      code: [''],
      etablissementId: [null]
    });
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.etablissementService.getAll().subscribe(data => this.etablissements = data);
    if (this.id) {
      this.departementService.getById(this.id).subscribe(data => {
        this.form.patchValue({
          nom: data.nom,
          code: data.code,
          etablissementId: data.etablissementId
        });
      });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) return;
    const payload = {
      nom: this.form.value.nom,
      code: this.form.value.code,
      etablissement: this.form.value.etablissementId ? { id: this.form.value.etablissementId } : null
    };
    const obs = this.id
      ? this.departementService.update(this.id, payload)
      : this.departementService.create(payload);
    obs.subscribe({
      next: () => {
        this.toastService.show('Département enregistré', 'success');
        this.router.navigate(['/admin/departements']);
      },
      error: () => this.toastService.show('Erreur', 'error')
    });
  }

  annuler(): void {
    this.router.navigate(['/admin/departements']);
  }
}