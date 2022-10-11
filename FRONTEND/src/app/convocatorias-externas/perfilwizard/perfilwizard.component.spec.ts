import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PerfilwizardComponent } from './perfilwizard.component';

describe('PerfilwizardComponent', () => {
  let component: PerfilwizardComponent;
  let fixture: ComponentFixture<PerfilwizardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PerfilwizardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PerfilwizardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
