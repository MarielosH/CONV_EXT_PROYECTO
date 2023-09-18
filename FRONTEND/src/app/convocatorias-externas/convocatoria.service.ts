import { Injectable } from '@angular/core';
import { AppconfigService } from '../appconfig.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ConvocatoriaService {

  uploadDocument(file: any, dpi: string): Observable<any>{
    const formData = new FormData();
    formData.append('file', file);
    formData.append('directory', dpi);
    return this.http.post<any>(this.appSettings.restApiServiceBaseUri + 'AplicarConvocatoria/aplicar', formData)
    .pipe(
      catchError(this.handleError('uploadFile', []))
    );
  }

  applyConv(idUsuario: any, idConv: any, body: any): Observable<any>{
    return this.http.post<any>(this.appSettings.restApiServiceBaseUri + `Estado-Aplicacion-Conv/inEstadoAplicacionConv/usuario/${idUsuario}/convocatoria/${idConv}`, body)
    .pipe(
      catchError(this.handleError('inEstarConvocatoria',[]))
    )
  }

  insConvocatoria(convVal): Observable<any> {
    return this.http.post<any>(this.appSettings.restApiServiceBaseUri + 'Convocatoria/inConvocatoria', convVal)
      .pipe(
        catchError(this.handleError('insConvocatoria', []))
      );
  }

  modConvocatoria(convVal, idConv): Observable<any> {
    return this.http.post<any>(this.appSettings.restApiServiceBaseUri + `Convocatoria/modConvocatoria/id/${idConv}`, convVal)
      .pipe(
        catchError(this.handleError('modConvocatoria', []))
      );
  }

  borConvocatoria(idConv): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + `Convocatoria/borConvocatoria/id/${idConv}`)
      .pipe(
        catchError(this.handleError('borConvocatoria', []))
      );
  }


  insDetalleConv(detalleVal, uri, conv): Observable<any> {
    return this.http.post<any>(this.appSettings.restApiServiceBaseUri + uri + `/in/convocatoria/${conv}`, detalleVal)
      .pipe(
        catchError(this.handleError('insDetalleConv', []))
      )
  }

  modDetalleConv(detalleVal, uri, detalleConv): Observable<any> {
    return this.http.post<any>(this.appSettings.restApiServiceBaseUri + uri + `/mod/id/${detalleConv}`, detalleVal)
      .pipe(
        catchError(this.handleError('modDetalleConv', []))
      )
  }

  borDetalleConv(uri, detalleConv): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + uri + `/bor/id/${detalleConv}`)
      .pipe(
        catchError(this.handleError('modDetalleConv', []))
      )
  }

  getDetalleConv(uri, idConv): Observable<any> {
    return this.http.get<any>(this.appSettings.restApiServiceBaseUri + uri + `/get/convocatoria/${idConv}`)
      .pipe(
        catchError(this.handleError('getDetalleConv', []))
      )
  }

  constructor(private http: HttpClient, private appSettings: AppconfigService) { }
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      return of(result as T);
    };
  }
}
