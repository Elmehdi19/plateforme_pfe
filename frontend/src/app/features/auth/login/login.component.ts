import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) return;

    this.authService.login(this.loginForm.value).subscribe({
      next: (response) => {
        console.log('Login réussi, réponse:', response);
        console.log('Token stocké:', localStorage.getItem('token'));
        console.log('Rôle stocké:', localStorage.getItem('role'));

        const role = this.authService.getRole()?.toUpperCase();
        console.log('Rôle après conversion:', role);

        if (role === 'ADMIN') {
          this.router.navigate(['/admin']).then(success => {
            console.log('Navigation vers /admin:', success ? 'réussie' : 'échouée');
          });
        } else if (role === 'PROFESSEUR') {
          this.router.navigate(['/professeur']).then(success => {
            console.log('Navigation vers /professeur:', success ? 'réussie' : 'échouée');
          });
        } else if (role === 'ETUDIANT') {
          this.router.navigate(['/etudiant']).then(success => {
            console.log('Navigation vers /etudiant:', success ? 'réussie' : 'échouée');
          });
        } else {
          console.warn('Aucun rôle correspondant, redirection vers /');
          this.router.navigate(['/']);
        }
      },
      error: (err) => {
        console.error('Erreur de connexion:', err);
        this.errorMessage = err.error?.message || 'Erreur de connexion';
      }
    });
  }
}