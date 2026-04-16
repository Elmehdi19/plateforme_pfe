import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SemestresFormComponent } from './semestres-form.component';

describe('SemestresFormComponent', () => {
  let component: SemestresFormComponent;
  let fixture: ComponentFixture<SemestresFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SemestresFormComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SemestresFormComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
