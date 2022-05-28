import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SummonersPageComponent } from './summoners-page.component';

describe('SummonersPageComponent', () => {
  let component: SummonersPageComponent;
  let fixture: ComponentFixture<SummonersPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SummonersPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SummonersPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
