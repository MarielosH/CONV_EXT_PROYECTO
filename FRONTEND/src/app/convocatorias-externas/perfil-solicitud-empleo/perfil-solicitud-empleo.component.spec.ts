import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PerfilSolicitudEmpleoComponent } from './perfil-solicitud-empleo.component';

describe('PerfilSolicitudEmpleoComponent', () => {
  let component: PerfilSolicitudEmpleoComponent;
  let fixture: ComponentFixture<PerfilSolicitudEmpleoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PerfilSolicitudEmpleoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PerfilSolicitudEmpleoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
