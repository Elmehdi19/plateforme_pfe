import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NotesService } from '../../../core/services/notes.service';
import { EtudiantsService } from '../../../core/services/etudiants.service';
import { ElementService } from '../../../core/services/element.service';
import { LoaderService } from '../../../shared/loader/loader.service';
import { ToastService } from '../../../shared/toast/toast.service';

@Component({
  selector: 'app-notes-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './notes-form.component.html',
  styleUrls: ['./notes-form.component.scss']
})
export class NotesFormComponent implements OnInit {
  note: any = {
    etudiantId: null,
    elementId: null,
    valeur: null,
    typeEvaluation: 'EXAMEN',
    session: 'ORDINAIRE',
    dateEvaluation: new Date().toISOString().substring(0,10)
  };
  etudiants: any[] = [];
  elements: any[] = [];
  id?: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private notesService: NotesService,
    private etudiantService: EtudiantsService,
    private elementService: ElementService,
    private loaderService: LoaderService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.loadEtudiants();
    this.loadElements();
    if (this.id) {
      this.loadNote();
    }
  }

  loadNote(): void {
    this.loaderService.show();
    this.notesService.getById(this.id!).subscribe({
      next: (data) => {
        this.note = data;
        this.loaderService.hide();
      },
      error: () => this.loaderService.hide()
    });
  }

  loadEtudiants(): void {
    this.etudiantService.getAll().subscribe(data => this.etudiants = data);
  }

  loadElements(): void {
    this.elementService.getAll().subscribe(data => this.elements = data);
  }

  save(): void {
    this.loaderService.show();
    if (this.id) {
      this.notesService.update(this.id, this.note).subscribe({
        next: () => {
          this.toastService.show('Note modifiée', 'success');
          this.router.navigate(['/admin/notes']);
        },
        error: () => this.toastService.show('Erreur', 'error')
      });
    } else {
      this.notesService.create(this.note).subscribe({
        next: () => {
          this.toastService.show('Note créée', 'success');
          this.router.navigate(['/admin/notes']);
        },
        error: () => this.toastService.show('Erreur', 'error')
      });
    }
  }

  annuler(): void {
    this.router.navigate(['/admin/notes']);
  }
}