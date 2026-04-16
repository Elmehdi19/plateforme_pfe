import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ProfesseursService } from '../../../../../core/services/professeurs.service';
import { ToastService } from '../../../../../shared/toast/toast.service';

@Component({
  selector: 'app-professeurs-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './professeurs-form.component.html',
  styleUrls: ['./professeurs-form.component.scss']
})
export class ProfesseursFormComponent implements OnInit {
  form: FormGroup;
  id: number | null = null;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private professeurService: ProfesseursService,
    private toastService: ToastService
  ) {
    this.form = this.fb.group({
      nom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      bureau: [''],
      specialite: [''],
      discipline: [''],
      departement: [''],
      responsableFiliere: [false]
    });
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'] ? +this.route.snapshot.params['id'] : null;
    if (this.id) {
      this.loadProfesseur();
    }
  }

  loadProfesseur(): void {
    this.professeurService.getById(this.id!).subscribe({
      next: (prof) => {
        this.form.patchValue({
          nom: prof.nom,
          email: prof.email,
          bureau: prof.bureau,
          specialite: prof.specialite,
          discipline: prof.discipline,
          departement: prof.departement,
          responsableFiliere: prof.responsableFiliere
        });
      },
      error: () => this.toastService.show('Erreur de chargement', 'error')
    });
  }

  // Dans votre méthode onSubmit
  onSubmit(): void {
  if (this.form.invalid) return;

  const payload = {
    user: {
      nom: this.form.value.nom,
      email: this.form.value.email
    },
    bureau: this.form.value.bureau,
    specialite: this.form.value.specialite,
    discipline: this.form.value.discipline,
    departement: this.form.value.departement,
    responsableFiliere: this.form.value.responsableFiliere || false
  };

  const obs = this.id
    ? this.professeurService.update(this.id, payload)
    : this.professeurService.create(payload);

  obs.subscribe({
    next: () => {
      this.toastService.show('Professeur enregistré', 'success');
      this.router.navigate(['/admin/professeurs']);
    },
    error: (err) => this.toastService.show('Erreur', 'error')
  });
}
}