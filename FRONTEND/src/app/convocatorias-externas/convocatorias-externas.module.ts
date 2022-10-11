import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../app.module';
import { ngfModule} from "angular-file";

import { ConvocatoriasExternasRoutingModule } from './convocatorias-externas-routing.module';
import { GestionCoordinadorComponent } from './gestion-coordinador/gestion-coordinador.component';
/*import { RegularizarComponent } from './regularizar/regularizar.component';
import { BajaComponent } from './baja/baja.component';*/
import { ListaConvocatoriasAspirantesComponent } from './lista-convocatorias-aspirantes/lista-convocatorias-aspirantes.component';
import { DetalleInformacionComponent } from './detalle-informacion/detalle-informacion.component';
import { CreacionConvocatoriaComponent } from './creacion-convocatoria/creacion-convocatoria.component';
import { EdicionConvocatoriaComponent } from './edicion-convocatoria/edicion-convocatoria.component';
import { PerfilSolicitudEmpleoComponent } from './perfil-solicitud-empleo/perfil-solicitud-empleo.component';
import { ListaJefevComponent } from './lista-jefev/lista-jefev.component';
import { BandejaComponent } from './bandeja/bandeja.component';
import { VerPerfilesComponent } from './ver-perfiles/ver-perfiles.component';
import { PerfilwizardComponent } from './perfilwizard/perfilwizard.component';

@NgModule({
  declarations: [
    /*, ActualizacionComponent, RegularizarComponent, BajaComponent, */
  GestionCoordinadorComponent,
  ListaConvocatoriasAspirantesComponent,
  DetalleInformacionComponent,
  CreacionConvocatoriaComponent,
  EdicionConvocatoriaComponent,
  PerfilSolicitudEmpleoComponent,
  ListaJefevComponent,
  BandejaComponent,
  VerPerfilesComponent,
  PerfilwizardComponent],
  imports: [
    CommonModule,
    ConvocatoriasExternasRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    ngfModule
  ],
  entryComponents:[]
})
export class ConvocatoriasExternasModule { }
