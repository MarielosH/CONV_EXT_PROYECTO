import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DepartamentoComponent } from './departamento/departamento.component';
import { MunicipioComponent } from './municipio/municipio.component';

@NgModule({
  declarations: [DepartamentoComponent, MunicipioComponent],
  imports: [
    CommonModule
  ]
})
export class CompartidosModule { }
