import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { ElementService } from '../../../../../core/services/element.service';
import { ToastService } from '../../../../../shared/toast/toast.service';

@Component({
  selector: 'app-elements-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './elements-list.component.html'
})
export class ElementsListComponent implements OnInit {
  elements: any[] = [];

  constructor(
    private elementService: ElementService,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.elementService.getAll().subscribe(data => this.elements = data);
  }

  nouveau(): void {
    this.router.navigate(['/admin/elements/nouveau']);
  }

  edit(id: number): void {
    this.router.navigate(['/admin/elements', id]);
  }

  delete(id: number): void {
    if (confirm('Supprimer cet élément ?')) {
      this.elementService.delete(id).subscribe({
        next: () => {
          this.toastService.show('Élément supprimé', 'success');
          this.load();
        },
        error: () => this.toastService.show('Erreur suppression', 'error')
      });
    }
  }
}