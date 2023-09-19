import { Injectable } from '@angular/core';
import { AppconfigService } from '../appconfig.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})

export class ConvocatoriasExternasService {

  getListaDepartamentosConv(): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + 'Convocatorias-Externas/getDepartamentos')
      .pipe(
        catchError(this.handleError('getListaDepartamentoConv', []))
      );
  }

  getListaMunicipioConv(depto): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + 'Convocatorias-Externas/getMunicipios/' + depto)
      .pipe(
        catchError(this.handleError('getListaMunicipioConv', []))
      );
  }

  getListaComunidadLinguisticaConv(): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + 'Convocatorias-Externas/getComunidadLinguistica')
      .pipe(
        catchError(this.handleError('getListaComunidadLinguisticaConv', []))
      );
  }

  getListaIdiomasExtranjeros(): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + 'Convocatorias-Externas/getIdiomas')
      .pipe(
        catchError(this.handleError('getListaIdiomasExtranjeros', []))
      );
  }

  insPerfilUsuario(perfilUsuario): Observable<any> {
    return this.http.post<any>(this.appSettings.restApiServiceBaseUri + 'Convocatorias-Externas/inPerfilSolicitudEmpleo', perfilUsuario)
      .pipe(
        catchError(this.handleError('insPerfilUsuario', []))
      );
  }

  getListaParentescoConv(): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + 'Convocatorias-Externas/getParentesco')
      .pipe(
        catchError(this.handleError('getListaParentescoConv', []))
      );
  }

  getListaEstadoCivilConv(): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + 'Convocatorias-Externas/getEstadoCivil')
      .pipe(
        catchError(this.handleError('getListaEstadoCivilConv', []))
      );
  }

  getListaEtniasConv(): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + 'Convocatorias-Externas/getEtnias')
      .pipe(
        catchError(this.handleError('getEtnias', []))
      );
  }

  insConvocatoria(convVal): Observable<any> {
    return this.http.post<any>(this.appSettings.restApiServiceBaseUri + 'Convocatoria/inConvocatoria', convVal)
      .pipe(
        catchError(this.handleError('insConvocatoria', []))
      );
  }

  getConvocatorias(): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + 'Convocatoria/getConvocatorias')
      .pipe(
        catchError(this.handleError('Mostrar Convocatorias', []))
      );
  }

  getPerfilUsuarioByDPI(dpi): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + 'Convocatorias-Externas/getPerfilSolicitudDpi/' + dpi)
      .pipe(
        catchError(this.handleError('getPerfilUsuarioByDPI', []))
      );
  }

  constructor(private http: HttpClient, private appSettings: AppconfigService) { }
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      return of(result as T);
    };
  }

  borFamiliar(id): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + `Familiar/borFamiliar/id/${id}`)
      .pipe(
        catchError(this.handleError('borFamiliar', []))
      );
  }

  borFamiliarLaborandoOJ(id): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + `Familiar-Laborando-OJ/borFamiliarLaborandoOJ/id/${id}`)
      .pipe(
        catchError(this.handleError('borFamiliarLaborandoOJ', []))
      );
  }

  borPasantiaOJ(id): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + `Pasantia-OJ/borPasantiaOJ/id/${id}`)
      .pipe(
        catchError(this.handleError('borPasantiaOJ', []))
      );
  }

  borExperienciaLaboral(id): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + `Experiencia-Laboral/borExperienciaLaboral/id/${id}`)
      .pipe(
        catchError(this.handleError('borExperienciaLaboral', []))
      );
  }

  borExperienciaLaboralOJ(id): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + `Experiencia-Laboral-OJ/borExperienciaLaboralOJ/id/${id}`)
      .pipe(
        catchError(this.handleError('borExperienciaLaboralOJ', []))
      );
  }

  borReferenciaPersonal(id): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + `Referencia-Personal/borReferenciaPersonal/id/${id}`)
      .pipe(
        catchError(this.handleError('borReferenciaPersonal', []))
      );
  }

  borIdiomasUsuario(usuarioId, idiomaId): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + `Idiomas-Usuario/borIdiomasUsuarioidioma/idioma/${idiomaId}/usuario/${usuarioId}`)
      .pipe(
        catchError(this.handleError('borIdiomasUsuario', []))
      );
  }

}