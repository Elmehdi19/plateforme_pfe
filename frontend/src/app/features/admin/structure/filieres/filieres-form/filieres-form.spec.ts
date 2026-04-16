import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilieresFormComponent } from './filieres-form.component';

describe('FilieresFormComponent', () => {
  let component: FilieresFormComponent;
  let fixture: ComponentFixture<FilieresFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FilieresFormComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(FilieresFormComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
