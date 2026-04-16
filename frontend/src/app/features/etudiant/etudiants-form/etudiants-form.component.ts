import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { EtudiantsService } from '../../../core/services/etudiants.service';
import { FiliereService } from '../../../core/services/filiere.service';
import { ToastService } from '../../../shared/toast/toast.service';

@Component({
  selector: 'app-etudiants-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './etudiants-form.component.html',
  styleUrls: ['./etudiants-form.component.scss']
})
export class EtudiantsFormComponent implements OnInit {
  etudiantForm: FormGroup;
  id?: number;
  filieres: any[] = [];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private etudiantService: EtudiantsService,
    private filiereService: FiliereService,
    private toastService: ToastService
  ) {
    this.etudiantForm = this.fb.group({
      nom: ['', Validators.required],      // nom de l'utilisateur
      email: ['', [Validators.required, Validators.email]], // email de l'utilisateur
      matricule: ['', Validators.required],
      cne: [''],
      cin: [''],
      nationalite: [''],
      sexe: [''],
      dateNaissance: [''],
      lieuNaissance: [''],
      adresse: [''],
      ville: [''],
      telephone: [''],
      bacAnnee: [''],
      bacSerie: [''],
      bacMention: [''],
      bacLycée: [''],
      bacLieuObtention: [''],
      bacAcademie: [''],
      filiereId: [null],
      annee: [null]
    });
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    if (this.id) {
      this.etudiantService.getById(this.id).subscribe(etudiant => {
        this.etudiantForm.patchValue({
          nom: etudiant.nom,
          email: etudiant.email,
          matricule: etudiant.matricule,
          cne: etudiant.cne,
          cin: etudiant.cin,
          nationalite: etudiant.nationalite,
          sexe: etudiant.sexe,
          dateNaissance: etudiant.dateNaissance,
          lieuNaissance: etudiant.lieuNaissance,
          adresse: etudiant.adresse,
          ville: etudiant.ville,
          telephone: etudiant.telephone,
          bacAnnee: etudiant.bacAnnee,
          bacSerie: etudiant.bacSerie,
          bacMention: etudiant.bacMention,
          bacLycée: etudiant.bacLycée,
          bacLieuObtention: etudiant.bacLieuObtention,
          bacAcademie: etudiant.bacAcademie,
          filiereId: etudiant.filiereId
        });
      });
    }
    this.filiereService.getAll().subscribe(data => this.filieres = data);
  }

  onSubmit(): void {
    if (this.etudiantForm.invalid) return;

    // Construction du payload avec l'objet user imbriqué
    const payload = {
      user: {
        nom: this.etudiantForm.value.nom,
        email: this.etudiantForm.value.email
      },
      matricule: this.etudiantForm.value.matricule,
      cne: this.etudiantForm.value.cne,
      cin: this.etudiantForm.value.cin,
      nationalite: this.etudiantForm.value.nationalite,
      sexe: this.etudiantForm.value.sexe,
      dateNaissance: this.etudiantForm.value.dateNaissance,
      lieuNaissance: this.etudiantForm.value.lieuNaissance,
      adresse: this.etudiantForm.value.adresse,
      ville: this.etudiantForm.value.ville,
      telephone: this.etudiantForm.value.telephone,
      bacAnnee: this.etudiantForm.value.bacAnnee,
      bacSerie: this.etudiantForm.value.bacSerie,
      bacMention: this.etudiantForm.value.bacMention,
      bacLycée: this.etudiantForm.value.bacLycée,
      bacLieuObtention: this.etudiantForm.value.bacLieuObtention,
      bacAcademie: this.etudiantForm.value.bacAcademie,
      filiere: this.etudiantForm.value.filiereId ? { id: this.etudiantForm.value.filiereId } : null
    };

    const obs = this.id
      ? this.etudiantService.update(this.id, payload)
      : this.etudiantService.create(payload);

    obs.subscribe({
      next: () => {
        this.toastService.show('Étudiant enregistré', 'success');
        this.router.navigate(['/admin/etudiants']);
      },
      error: (err) => this.toastService.show('Erreur', 'error')
    });
  }

  annuler(): void {
    this.router.navigate(['/admin/etudiants']);
  }
}