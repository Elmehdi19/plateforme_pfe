import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { ProfesseursService } from '../../../../../core/services/professeurs.service';
import { Professeur } from '../../../../../models/professeur.model';
import { ToastService } from '../../../../../shared/toast/toast.service';

@Component({
  selector: 'app-professeurs-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './professeurs-list.component.html'
})
export class ProfesseursListComponent implements OnInit {
  professeurs: Professeur[] = [];

  constructor(
    private professeurService: ProfesseursService,
    private router: Router,
    private toastService: ToastService   // ✅ injection correcte
  ) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.professeurService.getAll().subscribe({
      next: (data) => this.professeurs = data,
      error: (err) => this.toastService.show('Erreur chargement', 'error')
    });
  }

  nouveau(): void {
    this.router.navigate(['/admin/professeurs/nouveau']);
  }

  edit(id: number): void {
    this.router.navigate(['/admin/professeurs', id]);
  }

  delete(id: number): void {
    if (confirm('Supprimer ce professeur ?')) {
      this.professeurService.delete(id).subscribe({
        next: () => {
          this.toastService.show('Professeur supprimé', 'success');
          this.load();   // 🔄 recharge la liste après suppression
        },
        error: (err) => {
          console.error(err);
          this.toastService.show('Erreur suppression', 'error');
        }
      });
    }
  }
}