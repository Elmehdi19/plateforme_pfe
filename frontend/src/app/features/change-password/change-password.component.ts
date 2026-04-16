import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router'; // ← ajout
import { AuthService } from '../../core/services/auth.service';
import { ToastService } from '../../shared/toast/toast.service';

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent {
  passwordForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private toastService: ToastService,
    private router: Router // ← injection
  ) {
    this.passwordForm = this.fb.group({
      currentPassword: ['', Validators.required],
      newPassword: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, { validators: this.passwordMatchValidator });
  }

  passwordMatchValidator(group: FormGroup) {
    const newPw = group.get('newPassword')?.value;
    const confirm = group.get('confirmPassword')?.value;
    return newPw === confirm ? null : { mismatch: true };
  }

  onSubmit() {
    if (this.passwordForm.invalid) return;

    const { currentPassword, newPassword } = this.passwordForm.value;

    this.authService.changePassword(currentPassword, newPassword).subscribe({
      next: (response) => {
        this.toastService.show(response, 'success');
        this.passwordForm.reset();
      },
      error: (err) => {
        const message = err.error || 'Erreur lors du changement de mot de passe';
        this.toastService.show(message, 'error');
      }
    });
  }

  // Nouvelle méthode pour retourner au tableau de bord selon le rôle
  goBack(): void {
    const role = this.authService.getRole()?.toUpperCase();
    if (role === 'ADMIN') {
      this.router.navigate(['/admin/dashboard']);
    } else if (role === 'PROFESSEUR') {
      this.router.navigate(['/professeur/dashboard']);
    } else if (role === 'ETUDIANT') {
      this.router.navigate(['/etudiant/dashboard']);
    } else {
      this.router.navigate(['/']); // fallback
    }
  }
}