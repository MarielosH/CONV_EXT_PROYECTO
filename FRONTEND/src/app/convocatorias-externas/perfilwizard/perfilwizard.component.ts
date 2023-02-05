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
import { Sexo, EstadoCivil, TiposLicencia, Discapacidad, Raza , Idioma} from 'app/constantes';
@Component({
  selector: 'app-perfilwizard',
  templateUrl: './perfilwizard.component.html',
  styleUrls: ['./perfilwizard.component.scss']
})
export class PerfilwizardComponent implements OnInit {
  cantidiomas = 0;
  generos = [];
  estadoCivil = [];
  tiposLicencias = [];
  discapacidades = [];
  etnias = [];
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
  listaComunidadLinguistica;
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
  discapacidad;
  idioma1;
  idioma2;
  idioma3;
  idioma4;
  padre;
  fechaNacPadre;
  telPadre;
  vivePadre;
  profesionPadre;
  trabajaPadre;
  lugarTrabajoPadre;
  entnia;
  comunidadLinguistica;
  isLinear = true;
  etniasPerfil = [];
  comunidadesLinguisticas = [];
  idiomas: Idioma[] = [];
  listaDependientes = [];
  listarazaPerfil;
  listaComunidadesLinguisticas;
  madre;
  fechaNacmadre;
  telmadre;
  vivemadre;
  profesionmadre;
  lugarTrabajomadre;
  trabajamadre;
  hermano1;
  fechaNachermano1;
  telhermano1;
  vivehermano1;
  profesionhermano1;
  lugarTrabajohermano1;
  trabajahermano1;
  hermano2;
  fechaNachermano2;
  telhermano2;
  vivehermano2;
  profesionhermano2;
  lugarTrabajohermano2;
  trabajahermano2;
  hermano3;
  fechaNachermano3;
  telhermano3;
  vivehermano3;
  profesionhermano3;
  lugarTrabajohermano3;
  trabajahermano3;
  conyuge;
  fechaNacconyuge;
  telconyuge;
  viveconyuge;
  profesionconyuge;
  lugarTrabajoconyuge;
  trabajaconyuge;
  hijos;
  noHijo;
  dependientes;
  parentesco;
  nombreFamiliar;
  dependencia;
  puesto;
  parentesco1;
  nombreFamiliar1;
  dependencia1;
  puesto1;
  parentesco2;
  nombreFamiliar2;
  dependencia2;
  puesto2;
  gradoAprobado;
  institucionEstudios;
  constancia;
  institucionEstudios1;
  constancia1;
  gradoAprobado1;
  institucionEstudios2;
  constancia2;
  gradoAprobado2;
  anioGraduacion;
  carrera;
  carreraU;
  universidad;
  constancia3;
  semestreA;
  cierre;
  gradoTecnico;
  gradoLicenciatura;
  colegiado;
  vigenciaColegiado;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  fourthFormGroup: FormGroup;
  fifthFormGroup: FormGroup;
  sixthFormGroup: FormGroup;
  seventhFormGroup: FormGroup;
  eighthFormGroup: FormGroup;
  nineFormGroup: FormGroup;

  constructor(private convocatoriasService: ConvocatoriasExternasService, public authService: AuthService,
    public HttpClient: HttpClient, private fb: FormBuilder, private _location: Location, private datePipe: DatePipe,
    private router: Router, private route: ActivatedRoute
  ) {
    this.session = this.authService.getsession().SESSION;
  }

  ngOnInit() {
    this.generos = Sexo;
    this.estadoCivil = EstadoCivil;
    this.tiposLicencias = TiposLicencia;
    this.discapacidades = Discapacidad;
    this.etnias = Raza;
    this.firstFormGroup = this.fb.group({
      nombres: [localStorage.getItem('nombres'), Validators.required],
      primerApellido: [localStorage.getItem('primerApellido'), Validators.required],
      segundoApellido: [localStorage.getItem('segundoApellido'), Validators.required],
      fechaNac: ['', Validators.required],
      edad: ['', Validators.required],
      sexo: ['', Validators.required],
      estadoCivilAspirante: ['', Validators.required],
      nacionalidad: ['', Validators.required],
      profesion: ['', Validators.required],
      direccion: ['', Validators.required],
      selDepartamento: ['', Validators.required],
      selMunicipio: ['', Validators.required],
      correo: [localStorage.getItem('correo'), Validators.required],
      telefonoCasa: ['', Validators.required],
      telefonoCelular: ['', Validators.required],
      dpi: [localStorage.getItem('cui'), Validators.required],
      fechaVencDPI: ['', Validators.required],
      nit: ['', Validators.required],
      nombreClase: ['', Validators.required],
      numeroLicencia: [''],
      discapacidad: ['', Validators.required]
    });
    this.secondFormGroup = this.fb.group({    
      etnia: ['', Validators.required],
      comunidadLinguistica: ['',Validators.required],
      idioma1: '',
      idiomas: [[]]
    });

    this.thirdFormGroup = this.fb.group({
      listarazaPerfil: ''
    });

    this.fourthFormGroup = this.fb.group({
      listaComunidadesLinguisticas: ''
    });

    this.fifthFormGroup = this.fb.group({
      idioma1: '',
      idioma2: '',
      idioma3: '',
      idioma4: ''
    });

    this.sixthFormGroup = this.fb.group({
      padre: [''],
      fechaNacPadre: [''],
      telPadre: [''],
      vivePadre: [''],
      profesionPadre: [''],
      lugarTrabajoPadre: [''],
      trabajaPadre: [''],
      madre: [''],
      fechaNacmadre: [''],
      telmadre: [''],
      vivemadre: [''],
      profesionmadre: [''],
      lugarTrabajomadre: [''],
      trabajamadre: [''],
      hermano1: [''],
      fechaNachermano1: [''],
      telhermano1: [''],
      vivehermano1: [''],
      profesionhermano1: [''],
      lugarTrabajohermano1: [''],
      trabajahermano1: [''],
      hermano2: [''],
      fechaNachermano2: [''],
      telhermano2: [''],
      vivehermano2: [''],
      profesionhermano2: [''],
      lugarTrabajohermano2: [''],
      trabajahermano2: [''],
      hermano3: [''],
      fechaNachermano3: [''],
      telhermano3: [''],
      vivehermano3: [''],
      profesionhermano3: [''],
      lugarTrabajohermano3: [''],
      trabajahermano3: [''],
      conyuge: [''],
      fechaNacconyuge: [''],
      telconyuge: [''],
      viveconyuge: [''],
      profesionconyuge: [''],
      lugarTrabajoconyuge: [''],
      trabajaconyuge: ['']

    });

    this.seventhFormGroup = this.fb.group({
      hijos: [''],
      noHijo: [''],
      dependientes: ['']
    });
    this.eighthFormGroup = this.fb.group({
      parentesco: [''],
      nombreFamiliar: [''],
      dependencia: [''],
      puesto: [''],
      parentesco1: [''],
      nombreFamiliar1: [''],
      dependencia1: [''],
      puesto1: [''],
      parentesco2: [''],
      nombreFamiliar2: [''],
      dependencia2: [''],
      puesto2: ['']
    });

    this.nineFormGroup = this.fb.group({
      gradoAprobado: [''],
      institucionEstudios: [''],
      constancia: [''],
      institucionEstudios1: [''],
      constancia1: [''],
      gradoAprobado1: [''],
      institucionEstudios2: [''],
      constancia2: [''],
      gradoAprobado2: [''],
      anioGraduacion: [''],
      carrera: [''],
      carreraU: [''],
      universidad: [''],
      constancia3: [''],
      semestreA: [''],
      cierre: [''],
      gradoTecnico: [''],
      gradoLicenciatura: [''],
      colegiado: [''],
      vigenciaColegiado: ['']
    });
    this.obtenerDepartamentos();
    this.obtenerComunidadesLinguisticas();
  }



  obtenerDepartamentos() {
    this.convocatoriasService
      .getListaDepartamentosConv()
      .subscribe(
        data => {
          this.listaDepartamentos = data;
        });
  }

  obtenerComunidadesLinguisticas() {
    this.convocatoriasService
      .getListaComunidadLinguisticaConv()
      .subscribe(
        data => {
          this.listaComunidadLinguistica = data;
        });
  }
  cambioListaEtnias(e) {
    //console.log(e);
    if (e.option.selected === true) {
      this.etniasPerfil.push(e.option.value);
    } else {
      let index = this.etniasPerfil.indexOf(e.option.value);
      this.etniasPerfil.splice(index, 1);
    }
    console.log(this.etniasPerfil);
    //console.log(this.listarazaPerfil);
  }

  agregarIdioma(){
    console.log("se agrega un idioma"); 
    this.idiomas.push({
      id_idioma : 0,
      idioma: "",
      habla: "",
      lee: "",
      escribe: ""
    });
  }
  cambioListaComunidadesLinguistica(e) {
    // console.log(e);
    if (e.option.selected === true) {
      this.comunidadesLinguisticas.push(e.option.value);
    } else {
      let index = this.comunidadesLinguisticas.indexOf(e.option.value);
      this.comunidadesLinguisticas.splice(index, 1);
    }
    console.log(this.comunidadesLinguisticas);
    //console.log(this.listaComunidadesLinguisticas);
  }

  crearPerfil(){
    this.obtenerDepartamentos();
    this.obtenerComunidadesLinguisticas();
   // swal("Perfil Solicitud Empleo", "Cambios Guardados", "success")
  }
  cambioListaDependientes(e) {
    // console.log(e);
    if (e.option.selected === true) {
      this.listaDependientes.push(e.option.value);
    } else {
      let index = this.listaDependientes.indexOf(e.option.value);
      this.listaDependientes.splice(index, 1);
    }
    console.log(this.listaDependientes);
    //console.log(this.listaComunidadesLinguisticas);

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
