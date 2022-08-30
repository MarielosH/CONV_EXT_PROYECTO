import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VerPerfilesComponent } from './ver-perfiles.component';

describe('VerPerfilesComponent', () => {
  let component: VerPerfilesComponent;
  let fixture: ComponentFixture<VerPerfilesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VerPerfilesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VerPerfilesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
