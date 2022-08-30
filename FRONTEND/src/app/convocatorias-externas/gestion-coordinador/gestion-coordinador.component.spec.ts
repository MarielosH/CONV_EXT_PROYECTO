import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionCoordinadorComponent  } from './gestion-coordinador.component';

describe('GestionCoordinadorComponent', () => {
  let component: GestionCoordinadorComponent;
  let fixture: ComponentFixture<GestionCoordinadorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestionCoordinadorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestionCoordinadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
