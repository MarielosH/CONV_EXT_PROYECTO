import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleConvocatoriaComponent } from './detalle-convocatoria.component';

describe('DetalleConvocatoriaComponent', () => {
  let component: DetalleConvocatoriaComponent;
  let fixture: ComponentFixture<DetalleConvocatoriaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalleConvocatoriaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleConvocatoriaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
