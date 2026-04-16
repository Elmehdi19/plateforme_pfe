import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElementsFormComponent } from './elements-form.component';

describe('ElementsFormComponent', () => {
  let component: ElementsFormComponent;
  let fixture: ComponentFixture<ElementsFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ElementsFormComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ElementsFormComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
