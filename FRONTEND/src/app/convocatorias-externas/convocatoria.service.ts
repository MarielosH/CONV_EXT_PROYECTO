import { Injectable } from '@angular/core';
import { AppconfigService } from '../appconfig.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ConvocatoriaService {

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


  constructor(private http: HttpClient, private appSettings: AppconfigService) { }
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      return of(result as T);
    };
  }
}
