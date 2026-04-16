import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EtudiantsFormComponent } from './etudiants-form.component';

describe('EtudiantsFormComponent', () => {
  let component: EtudiantsFormComponent;
  let fixture: ComponentFixture<EtudiantsFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EtudiantsFormComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(EtudiantsFormComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
