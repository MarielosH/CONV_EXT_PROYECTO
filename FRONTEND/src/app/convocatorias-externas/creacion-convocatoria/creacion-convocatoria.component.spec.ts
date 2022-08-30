import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreacionConvocatoriaComponent } from './creacion-convocatoria.component';

describe('CreacionConvocatoriaComponent', () => {
  let component: CreacionConvocatoriaComponent;
  let fixture: ComponentFixture<CreacionConvocatoriaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreacionConvocatoriaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreacionConvocatoriaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
