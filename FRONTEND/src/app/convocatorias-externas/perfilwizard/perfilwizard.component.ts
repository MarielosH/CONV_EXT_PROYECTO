import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Observable, Subscription, BehaviorSubject } from '../../../../node_modules/rxjs';
import { HttpEvent, HttpRequest, HttpClient, HttpResponse } from '../../../../node_modules/@angular/common/http';
import { ngf } from "angular-file"
import { DatePipe, Location } from '@angular/common';
import swal from 'sweetalert2';
import { AuthService } from '../../recursos/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ConvocatoriasExternasService } from '../convocatorias-externas.service';
import { Sexo, EstadoCivil} from 'app/constantes';
@Component({
  selector: 'app-perfilwizard',
  templateUrl: './perfilwizard.component.html',
  styleUrls: ['./perfilwizard.component.scss']
})
export class PerfilwizardComponent implements OnInit {
  generos = [];
  estadoCivil = []; 
  floatLabelControl = new FormControl('none');
  floatLabelControl2 = new FormControl('none');
  title = 'newMat';
  validaDpi = false;
  validaCodDep = false;
  validaCodPres = false;
  valDepto;
  session;
  iniciaValDep = false;
  iniciaValPres = false;
  validaFecha = false;
  validaFechaPublica = false;
  checked = false;
  listaDepartamentos;
  municipios;
  selMunicipio;
  selDepartamento;
  /* Controles */
  dpi;
  nombres;
  primerApellido;
  segundoApellido;
  fechaNac;
  edad;
  sexo;
  estadoCivilAspirante;
  profesion;
  nacionalidad;
  direccion;
  correo;
  telefonoCasa;
  telefonoCelular;
  fechaVencDPI;
  nit;
  nombreClase;
  numeroLicencia;
  idioma1;
  idioma2;
  idioma3;
  idioma4;
  padre;
  fechaNacPadre;
  telPadre;
  vivePadre;
  profesionPadre;
  lugarTrabajoPadre;
  isLinear = true;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;

  constructor(private convocatoriasService: ConvocatoriasExternasService, public authService: AuthService,
    public HttpClient: HttpClient, private fb: FormBuilder, private _location: Location, private datePipe: DatePipe,
    private router: Router, private route: ActivatedRoute
  ) {
    this.session = this.authService.getsession().SESSION;
  }

  ngOnInit() {
    this.generos = Sexo;
    this.estadoCivil = EstadoCivil;
    this.firstFormGroup = this.fb.group({
      nombres: ['', Validators.required],
      primerApellido: ['', Validators.required],
      segundoApellido: ['', Validators.required],
      fechaNac: ['', Validators.required],
      edad: ['', Validators.required],
      sexo: ['', Validators.required],
      estadoCivilAspirante: ['', Validators.required],
      nacionalidad: ['', Validators.required],
      profesion: ['', Validators.required],
      direccion: ['', Validators.required], 
      selDepartamento: ['', Validators.required], 
      selMunicipio: ['', Validators.required] 
    });
    this.secondFormGroup = this.fb.group({
      amount: ['', Validators.required],
      stock: ['', Validators.required]
    });

    this.convocatoriasService
    .getListaDepartamentosConv()
    .subscribe(
      data => {
        this.listaDepartamentos = data;
      });
  }

  getMunicipio(event: any, valDepto: number) {
    if (event.isUserInput) {
      this.convocatoriasService.getListaMunicipioConv(valDepto).subscribe(
        data => {
          this.municipios = data;
        });
    }
  }

  parseDate(dateString: any): any {
    if (dateString) {
      if (this.valDates()) {
        swal("Fecha Inválida", "Fecha Inicio de Vigencia no puede ser anterior a Fecha de Acuerdo", "info")
      }
      return this.datePipe.transform(dateString, 'yyyy-MM-dd')
    } else {
      return null;
    }
  }

  valDates() {
    let acuerdo = new Date(this.firstFormGroup.value.fechaNac);
    let vigencia = new Date(this.firstFormGroup.value.fechaNacPadre);

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

  get f() { return this.firstFormGroup.controls; }

  valDpi(valor: any): boolean {
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
    return true;
  }
  valCodPres(valor: any): boolean {
    /*
    if(valor !== null && valor !== undefined &&  valor !== "" && this.iniciaValPres){
    this.mantenimientoDependenciaService.validaCodigosCreacion(valor,2).subscribe(
      data => {
        this.validaCodPres = data.id !== 0;       
      });
    }else{
      this.validaCodPres = false;
    }*/

    return this.validaCodPres;
  }

  submit() {
    console.log(this.firstFormGroup.value);
    console.log(this.secondFormGroup.value);
  }


}
