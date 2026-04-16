import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ToastService {
  show(message: string, type: 'success' | 'error' | 'info' = 'info'): void {
    // Implémentation simple avec alert, mais vous pouvez utiliser une bibliothèque comme ngx-toastr
    alert(`${type.toUpperCase()}: ${message}`);
  }
}