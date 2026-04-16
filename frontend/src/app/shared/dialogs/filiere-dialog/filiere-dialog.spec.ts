import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FiliereDialogComponent } from './filiere-dialog.component';

describe('FiliereDialogComponent', () => {
  let component: FiliereDialogComponent;
  let fixture: ComponentFixture<FiliereDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FiliereDialogComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(FiliereDialogComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
