import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotesFormComponent } from './notes-form.component';

describe('NotesFormComponent', () => {
  let component: NotesFormComponent;
  let fixture: ComponentFixture<NotesFormComponent                                                  >;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NotesFormComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NotesFormComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
