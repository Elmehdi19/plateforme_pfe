import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SemestresListComponent } from './semestres-list.component';

describe('SemestresListComponent', () => {
  let component: SemestresListComponent;
  let fixture: ComponentFixture<SemestresListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SemestresListComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SemestresListComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
