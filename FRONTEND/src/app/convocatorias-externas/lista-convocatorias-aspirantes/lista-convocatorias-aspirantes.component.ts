import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDatepickerModule, MatSelect, MatInput, MatButton, MatAutocomplete, MatChipInputEvent, MatAutocompleteSelectedEvent, MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { FormGroup, FormBuilder, Validators, ValidationErrors, FormControl, ValidatorFn, AbstractControl } from '@angular/forms';
import { Observable, Subscription, of } from 'rxjs';
import { HttpEvent, HttpRequest, HttpClient, HttpResponse } from '@angular/common/http';
import { ngf } from "angular-file"
import { DatePipe, Location } from '@angular/common';
import swal,{ SweetAlertOptions } from 'sweetalert2';
import { AuthService } from '../../recursos/auth.service';
import { startWith, map } from 'rxjs/operators';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { MatDialog, MatDialogRef} from '@angular/material/dialog';
import { Convocatorias } from 'app/constantes';
import { ConvocatoriaService } from '../convocatoria.service';
@Component({
  selector: 'app-lista-convocatorias-aspirantes',
  templateUrl: './lista-convocatorias-aspirantes.component.html',
  styleUrls: ['./lista-convocatorias-aspirantes.component.scss']
})
export class ListaConvocatoriasAspirantesComponent implements OnInit {

  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
    
  dependencias;
  idEstado=1;
  session;
  constantes;
  selected; 
  filtered:Observable<any[]>[];
  viewCIDEJ = false;
  viewCIT = false;
  dataSource = new MatTableDataSource<any>();
    
  constructor(public authService: AuthService,
    public HttpClient: HttpClient,private fb: FormBuilder,  private _location: Location, private datePipe : DatePipe,
    private router: Router,private route:ActivatedRoute,public dialog: MatDialog, private convocatoriaService: ConvocatoriaService
  ) { 
    this.session = this.authService.getsession().SESSION;
    this.constantes = this.authService.getsession().CONSTANTES;
      }

  ngOnInit() {
    
    this.route.params.subscribe(
      (params: Params) => {
        this.loadDependencias();
      }
    );

    this.valProfile();
    this.getListaAplicadas();
  }

  getListaAplicadas(){
    this.convocatoriaService.showApplyConv(localStorage.getItem('informacion_personal_id')).subscribe(data => {
      console.log(data);
      if (data.length > 0) {
        this.dataSource.data = [...data];
      }
    });
  }

  valProfile(){
    for(var i=0; i<this.session.PERFILES.length; i++){
       if(this.constantes.CIDEJ == this.session.PERFILES[i].ID_PERFIL){
           this.viewCIDEJ = true;
       }else if(this.constantes.CIT == this.session.PERFILES[i].ID_PERFIL){
        this.viewCIT = true;
      }
   }
  } 

  loadDependencias(){
      this.dependencias = Convocatorias;
      this.dataSource = new MatTableDataSource(this.dependencias);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
  }


 filterMatTable(filterValue: any){
  this.dataSource.filter = filterValue;
 }

  //Acceder al modal de Correo
  verInformacion(){
    this.router.navigate(['convocatorias-externas/detalle']);
  }

  modalCorreo(dataDep){
/*
    const dialogRef = this.dialog.open(CorreoComponent, {
      width: '800px',
      height: '400',
      data: dataDep});

      dialogRef.afterClosed().subscribe(result => {
        if(result != undefined){
             this.saveCorreo(result);
        }
      });*/
    
  }

  //Guardar Correo 

  saveCorreo(dataCorreo){

    let correoDep = {  
         ID_DEPENDENCIA_CORREO : dataCorreo.ID_DEPENDENCIA_CORREO,
         DEPENDENCIA : dataCorreo.DEPENDENCIA,
         CORREO_ELECTRONICO : dataCorreo.CORREO_ELECTRONICO,
         IP : "",
         ID_USUARIO_REGISTRO : this.session.ID_USUARIO
    };
 /*
    this.mantenimientoDependenciaService.correoDependencia(correoDep).subscribe(
       data => {
         if(data.result=='OK'){
           swal("Correo Agregado Exitosamente", "", "success")
          }else{
           swal("Error", data.msj, "error")
         } 
       });*/
 
   }
 
  //Acceder al modal de código de despacho
  
  modalDespacho(dataDep){
/*
    const dialogRef = this.dialog.open(CodigoDespachoComponent, {
      width: '800px',
      height: '400',
      data: dataDep});

      dialogRef.afterClosed().subscribe(result => {
        if(result != undefined){
           this.saveDespacho(result); 
           //this.validaDespacho(result); 
        }
      });
    */
  }

  //Guardar Despacho 

  saveDespacho(dataDespacho){

    let codDespacho = {  
         ID_DEP_RH_CIDEJ : dataDespacho.ID_DEP_RH_CIDEJ,
         DEPENDENCIA : dataDespacho.DEPENDENCIA,
         ID_DESPACHO : dataDespacho.ID_DESPACHO,
         IP : "",
         ID_USUARIO_REGISTRO : this.session.ID_USUARIO
    };
 /*
     this.mantenimientoDependenciaService.codDespachoDependencia(codDespacho).subscribe(
       data => {
         if(data.result=='OK'){
           swal("Código Despacho Agregado Exitosamente", "", "success")
          }else{
           swal("Error", data.msj, "error")
         } 
       });
 */
   }

}
