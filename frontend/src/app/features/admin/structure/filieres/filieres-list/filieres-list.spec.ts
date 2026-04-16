import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilieresListComponent } from './filieres-list.component';

describe('FilieresListComponent', () => {
  let component: FilieresListComponent;
  let fixture: ComponentFixture<FilieresListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FilieresListComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(FilieresListComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
