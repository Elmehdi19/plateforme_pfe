import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FiliereService } from '../../../../../core/services/filiere.service';
import { ToastService } from '../../../../../shared/toast/toast.service';

@Component({
  selector: 'app-filieres-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './filieres-list.component.html'
})
export class FilieresListComponent implements OnInit {
  filieres: any[] = [];

  constructor(
    private filiereService: FiliereService,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.filiereService.getAll().subscribe(data => this.filieres = data);
  }

  nouvelle(): void {
    this.router.navigate(['/admin/filieres/nouveau']);
  }

  edit(id: number): void {
    this.router.navigate(['/admin/filieres', id]);
  }

  delete(id: number): void {
    if (confirm('Supprimer cette filière ?')) {
      this.filiereService.delete(id).subscribe({
        next: () => {
          this.toastService.show('Filière supprimée', 'success');
          this.load();
        },
        error: () => this.toastService.show('Erreur suppression', 'error')
      });
    }
  }
}