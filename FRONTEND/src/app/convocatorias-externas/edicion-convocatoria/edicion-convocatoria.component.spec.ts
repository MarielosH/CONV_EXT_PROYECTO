import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EdicionConvocatoriaComponent } from './edicion-convocatoria.component';

describe('EdicionConvocatoriaComponent', () => {
  let component: EdicionConvocatoriaComponent;
  let fixture: ComponentFixture<EdicionConvocatoriaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EdicionConvocatoriaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EdicionConvocatoriaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
