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
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ConvocatoriaService } from '../convocatoria.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap'
import Detalle from '../detallesConv'
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
  selectedFiles: File[] = [];

  creation: FormGroup;
  dtConv: FormGroup;
  dependencia: FormGroup;
  aplicante: FormGroup;
  //hideRequiredControl = new FormControl(false);
  floatLabelControl = new FormControl('none');
  convFloatLabelCtrl = new FormControl('none');
  aplicanteFL = new FormControl('none');
  departamentos;
  listArea;
  municipios;
  ngf: ngf;
  validaCodDep = false;
  validaCodPres = false;
  validaCreate = false;
  valDepto;
  session;
  iniciaValDep = false;
  iniciaValPres = false;
  validaFecha = false;
  validaFechaFinVigencia = false;
  checked = false;
  id;
  convDetalles;
  dataSource = new MatTableDataSource<any>();
  tmp_id = '';
  fileNames = '';
  dpi = '';

  aspirante = false;
  coordinador = false;
  jefeV = false;

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
    public HttpClient: HttpClient, private fb: FormBuilder, private _location: Location, private datePipe: DatePipe,
    private router: Router, private route: ActivatedRoute, public dialog: MatDialog,
    private convocatoriasService: ConvocatoriaService, private changeDetectorRef: ChangeDetectorRef,
  ) {
    this.session = this.authService.getsession().SESSION;
  }

  ngOnInit() {
    this.dpi = localStorage.getItem('cui');
    this.localStorageRol()
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
      tipoDetalle: ['', Validators.required],
      descripcion: ['', Validators.required],
    });

    this.dependencia = this.fb.group({
      floatLabel: this.floatLabelControl,
      busqueda: [''],
      selEstados: ['']
    });
    this.convDetalles = []
    this.loadDetalles();

    this.aplicante = this.fb.group({
      floatlabelA: this.aplicanteFL,
      dpi: [0, Validators.required],
      fileInput: [File, Validators.required],
    })

    if (conv) {
      this.creation.setValue({
        floatLabel: this.floatLabelControl,
        titulo: conv.TITULO,
        descripcion: conv.DESCRIPCION,
        objetivo: conv.OBJETIVO,
        experiencia: conv.EXPERIENCIA,
        fechaInicioVigencia: this.datePipe.transform(conv.FECHA_INICIO_VIGENCIA, 'yyyy-MM-dd'),
        fechaFinVigencia: this.datePipe.transform(conv.FECHA_FIN_VIGENCIA, 'yyyy-MM-dd'),
      })
      this.id = parseInt(conv.ID_CONVOCATORIA)
      this.validaCreate = !this.validaCreate;
      // recolectar todas los detalles de la conv
      this.cargarDetalles(parseInt(conv.ID_CONVOCATORIA))
    }

  }

  public localStorageRol() {
    const rol = parseInt(localStorage.getItem('rol'))
    switch(rol){
      case 1:
        this.aspirante = true;
        this.coordinador = this.jefeV = !this.aspirante;
      break;
      case 2:
        this.coordinador = true;
        this.aspirante = this.jefeV = !this.coordinador;
        break;
      case 3:
        this.jefeV = true;
        this.coordinador = this.aspirante = !this.jefeV;
        break
    }
  }

  cargarDetalles(id) {
    for (const key in Detalle) {
      this.convocatoriasService.getDetalleConv(Detalle[key][1], id).subscribe(
        data => {
          if (data.length > 0) {
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

  onFileSelected(event: any) {
    const files: FileList = event.target.files;
    for (let i = 0; i < files.length; i++) {
      this.selectedFiles.push(files[i]);
      this.fileNames = `${this.fileNames}${this.dpi}/${files[i].name},`
    }
    console.log(this.selectedFiles);
    console.log(this.fileNames);
  }

  loadDetalles() {
    this.dataSource = new MatTableDataSource(this.convDetalles);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  filterMatTable(filterValue: any) {
    this.dataSource.filter = filterValue;
  }

  asignarRequerido(valor: any) {
    if (this.checked) {
      this.creation.controls.inicioVigRef.setValidators(Validators.required);
      this.creation.controls.inicioVigencia.setValidators([]);
      this.creation.controls.inicioVigencia.updateValueAndValidity();
    } else {
      this.creation.controls.inicioVigencia.setValidators(Validators.required);
      this.creation.controls.inicioVigRef.setValidators([]);
      this.creation.controls.inicioVigRef.updateValueAndValidity();
    }
  }

  get f() { return this.creation.controls; }

  parseDate(dateString: any): any {
    if (dateString) {
      if (this.valDates()) {
        swal("Fecha Inválida", "Fecha Inicio de Vigencia no puede ser anterior a Fecha Fin Vigencia", "info")
      }
      return this.datePipe.transform(dateString, 'yyyy-MM-dd')
    } else {
      return null;
    }
  }

  parseDatePublicacion(dateString: any): any {
    if (dateString) {
      if (this.valDatesPublicacion()) {
        swal("Fecha Inválida", "Fecha Fin Vigencia no puede ser anterior a Fecha Inicio Vigencia", "info")
      }
      return this.datePipe.transform(dateString, 'yyyy-MM-dd')
    } else {
      return null;
    }
  }


  valDates() {
    let acuerdo = new Date(this.creation.value.fechaInicioVigencia);
    let vigencia = new Date(this.creation.value.inicioVigencia);

    this.validaFecha = false;

    if (acuerdo.getFullYear() > vigencia.getFullYear()) {
      this.validaFecha = true;
    } else if (acuerdo.getMonth() == vigencia.getMonth() && acuerdo.getFullYear() == vigencia.getFullYear()) {
      this.validaFecha = acuerdo.getDate() > vigencia.getDate();
    } else if (acuerdo.getMonth() > vigencia.getMonth() && acuerdo.getFullYear() == vigencia.getFullYear()) {
      this.validaFecha = true;
    }

    return this.validaFecha;
  }

  valDatesPublicacion() {
    let acuerdo = new Date(this.creation.value.fechaInicioVigencia);
    let publicacion = new Date(this.creation.value.fechaFinVigencia);

    this.validaFechaFinVigencia = false;

    if (acuerdo.getFullYear() > publicacion.getFullYear()) {
      this.validaFechaFinVigencia = true;
    } else if (acuerdo.getMonth() == publicacion.getMonth() && acuerdo.getFullYear() == publicacion.getFullYear()) {
      this.validaFechaFinVigencia = acuerdo.getDate() > publicacion.getDate();
    } else if (acuerdo.getMonth() > publicacion.getMonth() && acuerdo.getFullYear() == publicacion.getFullYear()) {
      this.validaFechaFinVigencia = true;
    }

    return this.validaFechaFinVigencia;
  }

  crearConvocatoria() {
    if (this.creation.value.titulo != '' && this.creation.value.descripcion != '' && this.creation.value.objetivo != '' && this.creation.value.experiencia != '' && this.creation.value.fechaInicioVigencia != '' && this.creation.value.fechaFinVigencia != '') {
      let convocatoria = {
        titulo: this.creation.value.titulo,
        descripcion: this.creation.value.descripcion,
        objetivo: this.creation.value.objetivo,
        experiencia: this.creation.value.experiencia,
        fechaInicioVigencia: this.datePipe.transform(this.creation.value.fechaInicioVigencia, 'yyyy-MM-dd'),
        fechaFinVigencia: this.datePipe.transform(this.creation.value.fechaFinVigencia, 'yyyy-MM-dd'),
      }
      this.convocatoriasService.insConvocatoria(convocatoria).subscribe(
        data => {
          if (data.result == 'OK') {
            this.id = data.id;
            this.validaCreate = true;
            swal("Convocaria Guardada", '', "success");
            return;
          } else {
            swal("Error", data.msj, "error");
            return;
          }
        }
      )
    } else {
      swal("Error", "Debe ingresar los campos de la convocatoria", "error");
    }


  }

  aplicarConvocatoria() {
    if (this.selectedFiles.length == 0) {
      swal("Error", "Debes adjuntar la papeleria requerida para poder aplicar", "error");
    } else {
      this.selectedFiles.forEach(element => {
        this.convocatoriasService.uploadDocument(element, this.dpi).subscribe(data => {})
      });
      const body = {documentos: this.fileNames, estado: 'E'}
      this.convocatoriasService.applyConv(localStorage.getItem('informacion_personal_id'),this.id, body).subscribe(data => {
        if (data.id >= 1) {
          swal("Su aplicación a la convocatoria a sido guardada", '', "success");
        }
      })
    }
  }

  actualizarConvocatoria() {
    if (this.validaCreate) {
      let convocatoria = {
        titulo: this.creation.value.titulo,
        descripcion: this.creation.value.descripcion,
        objetivo: this.creation.value.objetivo,
        experiencia: this.creation.value.experiencia,
        fechaInicioVigencia: this.datePipe.transform(this.creation.value.fechaInicioVigencia, 'yyyy-MM-dd'),
        fechaFinVigencia: this.datePipe.transform(this.creation.value.fechaFinVigencia, 'yyyy-MM-dd'),
      }
      this.convocatoriasService.modConvocatoria(convocatoria, this.id).subscribe(
        data => {
          if (data.result == 'OK') {
            swal("Convocaria Actualizada", '', "success");
            return;
          } else {
            swal("Error", data.msj, "error");
            return;
          }
        }
      )
    }
    // swal("Error", 'No se ha creado ninguna convocatoria', "warning");
  }

  borrarConvocatoria() {
    this.convocatoriasService.borConvocatoria(this.id).subscribe(
      data => {
        if (data.result == 'OK') {
          this.clean();
          return;
        } else {
          swal("Error", data.msj, "error");
          return;
        }
      }
    )
  }

  clearDetalleForm() {
    this.dtConv = this.fb.group({
      floatLabelC: this.convFloatLabelCtrl,
      tipoDetalle: ['', Validators.required],
      descripcion: ['', Validators.required],
    });
  }

  crearDetalleConvocatoria() {
    if (!this.validaCreate && (this.id == null || this.id <= 0)) {
      swal("Error", 'No se ha creado ninguna convocatoria', "warning");
      return;
    }

    const dtC: any = {};
    const tmp: any = {};
    const conv_id = this.tmp_id;
    let key = Detalle[this.dtConv.value.tipoDetalle];
    dtC[key[0]] = this.dtConv.value.descripcion;


    if (this.tmp_id != '') {
      this.convocatoriasService.modDetalleConv(dtC, key[1], this.tmp_id).subscribe(
        data => {
          if (data.result == 'OK') {
            this.dataSource.data = this.dataSource.data.filter(function (item) {
              return item.ID_DETALLE !== conv_id
            })
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

  borrar(row) {
    let key = Detalle[row.KEY];
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

  editar(row) {
    this.tmp_id = row.ID_DETALLE;
    this.dtConv.setValue({
      floatLabelC: this.convFloatLabelCtrl,
      tipoDetalle: parseInt(row.KEY),
      descripcion: row.DATA,
    })
  }

  clean() {
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
      chkRefVigencia: ['']
    });

    this.dtConv = this.fb.group({
      floatLabelC: this.convFloatLabelCtrl,
      tipoDetalle: ['', Validators.required],
      descripcion: ['', Validators.required],
    });

    this.dependencia = this.fb.group({
      floatLabel: this.floatLabelControl,
      busqueda: [''],
      selEstados: ['']
    });
  }

  cancelar() {
    this.clean()
    this.router.navigate(['/convocatorias-externas/gestion-convocatorias']);
  }

}