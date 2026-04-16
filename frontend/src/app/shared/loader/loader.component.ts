import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoaderService } from './loader.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-loader',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div *ngIf="loading$ | async" class="loader-overlay">
      <div class="spinner"></div>
    </div>
  `,
  styles: [/* ... */]
})
export class LoaderComponent {
  loading$: Observable<boolean>; 

  constructor(private loaderService: LoaderService) {
    this.loading$ = this.loaderService.loading$; 
  }
}