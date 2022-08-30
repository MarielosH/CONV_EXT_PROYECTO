import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaConvocatoriasAspirantesComponent } from './lista-convocatorias-aspirantes.component';

describe('ListaConvocatoriasAspirantesComponent', () => {
  let component: ListaConvocatoriasAspirantesComponent;
  let fixture: ComponentFixture<ListaConvocatoriasAspirantesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListaConvocatoriasAspirantesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListaConvocatoriasAspirantesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
