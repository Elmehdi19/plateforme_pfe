import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-filiere-dialog',
  standalone: true,
  imports: [CommonModule, FormsModule, MatDialogModule, MatButtonModule, MatInputModule],
  templateUrl: './filiere-dialog.component.html'
})
export class FiliereDialogComponent {

  filiere: any = { code: '', libelle: '' };

  constructor(
    public dialogRef: MatDialogRef<FiliereDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    if (data) {
      this.filiere = { ...data };
    }
  }

  save() {
    this.dialogRef.close(this.filiere);
  }

  close() {
    this.dialogRef.close();
  }
}
