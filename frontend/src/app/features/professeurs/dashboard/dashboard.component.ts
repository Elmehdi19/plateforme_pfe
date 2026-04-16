import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';
import { NotesService } from '../../../core/services/notes.service';
import { LoaderService } from '../../../shared/loader/loader.service';

@Component({
  selector: 'app-professeur-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  dernieresNotes: any[] = [];
  totalNotes = 0;
  derniereNote: any = null;

  constructor(
    public authService: AuthService,
    private notesService: NotesService,
    private loaderService: LoaderService
  ) {}

  ngOnInit(): void {
    this.loaderService.show();
    this.notesService.getAll().subscribe({
      next: (data) => {
        // Statistiques générales
        this.totalNotes = data.length;
        
        // Dernières notes (5 plus récentes)
        this.dernieresNotes = data
          .filter((n: any) => n.dateEvaluation)
          .sort((a: any, b: any) => new Date(b.dateEvaluation).getTime() - new Date(a.dateEvaluation).getTime())
          .slice(0, 5);
        
        if (this.dernieresNotes.length > 0) {
          this.derniereNote = this.dernieresNotes[0];
        }
        this.loaderService.hide();
      },
      error: () => this.loaderService.hide()
    });
  }
}