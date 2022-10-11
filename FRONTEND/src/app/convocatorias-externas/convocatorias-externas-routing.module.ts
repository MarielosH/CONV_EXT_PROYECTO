import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListaConvocatoriasAspirantesComponent } from './lista-convocatorias-aspirantes/lista-convocatorias-aspirantes.component';
/*import { BajaComponent } from './baja/baja.component';
import { RegularizarComponent } from './regularizar/regularizar.component';*/
import { GestionCoordinadorComponent } from './gestion-coordinador/gestion-coordinador.component';
import { CreacionConvocatoriaComponent } from './creacion-convocatoria/creacion-convocatoria.component';
import { DetalleInformacionComponent } from './detalle-informacion/detalle-informacion.component';
import { EdicionConvocatoriaComponent } from './edicion-convocatoria/edicion-convocatoria.component';
import { PerfilSolicitudEmpleoComponent } from './perfil-solicitud-empleo/perfil-solicitud-empleo.component';
import { ListaJefevComponent } from './lista-jefev/lista-jefev.component';
import { BandejaComponent } from './bandeja/bandeja.component';
import { VerPerfilesComponent } from './ver-perfiles/ver-perfiles.component';
import { PerfilwizardComponent } from './perfilwizard/perfilwizard.component';

const routes: Routes = [
 /* {
    path: 'gestiones/:id',
    component: GestionesComponent
  },
  {
    path: 'dependencias',
    component: DependenciasComponent
  },
  {
    path: 'complementos',
    component: ComplementosComponent
  },*/
  {
    path: 'gestion-convocatorias',
    component: GestionCoordinadorComponent
  },
  {
    path: 'ver-convocatorias',
    component: ListaConvocatoriasAspirantesComponent
  },
  {
    path: 'crear-convocatoria',
    component: CreacionConvocatoriaComponent
  },
  {
    path: 'editar-convocatoria',
    component: EdicionConvocatoriaComponent
  },
  {
    path: 'perfil-solicitud-empleo',
    component: PerfilSolicitudEmpleoComponent
  },
  {
    path: 'detalle',
    component: DetalleInformacionComponent
  },
  {
    path: 'ver-convocatorias-jefe',
    component: ListaJefevComponent
  },
  {
    path: 'bandeja',
    component: BandejaComponent
  },
  {
    path: 'ver-perfiles',
    component: VerPerfilesComponent
  }, 
  {
    path: 'wizard',
    component: PerfilwizardComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ConvocatoriasExternasRoutingModule { }
