import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleInformacionComponent } from './detalle-informacion.component';

describe('DetalleInformacionComponent', () => {
  let component: DetalleInformacionComponent;
  let fixture: ComponentFixture<DetalleInformacionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalleInformacionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleInformacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
