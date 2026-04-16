import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { ModuleService } from '../../../../../core/services/module.service';
import { ToastService } from '../../../../../shared/toast/toast.service';

@Component({
  selector: 'app-modules-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './modules-list.component.html',
  styleUrls: ['./modules-list.component.scss']
})
export class ModulesListComponent implements OnInit {
  modules: any[] = [];
  loading = false;

  constructor(
    private moduleService: ModuleService,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading = true;
    this.moduleService.getAll().subscribe({
      next: (data) => {
        this.modules = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        this.toastService.show('Erreur de chargement', 'error');
      }
    });
  }

  nouveau(): void {
    this.router.navigate(['/admin/modules/nouveau']);
  }

  edit(id: number): void {
    this.router.navigate(['/admin/modules', id]);
  }

  delete(id: number): void {
    if (confirm('Supprimer ce module ?')) {
      this.moduleService.delete(id).subscribe({
        next: () => {
          this.toastService.show('Module supprimé', 'success');
          this.load();
        },
        error: () => this.toastService.show('Erreur suppression', 'error')
      });
    }
  }
}