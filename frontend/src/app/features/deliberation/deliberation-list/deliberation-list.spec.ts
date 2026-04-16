import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeliberationListComponent } from './deliberation-list.component';

describe('DeliberationListComponent', () => {
  let component: DeliberationListComponent;
  let fixture: ComponentFixture<DeliberationListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeliberationListComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DeliberationListComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
