import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { EtudiantsService } from '../../../core/services/etudiants.service';
import { Etudiant } from '../../../models/etudiant.model';
import { ToastService } from '../../../shared/toast/toast.service';

@Component({
  selector: 'app-etudiants-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './etudiants-list.component.html',
  styleUrls: ['./etudiants-list.component.scss']
})
export class EtudiantsListComponent implements OnInit {
  etudiants: Etudiant[] = [];
  loading = false;

  constructor(
    private etudiantService: EtudiantsService,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.load();
  }

  load(search?: string): void {
    this.loading = true;
    this.etudiantService.getAll({ search }).subscribe({
      next: (data) => {
        this.etudiants = data;
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }

  onSearch(event: Event): void {
    const keyword = (event.target as HTMLInputElement).value;
    this.load(keyword);
  }

  nouveau(): void {
    this.router.navigate(['/admin/etudiants/nouveau']);
  }

  edit(id: number): void {
    this.router.navigate(['/admin/etudiants', id]);
  }

  delete(id: number): void {
    if (confirm('Supprimer cet étudiant ?')) {
      this.etudiantService.delete(id).subscribe({
        next: () => {
          this.toastService.show('Étudiant supprimé', 'success');
          this.load();
        },
        error: () => this.toastService.show('Erreur suppression', 'error')
      });
    }
  }
}