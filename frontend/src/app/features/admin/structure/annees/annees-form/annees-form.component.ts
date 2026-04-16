import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AnneeService } from '../../../../../core/services/annee.service';
import { FiliereService } from '../../../../../core/services/filiere.service';

@Component({
  selector: 'app-annees-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './annees-form.component.html'
})
export class AnneesFormComponent implements OnInit {
  form: FormGroup;
  id?: number;
  filieres: any[] = [];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private anneeService: AnneeService,
    private filiereService: FiliereService
  ) {
    this.form = this.fb.group({
      nom: ['', Validators.required],
      ordre: [null],
      diplômante: [false],
      filiereId: [null]
    });
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.filiereService.getAll().subscribe(data => this.filieres = data);
    if (this.id) {
      this.anneeService.getById(this.id).subscribe(data => {
        this.form.patchValue(data);
      });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) return;
    if (this.id) {
      this.anneeService.update(this.id, this.form.value).subscribe(() => this.router.navigate(['/admin/annees']));
    } else {
      this.anneeService.create(this.form.value).subscribe(() => this.router.navigate(['/admin/annees']));
    }
  }

  annuler(): void {
    this.router.navigate(['/admin/annees']);
  }
}