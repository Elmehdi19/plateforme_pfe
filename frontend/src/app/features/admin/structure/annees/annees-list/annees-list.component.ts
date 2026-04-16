import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AnneeService } from '../../../../../core/services/annee.service';
import { Annee } from '../../../../../models/annee.model';

@Component({
  selector: 'app-annees-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './annees-list.component.html'
})
export class AnneesListComponent implements OnInit {
  annees: Annee[] = [];

  constructor(private anneeService: AnneeService, private router: Router) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.anneeService.getAll().subscribe(data => this.annees = data);
  }

  nouvelle(): void {
    this.router.navigate(['/admin/annees/nouveau']);
  }

  edit(id: number): void {
    this.router.navigate(['/admin/annees', id]);
  }

  delete(id: number): void {
    if (confirm('Supprimer cette année ?')) {
      this.anneeService.delete(id).subscribe(() => this.load());
    }
  }
}