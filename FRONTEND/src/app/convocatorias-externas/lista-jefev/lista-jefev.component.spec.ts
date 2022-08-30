import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaJefevComponent } from './lista-jefev.component';

describe('ListaJefevComponent', () => {
  let component: ListaJefevComponent;
  let fixture: ComponentFixture<ListaJefevComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListaJefevComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListaJefevComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
