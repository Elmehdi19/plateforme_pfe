import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { LoaderService } from '../../shared/loader/loader.service';
import { ToastService } from '../../shared/toast/toast.service';

@Component({
  selector: 'app-import',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container">
      <h2>Import Excel</h2>
      <div class="card">
        <h3>Importer des étudiants</h3>
        <input type="file" (change)="onFileSelected($event, 'etudiants')" accept=".xlsx,.xls" />
      </div>
      <div class="card mt-3">
        <h3>Importer des professeurs</h3>
        <input type="file" (change)="onFileSelected($event, 'professeurs')" accept=".xlsx,.xls" />
      </div>
      <div class="card mt-3">
        <h3>Importer des modules</h3>
        <input type="file" (change)="onFileSelected($event, 'modules')" accept=".xlsx,.xls" />
      </div>
      <div *ngIf="result" class="alert alert-info mt-3">
        <p>Total : {{ result.total }}</p>
        <p>Succès : {{ result.success }}</p>
        <p>Erreurs : {{ result.errors }}</p>
      </div>
    </div>
  `,
  styles: [`
    .card { padding: 1rem; background: white; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
  `]
})
export class ImportComponent {
  private api = environment.apiUrl;
  result: any = null;

  constructor(
    private http: HttpClient,
    private loaderService: LoaderService,
    private toastService: ToastService
  ) {}

  onFileSelected(event: any, type: string): void {
    const file: File = event.target.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append('file', file);

    this.loaderService.show();
    this.http.post(`${this.api}/import/${type}`, formData).subscribe({
      next: (res: any) => {
        this.result = res;
        this.toastService.show(`Import ${type} réussi : ${res.success} succès, ${res.errors} erreurs`, 'success');
        this.loaderService.hide();
      },
      error: (err) => {
        this.toastService.show('Erreur lors de l\'import', 'error');
        this.loaderService.hide();
        console.error(err);
      }
    });
  }
}