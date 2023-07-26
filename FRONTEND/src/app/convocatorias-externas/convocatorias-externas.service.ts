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
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + 'Convocatorias-Externas/getMunicipios/' + depto )
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

  getListaEtniasConv(): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + 'Convocatorias-Externas/getEtnias')
      .pipe(
        catchError(this.handleError('getEtnias', []))
      );
  }


  constructor(private http: HttpClient, private appSettings: AppconfigService) { }
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      return of(result as T);
    };
  }

}