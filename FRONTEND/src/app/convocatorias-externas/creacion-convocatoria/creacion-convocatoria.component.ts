import { Component, OnInit, ViewChild, ChangeDetectorRef, Input } from '@angular/core';
import { MatDatepickerModule, MatSelect, MatInput, MatButton, MatAutocomplete, MatChipInputEvent, MatAutocompleteSelectedEvent, MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { FormGroup, FormBuilder, Validators, ValidationErrors, FormControl, ValidatorFn, AbstractControl } from '@angular/forms';
import { Observable, Subscription, BehaviorSubject } from '../../../../node_modules/rxjs';
import { HttpEvent, HttpRequest, HttpClient, HttpResponse } from '../../../../node_modules/@angular/common/http';
import { ngf } from "angular-file"
import { DatePipe, Location, CommonModule } from '@angular/common';
import swal from 'sweetalert2';
import { AuthService } from '../../recursos/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { MatDialog, MatDialogRef} from '@angular/material/dialog';
import { ConvocatoriaService } from '../convocatoria.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap'
import Detalle  from '../detallesConv'
import * as $ from 'jquery';

interface Funcionalidad {
  value: string;
  viewValue: string;
}

interface Conector {
  value: string;
  viewValue: string;
}

interface Filex extends Blob {
  lastModified: number;
  lastModifiedDate: Date;
  name: string;
  webkitRelativePath: string;
  observ: string;
}

@Component({
  selector: 'app-creacion-convocatoria',
  templateUrl: './creacion-convocatoria.component.html',
  styleUrls: ['./creacion-convocatoria.component.scss']
})
export class CreacionConvocatoriaComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @Input() idConvoctoria_in: number;

  creation: FormGroup;
  dtConv: FormGroup;
  dependencia: FormGroup;
  //hideRequiredControl = new FormControl(false);
  floatLabelControl = new FormControl('none');
  convFloatLabelCtrl = new FormControl('none');
  departamentos;
  listArea;
  municipios;
  ngf: ngf;
  validaCodDep=false;
  validaCodPres=false;
  validaCreate=false;
  valDepto;
  session;
  iniciaValDep = false;
  iniciaValPres = false;
  validaFecha = false;
  validaFechaPublica = false;
  checked = false;
  id;
  convDetalles;
  dataSource = new MatTableDataSource<any>();
  tmp_id = '';
  
/* Controles */
  titulo;
  descripcion;
  objetivo;
  experiencia;
  gafeteDep;
  documentoDep;
  selConector;
  selFuncionalidad;
  selArea;
  fechaInicioVigencia;
  fechaFinVigencia;
  inicioVigencia;
  selMunicipio;
  selDepartamento;
  referencia;
  inicioVigRef;
  chkRefVigencia;

  detalleOption = new Map<number, any>();

  constructor(public authService: AuthService,
    public HttpClient: HttpClient,private fb: FormBuilder,  private _location: Location, private datePipe : DatePipe,
    private router: Router,private route:ActivatedRoute, public dialog: MatDialog,
    private convocatoriasService: ConvocatoriaService, private changeDetectorRef: ChangeDetectorRef,
  ) { 
    this.session=this.authService.getsession().SESSION;
  }

  ngOnInit() {
    const conv = JSON.parse(localStorage.getItem('convocatoria'));
    this.creation = this.fb.group({
      floatLabel: this.floatLabelControl,
      titulo: ['', Validators.required],      
      descripcion: ['', Validators.required],      
      objetivo: ['', Validators.required],      
      experiencia: ['', Validators.required],      
      fechaInicioVigencia: ['', Validators.required],      
      fechaFinVigencia: ['', Validators.required],      
    });

    this.dtConv = this.fb.group({
      floatLabelC: this.convFloatLabelCtrl,
      tipoDetalle: ['',Validators.required],
      descripcion: ['',Validators.required],
    });

    // this.creation.get('titulo').valueChanges.subscribe((v)=> {if(v.length > 3){this.iniciaValDep = true }else{this.iniciaValDep = false}} );
    // this.creation.get('descripcion').valueChanges.subscribe((v)=> {if(v.length > 3){this.iniciaValPres = true }else{this.iniciaValPres = false}});

    this.dependencia = this.fb.group({
      floatLabel: this.floatLabelControl,
      busqueda: [''],      
      selEstados: ['']      
    });
    this.convDetalles = []
    this.loadDetalles();

    if (conv) {
        console.log(conv)
        this.creation.setValue({
          floatLabel: this.floatLabelControl,
          titulo: conv.TITULO,
          descripcion: conv.DESCRIPCION,
          objetivo: conv.OBJETIVO,
          experiencia: conv.EXPERIENCIA,
          fechaInicioVigencia: conv.FECHA_INICIO_VIGENCIA,
          fechaFinVigencia: conv.FECHA_FIN_VIGENCIA,
        })
        this.id = parseInt(conv.ID_CONVOCATORIA)
        this.validaCreate = !this.validaCreate;
        // recolectar todas los detalles de la conv
        this.cargarDetalles(parseInt(conv.ID_CONVOCATORIA))
      }
    
    
    //  this.convDetalles.push({
    //   "habilidadTecnica": "Saber que estas haciendo ",
    //   "data": "Saber que estas haciendo ",
    //   "ID_USUARIO": "27815",
    //   "type": 2,
    //   "id": 182,
    //   "id_detalle": 21,
    //   "titulo": "Habilidad tecnica"
    // })
    // this.convDetalles.push({
    //   "requisitoEducacion": "Computación aplicada",
    //   "data": "Computación aplicada",
    //   "ID_USUARIO": "27815",
    //   "type": 4,
    //   "id": 182,
    //   "id_detalle": 21,
    //   "titulo": "Educacion"
    // })
    // this.convDetalles.push({
    //   "papeleria": "Antecedentes",
    //   "data": "Antecedentes",
    //   "ID_USUARIO": "27815",
    //   "type": 5,
    //   "id": 182,
    //   "id_detalle": 21,
    //   "titulo": "Papeleria"
    // })
    // console.log(this.convDetalles)
    //
    /*this.mantenimientoDependenciaService.getListaDepartamento().subscribe(
      data => {
        this.departamentos = data;
      });

    this.mantenimientoDependenciaService.getListaArea().subscribe(
      data => {
        this.listArea = data;
      });*/

  }

  cargarDetalles(id){
    for (const key in Detalle) {
      this.convocatoriasService.getDetalleConv(Detalle[key][1], id).subscribe(
        data => {
          if (data.length > 0) {
            console.log(data)
            this.dataSource.data = [...data, ...this.dataSource.data];
          } 
        }
      )
    }
    
  }
  

  listFuncionalidad: Funcionalidad[] = [
    { value: 'T', viewValue: 'TURNO' },
    { value: 'N', viewValue: 'JORNADA' }
  ];

  listConector: Conector[] = [
    { value: 'DE', viewValue: 'DE' },
    { value: 'DEL', viewValue: 'DEL' },
    { value: 'DE LA', viewValue: 'DE LA' }
  ];

  loadDetalles(){
    this.dataSource = new MatTableDataSource(this.convDetalles);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  filterMatTable(filterValue: any){
    this.dataSource.filter = filterValue;
   }

  getMunicipio(event: any, valDepto: number) {
   /* this.creation.controls.selMunicipio.setValue(0);
    if (event.isUserInput) {
      this.mantenimientoDependenciaService.getListaMunicipio(valDepto).subscribe(
        data => {
          this.municipios = data;
        });
    }*/
  }


  valCodDep(valor : any): boolean{
    /*
    if(valor !== null && valor !== undefined &&  valor !== "" && this.iniciaValDep){
    this.mantenimientoDependenciaService.validaCodigosCreacion(valor,1).subscribe(
      data => {
        this.validaCodDep = data.id !== 0;       
      });

    }else{
      this.validaCodDep = false;
    }
*/
    return this.validaCodDep;
  }


  valCodPres(valor : any): boolean{
    /*
    if(valor !== null && valor !== undefined &&  valor !== "" && this.iniciaValPres){
    this.mantenimientoDependenciaService.validaCodigosCreacion(valor,2).subscribe(
      data => {
        this.validaCodPres = data.id !== 0;       
      });
    }else{
      this.validaCodPres = false;
    }
*/
    return this.validaCodPres;
  }

  asignaNombreDoc(valor: any) : void{
    this.creation.controls.documentoDep.setValue(valor);
  }  

  asignarRequerido(valor:any){
    if(this.checked){
      this.creation.controls.inicioVigRef.setValidators(Validators.required);
      this.creation.controls.inicioVigencia.setValidators([]);
      this.creation.controls.inicioVigencia.updateValueAndValidity();
    }else{
    this.creation.controls.inicioVigencia.setValidators(Validators.required);
    this.creation.controls.inicioVigRef.setValidators([]);
    this.creation.controls.inicioVigRef.updateValueAndValidity();
  }
  }

  get f() { return this.creation.controls; }



  crearDependencia(){

    let dependencia={
     
     CODIGO_DEPENDENCIA:this.creation.value.titulo,
     CODIGO_PRESUPUESTARIO:this.creation.value.descripcion,
     NOMBRE_DEPENDENCIA:this.creation.value.objetivo.toUpperCase(),
     NOMBRE_GAFETE:this.creation.value.gafeteDep.toUpperCase(),
     NOMBRE_ABREVIADO:this.creation.value.cortoDep.toUpperCase(),
     NOMBRE_DOCUMENTO:this.creation.value.documentoDep.toUpperCase(),
     CONECTOR:this.creation.value.selConector,
     FECHA_DEL_ACUERDO:this.datePipe.transform(this.creation.value.fechaInicioVigencia, 'dd/MM/yyyy'),
     FECHA_ENTRA_VIGENCIA:this.datePipe.transform(this.creation.value.inicioVigencia, 'dd/MM/yyyy'),     
     FECHA_ANULACION:"",
     FECHA_PUBLICACION:this.datePipe.transform(this.creation.value.fechaFinVigencia, 'dd/MM/yyyy'),
     OBS_FECHA_VIGENCIA:this.creation.value.inicioVigRef.toUpperCase(),
     REFERENCIA:this.creation.value.referencia.toUpperCase(),
     FUNCION_UNIDAD:this.creation.value.selFuncionalidad,
     DEPARTAMENTO:this.creation.value.selDepartamento,
     MUNICIPIO:this.creation.value.selMunicipio,
     TIPO_AREA:this.creation.value.selArea,
     IP:"",
     ID_USUARIO_REGISTRO:this.session.ID_USUARIO,
     ARCHIVO:"",
     NOMBRE_ARCHIVO:"",
     ID_SOLICITUD:""
    }

    if(this.files.length!=0){
      this.files.forEach(element => {   
        this.getBase64(element).then(
          data => {

            if(data){

              dependencia.NOMBRE_ARCHIVO = element.name;
              dependencia.ARCHIVO = data.toString();
              console.log("objeto " + dependencia); 
              this.saveDependencia(dependencia);

            }
          }
        );
      });    
    }

  }

  
  saveDependencia(dependencia){
   /* this.mantenimientoDependenciaService.insDependencia(dependencia).subscribe(
      data=>{
        if(data.result=='OK'){
         swal("Solicitud de Dependencia Creada", data.msj, "success")
         this.router.navigate(['/mantenimientos/gestiones/1']);
        }else{
         swal("Error", data.msj, "error")
       }
      })*/
  }

  parseDate(dateString: any): any {
    if (dateString) {        
      if(this.valDates()){
      swal("Fecha Inválida", "Fecha Inicio de Vigencia no puede ser anterior a Fecha de Acuerdo", "info")
      }
        return this.datePipe.transform(dateString, 'yyyy-MM-dd')
    } else {
        return null;
    }
  }

  parseDatePublicacion(dateString: any): any {
    if (dateString) {        
      if(this.valDatesPublicacion()){
      swal("Fecha Inválida", "Fecha de Publicación no puede ser anterior a Fecha de Acuerdo", "info")
      }
        return this.datePipe.transform(dateString, 'yyyy-MM-dd')
    } else {
        return null;
    }
  }


  valDates(){
    let acuerdo = new Date(this.creation.value.fechaInicioVigencia);
    let vigencia = new Date(this.creation.value.inicioVigencia);
    
   this.validaFecha = false;
   
    if(acuerdo.getFullYear() > vigencia.getFullYear()){
      this.validaFecha = true;
    }else if(acuerdo.getMonth() == vigencia.getMonth() && acuerdo.getFullYear() == vigencia.getFullYear()){
      this.validaFecha = acuerdo.getDate() > vigencia.getDate();
    }else if(acuerdo.getMonth() > vigencia.getMonth() && acuerdo.getFullYear() == vigencia.getFullYear()){
      this.validaFecha = true;
    }

    return this.validaFecha;
  }

  valDatesPublicacion(){
    let acuerdo = new Date(this.creation.value.fechaInicioVigencia);
    let publicacion = new Date(this.creation.value.fechaFinVigencia);
    
   this.validaFechaPublica = false;
   
    if(acuerdo.getFullYear() > publicacion.getFullYear()){
      this.validaFechaPublica = true;
    }else if(acuerdo.getMonth() == publicacion.getMonth() && acuerdo.getFullYear() == publicacion.getFullYear()){
      this.validaFechaPublica = acuerdo.getDate() > publicacion.getDate();
    }else if(acuerdo.getMonth() > publicacion.getMonth() && acuerdo.getFullYear() == publicacion.getFullYear()){
      this.validaFechaPublica = true;
    }

    return this.validaFechaPublica;
  }

  verificaValidaciones(){
    if(this.files.length==0 || this.validaCodDep || this.validaCodPres || this.validaFecha || this.validaFechaPublica){
         return true;
    }

    return false;
  }

  /* upload file*/
  accept = '*'
  files: Filex[] = []
  progress: number
  hasBaseDropZoneOver: boolean = false
  httpEmitter: Subscription
  httpEvent: HttpEvent<{}>
  lastFileAt: Date

  sendableFormData: FormData//populated via ngfFormData directive 
   
  cancel() {
    this.progress = 0
    if (this.httpEmitter) {
      //console.log('cancelled')
      this.httpEmitter.unsubscribe()
    }
  }

  getDate() {
    return new Date()
  }

  getBase64(file) {
    return new Promise((resolve, reject) => {
      let reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        let encoded = reader.result.toString().replace(/^data:(.*;base64,)?/, '');
        resolve(encoded);
      };
      reader.onerror = error => reject(error);
    });
  }

  /* fin upload file*/

  cancelar(){
    this.clean()
    this.router.navigate(['/mantenimientos/gestiones/1']);
  }

  crearConvocatoria(){
    let convocatoria = {
      titulo:this.creation.value.titulo,
      descripcion:this.creation.value.descripcion,
      objetivo:this.creation.value.objetivo,
      experiencia:this.creation.value.experiencia,
      fechaInicioVigencia:this.datePipe.transform(this.creation.value.fechaInicioVigencia, 'dd/MM/yyyy'),
      fechaFinVigencia:this.datePipe.transform(this.creation.value.fechaFinVigencia, 'dd/MM/yyyy'),
    }
    this.convocatoriasService.insConvocatoria(convocatoria).subscribe(
      data => {
        if (data.result == 'OK') {
          this.id = data.id;
          this.validaCreate = true;
          swal("Convocaria Guardada", '', "success")
        } else {
          swal("Error", data.msj, "error")
        }
      }
    )
  }

  actualizarConvocatoria(){
    if (this.validaCreate) {
      let convocatoria = {
        titulo:this.creation.value.titulo,
        descripcion:this.creation.value.descripcion,
        objetivo:this.creation.value.objetivo,
        experiencia:this.creation.value.experiencia,
        fechaInicioVigencia:this.datePipe.transform(this.creation.value.fechaInicioVigencia, 'dd/MM/yyyy'),
        fechaFinVigencia:this.datePipe.transform(this.creation.value.fechaFinVigencia, 'dd/MM/yyyy'),
      }
      this.convocatoriasService.modConvocatoria(convocatoria, this.id).subscribe(
        data => {
          if (data.result == 'OK') {
            swal("Convocaria Actualizada", '', "success")
          } else {
            swal("Error", data.msj, "error")
          }
        }
      )
    } 
    swal("Error", 'No se a creado ninguna convocatoria', "warning");
  }

  borrarConvocatoria(){
    this.convocatoriasService.borConvocatoria(this.id).subscribe(
      data => {
        if (data.result == 'OK') {
          this.clean()
        } else {
          swal("Error", data.msj, "error")
        }
      }
    )
  }

  clearDetalleForm(){
    this.dtConv = this.fb.group({
      floatLabelC: this.convFloatLabelCtrl,
      tipoDetalle: ['',Validators.required],
      descripcion: ['',Validators.required],
    });
  }

  crearDetalleConvocatoria(){
    if (!this.validaCreate) {
      swal("Error", 'No se a creado ninguna convocatoria', "warning");
      return;
    }

    const dtC: any = {};
    const tmp: any = {};
    const conv_id = this.tmp_id;
    let key = Detalle[this.dtConv.value.tipoDetalle];
    dtC[key[0]] = this.dtConv.value.descripcion;


    if(this.tmp_id != ''){
      this.convocatoriasService.modDetalleConv(dtC, key[1], this.tmp_id).subscribe(
        data => {
          if (data.result == 'OK') {
            console.log(this.dataSource.data)
            console.log(conv_id)
            this.dataSource.data = this.dataSource.data.filter(function (item) {
              return item.ID_DETALLE !== conv_id
            })
            console.log(this.dataSource.data)
            tmp['KEY'] = this.dtConv.value.tipoDetalle;
            tmp['TITULO'] = key[2];
            tmp['ID_CONVOCATORIA'] = this.id.toString();
            tmp['ID_DETALLE'] = data.id.toString();
            tmp['DATA'] = this.dtConv.value.descripcion;
            this.dataSource.data = [tmp, ...this.dataSource.data];
            this.clearDetalleForm()
            this.tmp_id = ''
          } else {
            swal("Error", data.msj, "error");
          }
        }
      )
    } else {
      this.convocatoriasService.insDetalleConv(dtC, key[1], this.id).subscribe(
        data => {
          if (data.result == 'OK') {
            tmp['KEY'] = this.dtConv.value.tipoDetalle;
            tmp['TITULO'] = key[2];
            tmp['ID_CONVOCATORIA'] = this.id.toString();
            tmp['ID_DETALLE'] = data.id.toString();
            tmp['DATA'] = this.dtConv.value.descripcion;
            this.dataSource.data = [tmp, ...this.dataSource.data];
            this.clearDetalleForm()
          } else {
            swal("Error", data.msj, "error");
          }
        }
      );
    }
  }

  borrar(row){
    let key = Detalle[row.key];
    this.convocatoriasService.borDetalleConv(key[1], row.ID_DETALLE).subscribe(
      data => {
        if (data.result == 'OK') {
          this.dataSource.data = this.dataSource.data.filter(function (item) {
            return item.ID_DETALLE !== row.ID_DETALLE
          })
          this.dataSource.data = [...this.dataSource.data]
        } else {
          swal("Error", data.msj, "error");
        }
      }
    )


  }

  editar(row){
    console.log(row)
    this.tmp_id = row.ID_DETALLE;
    this.dtConv.setValue({
      floatLabelC: this.convFloatLabelCtrl,
      tipoDetalle: parseInt(row.KEY),
      descripcion: row.DATA,
    })
  }

  clean(){
    this.id = NaN;
    this.validaCreate = false;
    this.tmp_id = '';
    this.dataSource.data = [...[]]
    localStorage.removeItem('convocatoria');
    this.creation = this.fb.group({
      floatLabel: this.floatLabelControl,
      titulo: ['', Validators.required],      
      descripcion: ['', Validators.required],      
      objetivo: ['', Validators.required],      
      experiencia: ['', Validators.required],      
      cortoDep: ['', Validators.required],      
      gafeteDep: ['', Validators.required],      
      documentoDep: ['', Validators.required],      
      selConector: ['', Validators.required],      
      selFuncionalidad: ['', Validators.required],      
      selArea: ['', Validators.required],      
      fechaInicioVigencia: ['', Validators.required],      
      inicioVigencia: ['', Validators.required],      
      selDepartamento: ['', Validators.required],      
      selMunicipio: ['', Validators.required],      
      referencia: ['', Validators.required],
      fechaFinVigencia: ['', Validators.required],      
      inicioVigRef: [''],
      chkRefVigencia:['']      
    });

    this.dtConv = this.fb.group({
      floatLabelC: this.convFloatLabelCtrl,
      tipoDetalle: ['',Validators.required],
      descripcion: ['',Validators.required],
    });

    this.dependencia = this.fb.group({
      floatLabel: this.floatLabelControl,
      busqueda: [''],      
      selEstados: ['']      
    });
  }
}


