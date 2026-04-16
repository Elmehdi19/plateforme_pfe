import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ElementService } from '../../../../../core/services/element.service';
import { ModuleService } from '../../../../../core/services/module.service';
import { ProfesseursService } from '../../../../../core/services/professeurs.service';
import { ToastService } from '../../../../../shared/toast/toast.service';

@Component({
  selector: 'app-elements-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './elements-form.component.html'
})
export class ElementsFormComponent implements OnInit {
  form: FormGroup;
  id?: number;
  modules: any[] = [];
  professeurs: any[] = [];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private elementService: ElementService,
    private moduleService: ModuleService,
    private professeurService: ProfesseursService,
    private toastService: ToastService
  ) {
    this.form = this.fb.group({
      nom: ['', Validators.required],
      code: [''],
      coefficient: [null],
      moduleId: [null],
      professeurId: [null]
    });
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    if (this.id) {
      this.elementService.getById(this.id).subscribe(data => {
        this.form.patchValue({
          nom: data.nom,
          code: data.code,
          coefficient: data.coefficient,
          moduleId: data.moduleId,
          professeurId: data.professeurId
        });
      });
    }
    this.moduleService.getAll().subscribe(data => this.modules = data);
    this.professeurService.getAll().subscribe(data => this.professeurs = data);
  }

  onSubmit(): void {
    if (this.form.invalid) return;

    const payload = {
      nom: this.form.value.nom,
      code: this.form.value.code,
      coefficient: this.form.value.coefficient,
      module: this.form.value.moduleId ? { id: this.form.value.moduleId } : null,
      professeur: this.form.value.professeurId ? { id: this.form.value.professeurId } : null
    };

    const obs = this.id
      ? this.elementService.update(this.id, payload)
      : this.elementService.create(payload);

    obs.subscribe({
      next: () => {
        this.toastService.show('Élément enregistré', 'success');
        this.router.navigate(['/admin/elements']);
      },
      error: (err) => this.toastService.show('Erreur', 'error')
    });
  }

  annuler(): void {
    this.router.navigate(['/admin/elements']);
  }
}