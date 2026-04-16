import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from '../../../../shared/navbar/navbar.component';
import { SidebarComponent } from '../../../../shared/sidebar/sidebar.component';

@Component({
  selector: 'app-etudiant-layout',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent, SidebarComponent],
  template: `
    <app-navbar></app-navbar>
    <div style="display: flex;">
      <app-sidebar></app-sidebar>
      <main style="flex:1; padding:20px;">
        <router-outlet></router-outlet>
      </main>
    </div>
  `
})
export class EtudiantLayoutComponent {}