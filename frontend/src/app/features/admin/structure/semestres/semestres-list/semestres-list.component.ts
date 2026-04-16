import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { SemestreService } from '../../../../../core/services/semestre.service';
import { Semestre } from '../../../../../models/semestre.model';

@Component({
  selector: 'app-semestres-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './semestres-list.component.html'
})
export class SemestresListComponent implements OnInit {
  semestres: Semestre[] = [];

  constructor(
    private semestreService: SemestreService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.semestreService.getAll().subscribe(data => this.semestres = data);
  }

  nouveau(): void {
    this.router.navigate(['/admin/semestres/nouveau']);
  }

  edit(id: number): void {
    this.router.navigate(['/admin/semestres', id]);
  }

  delete(id: number): void {
    if (confirm('Supprimer ce semestre ?')) {
      this.semestreService.delete(id).subscribe(() => this.load());
    }
  }
}