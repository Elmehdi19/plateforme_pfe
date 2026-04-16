import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { NotesService } from '../../../core/services/notes.service';
import { LoaderService } from '../../../shared/loader/loader.service';
import { ToastService } from '../../../shared/toast/toast.service';

@Component({
  selector: 'app-etudiant-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  nom = '';
  email = '';
  dernieresNotes: any[] = [];
  moyenneGenerale = 0;

  constructor(
    private authService: AuthService,
    private notesService: NotesService,
    private loaderService: LoaderService,
    private toastService: ToastService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.nom = this.authService.getUserName() || '';
    this.email = this.authService.getUserEmail() || '';

    const etudiantId = this.authService.getEtudiantId();
    if (!etudiantId) {
      this.toastService.show('Profil étudiant non trouvé', 'error');
      this.router.navigate(['/login']);
      return;
    }

    this.loaderService.show();
    this.notesService.getByEtudiant(etudiantId).subscribe({
      next: (notes) => {
        this.dernieresNotes = notes.slice(0, 5);
        if (notes.length > 0) {
          const somme = notes.reduce((acc, n) => acc + n.valeur, 0);
          this.moyenneGenerale = somme / notes.length;
        }
        this.loaderService.hide();
      },
      error: (err) => {
        console.error('Erreur chargement notes', err);
        this.toastService.show('Impossible de charger vos notes', 'error');
        this.loaderService.hide();
      }
    });
  }
}