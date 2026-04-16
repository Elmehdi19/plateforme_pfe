import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { NotesService } from '../../../core/services/notes.service';
import { EtudiantsService } from '../../../core/services/etudiants.service';
import { ElementService } from '../../../core/services/element.service';
import { LoaderService } from '../../../shared/loader/loader.service';
import { ToastService } from '../../../shared/toast/toast.service';
import { Note } from '../../../models/note.model';

@Component({
  selector: 'app-notes-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './notes-list.component.html',
  styleUrls: ['./notes-list.component.scss']
})
export class NotesListComponent implements OnInit {
  notes: Note[] = [];
  etudiantsMap: Map<number, string> = new Map();
  elementsMap: Map<number, string> = new Map();

  role: string | null = null;
  etudiantId: number | null = null;
  isLoading = true;

  constructor(
    private authService: AuthService,
    private notesService: NotesService,
    private etudiantService: EtudiantsService,
    private elementService: ElementService,
    private loaderService: LoaderService,
    private toastService: ToastService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Récupérer les infos depuis le localStorage via le service
    this.role = this.authService.getRole();
    this.etudiantId = this.authService.getEtudiantId();

    console.log('NotesListComponent - rôle:', this.role);
    console.log('NotesListComponent - etudiantId:', this.etudiantId);

    // Vérifier le rôle (accepter minuscules/majuscules)
    const isEtudiant = this.role?.toUpperCase() === 'ETUDIANT';

    if (isEtudiant && this.etudiantId) {
      // Charger uniquement les notes de l'étudiant
      this.loadMesNotes();
    } else if (this.role?.toUpperCase() === 'PROFESSEUR' || this.role?.toUpperCase() === 'ADMIN') {
      // Charger toutes les données (pour professeur/admin)
      this.loadReferences();
      this.loadAllNotes();
    } else {
      // Cas non autorisé ou données manquantes
      this.isLoading = false;
      console.warn('Accès non autorisé – rôle ou etudiantId manquant');
      this.toastService.show('Vous n\'êtes pas autorisé à voir cette page', 'error');
      // Rediriger vers le dashboard ou une page d'accueil
      // this.router.navigate(['/dashboard']);
    }
  }

  private loadMesNotes(): void {
    this.loaderService.show();
    this.notesService.getByEtudiant(this.etudiantId!).subscribe({
      next: (notes) => {
        this.notes = notes;
        this.isLoading = false;
        this.loaderService.hide();
        console.log('Notes chargées pour étudiant:', notes);
      },
      error: (err) => {
        console.error('Erreur chargement notes étudiant', err);
        this.toastService.show('Erreur de chargement des notes', 'error');
        this.isLoading = false;
        this.loaderService.hide();
      }
    });

    // Optionnel : charger les éléments si besoin pour afficher les noms
    this.elementService.getAll().subscribe({
      next: (elements) => {
        elements.forEach(el => this.elementsMap.set(el.id!, el.nom));
      },
      error: (err) => console.warn('Impossible de charger les éléments', err)
    });
  }

  private loadReferences(): void {
    this.etudiantService.getAll().subscribe({
      next: (etudiants) => {
        etudiants.forEach(e => this.etudiantsMap.set(e.id!, e.nom!));
      },
      error: (err) => console.error('Erreur chargement étudiants', err)
    });

    this.elementService.getAll().subscribe({
      next: (elements) => {
        elements.forEach(el => this.elementsMap.set(el.id!, el.nom));
      },
      error: (err) => console.error('Erreur chargement éléments', err)
    });
  }

  private loadAllNotes(): void {
    this.loaderService.show();
    this.notesService.getAll().subscribe({
      next: (data) => {
        this.notes = data;
        this.isLoading = false;
        this.loaderService.hide();
      },
      error: () => {
        this.toastService.show('Erreur de chargement', 'error');
        this.isLoading = false;
        this.loaderService.hide();
      }
    });
  }

  getEtudiantName(id: number): string {
    // Si étudiant, on peut retourner "Moi" ou chercher dans le service Auth
    if (this.role?.toUpperCase() === 'ETUDIANT') {
      return 'Moi';
    }
    return this.etudiantsMap.get(id) || 'Inconnu';
  }

  getElementName(id: number): string {
    return this.elementsMap.get(id) || 'Inconnu';
  }

  edit(id: number): void {
    if (this.role?.toUpperCase() === 'PROFESSEUR' || this.role?.toUpperCase() === 'ADMIN') {
      this.router.navigate(['/professeur/notes/edit', id]);
    } else {
      this.toastService.show('Action non autorisée', 'error');
    }
  }

  delete(id: number): void {
    if (this.role?.toUpperCase() === 'PROFESSEUR' || this.role?.toUpperCase() === 'ADMIN') {
      if (confirm('Supprimer cette note ?')) {
        this.notesService.delete(id).subscribe(() => this.loadAllNotes());
      }
    } else {
      this.toastService.show('Action non autorisée', 'error');
    }
  }

  nouvelle(): void {
    if (this.role?.toUpperCase() === 'PROFESSEUR' || this.role?.toUpperCase() === 'ADMIN') {
      this.router.navigate(['/professeur/notes/nouveau']);
    } else {
      this.toastService.show('Action non autorisée', 'error');
    }
  }
}