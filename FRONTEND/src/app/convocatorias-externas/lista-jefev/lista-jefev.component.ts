import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDatepickerModule, MatSelect, MatInput, MatButton, MatAutocomplete, MatChipInputEvent, MatAutocompleteSelectedEvent, MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { FormGroup, FormBuilder, Validators, ValidationErrors, FormControl, ValidatorFn, AbstractControl } from '@angular/forms';
import { Observable, Subscription, of } from '../../../../node_modules/rxjs';
import { HttpEvent, HttpRequest, HttpClient, HttpResponse } from '../../../../node_modules/@angular/common/http';
import { ngf } from "angular-file"
import { DatePipe, Location } from '@angular/common';
import swal from 'sweetalert';
import { AuthService } from '../../recursos/auth.service';
import { startWith, map } from '../../../../node_modules/rxjs/operators';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { MatDialog, MatDialogRef} from '@angular/material/dialog';
import { Convocatorias } from 'app/constantes';

@Component({
  selector: 'app-lista-jefev',
  templateUrl: './lista-jefev.component.html',
  styleUrls: ['./lista-jefev.component.scss']
})
export class ListaJefevComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
    
  dependencia: FormGroup;
  floatLabelControl = new FormControl('never');
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
    private router: Router,private route:ActivatedRoute,public dialog: MatDialog
  ) { 
    this.session = this.authService.getsession().SESSION;
    this.constantes = this.authService.getsession().CONSTANTES;
      }

  ngOnInit() {
    
    this.dependencia = this.fb.group({
      floatLabel: this.floatLabelControl,
      busqueda: [''],      
      selEstados: ['']      
    });
    
    this.route.params.subscribe(
      (params: Params) => {
        this.loadDependencias();
      }
    );

    this.valProfile();

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
    /*this.mantenimientoDependenciaService.getDepedenciasComplementos().subscribe(
      data => {
        if(data.length>0){
        this.dependencias = data;
      }else{ 
          //swal("Dependencias Nominales", "No se han encontrado dependencias", "info")
        }
       
      });*/
      this.dependencias = Convocatorias;
      this.dataSource = new MatTableDataSource(this.dependencias);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
  }


 filterMatTable(filterValue: any){
  this.dataSource.filter = filterValue;
 }

  //Acceder al modal de Correo
  editar(data){
    console.log(data);
    this.router.navigate(['convocatorias-externas/editar-convocatoria']);
  }

  crear(data){
    console.log(data);
    this.router.navigate(['convocatorias-externas/crear-convocatoria']);
  }

  eliminar(data) {
    console.log(data);
    swal({
      title: "Are you sure?",
      text: "Once deleted, you will not be able to recover this imaginary file!",
      icon: "warning",
      buttons: [true],
      dangerMode: true,
    })
    .then((willDelete) => {
      if (willDelete) {
        swal("Poof! Your imaginary file has been deleted!", {
          icon: "success",
        });
      } else {
        swal("Your imaginary file is safe!");
      }
    });
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
      });
    */
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
       });
 */
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
