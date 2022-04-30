import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookRegularComponent } from './book-regular.component';

describe('BookRegularComponent', () => {
  let component: BookRegularComponent;
  let fixture: ComponentFixture<BookRegularComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookRegularComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BookRegularComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
