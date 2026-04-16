import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { DepartementService } from '../../../../../core/services/departement.service';
import { ToastService } from '../../../../../shared/toast/toast.service';

@Component({
  selector: 'app-departements-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './departements-list.component.html'
})
export class DepartementsListComponent implements OnInit {
  departements: any[] = [];

  constructor(
    private departementService: DepartementService,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.departementService.getAll().subscribe(data => this.departements = data);
  }

  nouveau(): void {
    this.router.navigate(['/admin/departements/nouveau']);
  }

  edit(id: number): void {
    this.router.navigate(['/admin/departements', id]);
  }

  delete(id: number): void {
    if (confirm('Supprimer ce département ?')) {
      this.departementService.delete(id).subscribe({
        next: () => {
          this.toastService.show('Département supprimé', 'success');
          this.load();
        },
        error: () => this.toastService.show('Erreur suppression', 'error')
      });
    }
  }
}