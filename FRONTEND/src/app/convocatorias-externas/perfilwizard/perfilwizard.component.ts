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
import { Sexo, EstadoCivil, TiposLicencia, Discapacidad, Raza, Idioma, Familiar, FamiliarLaborandoOJ, PasantiaOJ, ExperienciaLaboralOJ, ExperienciaLaboral, ReferenciaPersonal } from 'app/constantes';
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
  habla1;
  lee1;
  escribe1;
  entnia;
  comunidadLinguistica;
  padre;
  fechaNacPadre;
  telPadre;
  vivePadre;
  profesionPadre;
  trabajaPadre;
  lugarTrabajoPadre;
  parentezco;
  familiar;
  fechaNacFamiliar;
  telFamiliar;
  viveFamiliar;
  profesionFamiliar;
  trabajaFamiliar;
  lugarTrabajoFamiliar;
  isLinear = true;
  idiomas: Idioma[] = [];
  nuevoIdioma: Idioma;
  familiares: Familiar[] = [];
  nuevoFamiliar: Familiar;
  familiaresLaborandoOJ: FamiliarLaborandoOJ[] = [];
  nuevoFamiliarLaborandoOJ: FamiliarLaborandoOJ;
  pasantiasOJ: PasantiaOJ[] = [];
  nuevaPasantiaOJ: PasantiaOJ;
  experienciaLaboralOJ: ExperienciaLaboralOJ[] = [];
  nuevaExperienciaLaboralOJ: ExperienciaLaboralOJ;
  experienciaLaboral: ExperienciaLaboral[] = [];
  nuevaExperienciaLaboral: ExperienciaLaboral;
  referenciasPersonales: ReferenciaPersonal[] = [];
  nuevaReferenciaPersonal: ReferenciaPersonal;
  dependenciaPasantia;
  inicioPasantia;
  finPasantia;
  secrectarioJuezPasantia;
  listaDependientes = [];
  listarazaPerfil;
  listaComunidadesLinguisticas;
  hijos;
  noHijo;
  dependientes;
  parentesco;
  nombreFamiliar;
  dependencia;
  puesto;
  parentescoFOJ;
  nombreFOJ;
  dependenciaFOJ;
  puestoFOJ;
  nivelAcademicoPrimaria;
  nivelAcademicoBasicos;
  nivelAcademicoDiversificado;
  gradoAprobadoPrimaria;
  institucionEstudiosPrimaria;
  constanciaPrimaria;
  gradoAprobadoBasicos;
  institucionEstudiosBasicos;
  constanciaBasicos;
  gradoAprobadoDiversificado;
  institucionEstudiosDiversificado;
  constanciaDiversificado;
  anioGraduacionDiversificado;
  carreraDiversificado;
  carreraU;
  universidad;
  constanciaUniversidad;
  constancia3;
  semestreA;
  cierre;
  graduadoTecnico;
  gradoTecnico;
  gradoLicenciatura;
  colegiado;
  vigenciaColegiado;
  carreraPosgrado;
  universidadPosgrado;
  constanciaUniversidadPosgrado;
  semestreAprobadoPosgrado;
  cierrePensumPosgrado;
  graduadoMaestria;
  graduadoDoctorado;
  dependenciaExperienciaOJ;
  fechaInicioExperienciaOJ;
  fechaFinalizacionExperienciaOJ;
  jefeInmediatoExperienciaOJ;
  puestoExperienciaOJ;
  renglonPresupuestarioExperienciaOJ;
  motivoFinRelacionLaboralExperienciaOJ;
  institucionEmpresaExperienciaLaboral;
  fechaInicioExperienciaLaboral;
  fechaFinalizacionExperienciaLaboral;
  renglonPresupuestarioExperienciaLaboral;
  puestoExperienciaLaboral;
  jefeInmediatoExperienciaLaboral;
  telefonoExperienciaLaboral;
  motivoFinRelacionLaboralExperienciaLaboral;
  nombreReferenciaPersonal;
  tipoRelacionReferenciaPersonal;
  aniosConocerloReferenciaPersonal;
  telefonoReferenciaPersonal;
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
      comunidadLinguistica: ['', Validators.required],
      idioma1: [''],
      habla1: [''],
      lee1: [''],
      escribe1: ['']
    });

    this.thirdFormGroup = this.fb.group({
      hijos: [''],
      noHijo: [''],
      dependientes: [''],
      parentezco: [''],
      familiar: [''],
      fechaNacFamiliar: [''],
      telFamiliar: [''],
      viveFamiliar: [''],
      profesionFamiliar: [''],
      trabajaFamiliar: [''],
      lugarTrabajoFamiliar: ['']
    });

    this.fourthFormGroup = this.fb.group({
      parentescoFOJ: [''],
      nombreFOJ: [''],
      dependenciaFOJ: [''],
      puestoFOJ: ['']
    });

    this.fifthFormGroup = this.fb.group({
      nivelAcademicoPrimaria: [''],
      nivelAcademicoBasicos: [''],
      nivelAcademicoDiversificado: [''],
      gradoAprobadoPrimaria: [''],
      institucionEstudiosPrimaria: [''],
      constanciaPrimaria: [''],
      gradoAprobadoBasicos: [''],
      institucionEstudiosBasicos: [''],
      constanciaBasicos: [''],
      gradoAprobadoDiversificado: [''],
      institucionEstudiosDiversificado: [''],
      constanciaDiversificado: [''],
      anioGraduacionDiversificado: [''],
      carreraDiversificado: [''],
      carreraU: [''],
      universidad: [''],
      constancia3: [''],
      constanciaUniversidad: [''],
      semestreA: [''],
      cierre: [''],
      gradoTecnico: [''],
      graduadoTecnico: [''],
      gradoLicenciatura: [''],
      colegiado: [''],
      vigenciaColegiado: [''],
      carreraPosgrado: [''],
      universidadPosgrado: [''],
      constanciaUniversidadPosgrado: [''],
      semestreAprobadoPosgrado: [''],
      cierrePensumPosgrado: [''],
      graduadoMaestria: [''],
      graduadoDoctorado: ['']
    });

    this.sixthFormGroup = this.fb.group({
      dependenciaPasantia: [''],
      inicioPasantia: [''],
      finPasantia: [''],
      secrectarioJuezPasantia: ['']
    });

    this.seventhFormGroup = this.fb.group({
      dependenciaExperienciaOJ: [''],
      fechaInicioExperienciaOJ: [''],
      fechaFinalizacionExperienciaOJ: [''],
      jefeInmediatoExperienciaOJ: [''],
      puestoExperienciaOJ: [''],
      renglonPresupuestarioExperienciaOJ: [''],
      motivoFinRelacionLaboralExperienciaOJ: ['']
    });

    this.eighthFormGroup = this.fb.group({
      institucionEmpresaExperienciaLaboral: [''],
      fechaInicioExperienciaLaboral: [''],
      fechaFinalizacionExperienciaLaboral: [''],
      renglonPresupuestarioExperienciaLaboral: [''],
      puestoExperienciaLaboral: [''],
      jefeInmediatoExperienciaLaboral: [''],
      telefonoExperienciaLaboral: [''],
      motivoFinRelacionLaboralExperienciaLaboral: ['']
    });

    this.nineFormGroup = this.fb.group({
      nombreReferenciaPersonal: [''],
      tipoRelacionReferenciaPersonal: [''],
      aniosConocerloReferenciaPersonal: [''],
      telefonoReferenciaPersonal: ['']
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

  agregarIdioma() {
    if (this.secondFormGroup.value.idioma1 !== '' && this.secondFormGroup.value.idioma1 !== undefined && this.secondFormGroup.value.idioma1 !== null) {
      this.nuevoIdioma = {
        id_idioma: 0,
        idioma: this.secondFormGroup.value.idioma1,
        habla: this.secondFormGroup.value.habla1,
        lee: this.secondFormGroup.value.lee1,
        escribe: this.secondFormGroup.value.escribe1
      };
      this.idiomas.push(this.nuevoIdioma);

      this.secondFormGroup = this.fb.group({
        etnia: [this.secondFormGroup.value.etnia, Validators.required],
        comunidadLinguistica: [this.secondFormGroup.value.comunidadLinguistica, Validators.required],
        idioma1: [''],
        habla1: [''],
        lee1: [''],
        escribe1: ['']
      });
    }

  }

  agregarFamiliar() {
    console.log("Agregar familiar");
    console.log(this.thirdFormGroup.value.familiar);
    if (this.thirdFormGroup.value.familiar !== '' && this.thirdFormGroup.value.familiar !== undefined && this.thirdFormGroup.value.familiar !== null) {
      console.log("es distinto de undefined");

      this.nuevoFamiliar = {
        nombreFamiliar: this.thirdFormGroup.value.familiar,
        parentezco: this.thirdFormGroup.value.parentezco,
        fechaNacimiento: this.thirdFormGroup.value.fechaNacFamiliar,
        telefono: this.thirdFormGroup.value.telFamiliar,
        vive: this.thirdFormGroup.value.viveFamiliar,
        profesion: this.thirdFormGroup.value.profesionFamiliar,
        trabaja: this.thirdFormGroup.value.trabajaFamiliar,
        lugarTrabajo: this.thirdFormGroup.value.lugarTrabajoFamiliar
      };
      console.log(this.nuevoFamiliar);
      this.familiares.push(this.nuevoFamiliar);

      this.thirdFormGroup = this.fb.group({
        parentezco: [''],
        familiar: [''],
        fechaNacFamiliar: [''],
        telFamiliar: [''],
        viveFamiliar: [''],
        profesionFamiliar: [''],
        trabajaFamiliar: [''],
        lugarTrabajoFamiliar: ['']
      });
      console.log(this.familiares);
    }

  }

  agregarFamiliarLaborandoOJ() {
    console.log("Agregar familiar Laborando OJ");
    console.log(this.thirdFormGroup.value.familiar);
    if (this.fourthFormGroup.value.nombreFOJ !== '' && this.fourthFormGroup.value.nombreFOJ !== undefined && this.fourthFormGroup.value.nombreFOJ !== null) {
      console.log("es distinto de undefined");

      this.nuevoFamiliarLaborandoOJ = {
        nombreCompleto: this.fourthFormGroup.value.nombreFOJ,
        parentezco: this.fourthFormGroup.value.parentescoFOJ,
        dependencia: this.fourthFormGroup.value.dependenciaFOJ,
        puesto: this.fourthFormGroup.value.puestoFOJ
      };
      console.log(this.nuevoFamiliarLaborandoOJ);
      this.familiaresLaborandoOJ.push(this.nuevoFamiliarLaborandoOJ);

      this.fourthFormGroup = this.fb.group({
        parentescoFOJ: [''],
        nombreFOJ: [''],
        dependenciaFOJ: [''],
        puestoFOJ: ['']
      });

      console.log(this.familiaresLaborandoOJ);
    }

  }

  agregarPasantia() {
    console.log("Agregar pasantia OJ");
    console.log(this.sixthFormGroup.value.dependenciaPasantia);
    if (this.sixthFormGroup.value.dependenciaPasantia !== '' && this.sixthFormGroup.value.dependenciaPasantia !== undefined && this.sixthFormGroup.value.dependenciaPasantia !== null) {
      console.log("es distinto de undefined");

      this.nuevaPasantiaOJ = {
        dependencia: this.sixthFormGroup.value.dependenciaPasantia,
        fechaInicio: this.sixthFormGroup.value.inicioPasantia,
        fechaFinalizacion: this.sixthFormGroup.value.finPasantia,
        secretarioJuez: this.sixthFormGroup.value.secrectarioJuezPasantia
      };
      console.log(this.nuevaPasantiaOJ);
      this.pasantiasOJ.push(this.nuevaPasantiaOJ);

      this.sixthFormGroup = this.fb.group({
        dependenciaPasantia: [''],
        inicioPasantia: [''],
        finPasantia: [''],
        secrectarioJuezPasantia: ['']
      });

      console.log(this.pasantiasOJ);
    }

  }

  agregarExperienciaLaboralOJ() {
    console.log("Agregar Experiencia laboral OJ");
    console.log(this.seventhFormGroup.value.puestoExperienciaOJ);
    if (this.seventhFormGroup.value.puestoExperienciaOJ !== '' && this.seventhFormGroup.value.puestoExperienciaOJ !== undefined && this.seventhFormGroup.value.puestoExperienciaOJ !== null) {
      console.log("es distinto de undefined");
      this.nuevaExperienciaLaboralOJ = {
        dependencia: this.seventhFormGroup.value.dependenciaExperienciaOJ,
        fechaInicio: this.seventhFormGroup.value.fechaFinalizacionExperienciaOJ,
        fechaFinalizacion: this.seventhFormGroup.value.fechaInicioExperienciaOJ,
        jefeInmediato: this.seventhFormGroup.value.jefeInmediatoExperienciaOJ,
        puesto: this.seventhFormGroup.value.puestoExperienciaOJ,
        renglonPresupuestario: this.seventhFormGroup.value.renglonPresupuestarioExperienciaOJ,
        motivoFinRelacionLaboral: this.seventhFormGroup.value.motivoFinRelacionLaboralExperienciaOJ
      };
      console.log(this.nuevaExperienciaLaboralOJ);
      this.experienciaLaboralOJ.push(this.nuevaExperienciaLaboralOJ);

      this.seventhFormGroup = this.fb.group({
        dependenciaExperienciaOJ: [''],
        fechaInicioExperienciaOJ: [''],
        fechaFinalizacionExperienciaOJ: [''],
        jefeInmediatoExperienciaOJ: [''],
        puestoExperienciaOJ: [''],
        renglonPresupuestarioExperienciaOJ: [''],
        motivoFinRelacionLaboralExperienciaOJ: ['']
      });

      console.log(this.experienciaLaboralOJ);
    }

  }

  agregarExperienciaLaboral() {
    console.log("Agregar Experiencia laboral ");
    console.log(this.eighthFormGroup.value.institucionEmpresaExperienciaLaboral);
    if (this.eighthFormGroup.value.institucionEmpresaExperienciaLaboral !== '' && this.eighthFormGroup.value.institucionEmpresaExperienciaLaboral !== undefined && this.eighthFormGroup.value.institucionEmpresaExperienciaLaboral !== null) {
      console.log("es distinto de undefined");
      this.nuevaExperienciaLaboral = {
        institucionEmpresa: this.eighthFormGroup.value.institucionEmpresaExperienciaLaboral,
        fechaInicio: this.eighthFormGroup.value.fechaInicioExperienciaLaboral,
        fechaFinalizacion: this.eighthFormGroup.value.fechaFinalizacionExperienciaLaboral,
        renglonPresupuestario: this.eighthFormGroup.value.renglonPresupuestarioExperienciaLaboral,
        puesto: this.eighthFormGroup.value.puestoExperienciaLaboral,
        jefeInmediato: this.eighthFormGroup.value.jefeInmediatoExperienciaLaboral,
        telefono: this.eighthFormGroup.value.telefonoExperienciaLaboral,
        motivoFinRelacionLaboral: this.eighthFormGroup.value.motivoFinRelacionLaboralExperienciaLaboral
      };
      console.log(this.nuevaExperienciaLaboral);
      this.experienciaLaboral.push(this.nuevaExperienciaLaboral);

      this.eighthFormGroup = this.fb.group({
        institucionEmpresaExperienciaLaboral: [''],
        fechaInicioExperienciaLaboral: [''],
        fechaFinalizacionExperienciaLaboral: [''],
        renglonPresupuestarioExperienciaLaboral: [''],
        puestoExperienciaLaboral: [''],
        jefeInmediatoExperienciaLaboral: [''],
        telefonoExperienciaLaboral: [''],
        motivoFinRelacionLaboralExperienciaLaboral: ['']
      });

      console.log(this.experienciaLaboral);
    }

  }

  agregarReferenciaPersonal() {
    console.log("Agregar referencia");
    console.log(this.nineFormGroup.value.nombreReferenciaPersonal);
    if (this.nineFormGroup.value.nombreReferenciaPersonal !== '' && this.nineFormGroup.value.nombreReferenciaPersonal !== undefined && this.nineFormGroup.value.nombreReferenciaPersonal !== null) {
      console.log("es distinto de undefined");

      this.nuevaReferenciaPersonal = {
        nombre: this.nineFormGroup.value.nombreReferenciaPersonal,
        tipoRelacion: this.nineFormGroup.value.tipoRelacionReferenciaPersonal,
        aniosConocerlo: this.nineFormGroup.value.aniosConocerloReferenciaPersonal,
        telefono: this.nineFormGroup.value.telefonoReferenciaPersonal
      };
      console.log(this.nuevaReferenciaPersonal);
      this.referenciasPersonales.push(this.nuevaReferenciaPersonal);

      this.nineFormGroup = this.fb.group({
        nombreReferenciaPersonal: [''],
        tipoRelacionReferenciaPersonal: [''],
        aniosConocerloReferenciaPersonal: [''],
        telefonoReferenciaPersonal: ['']
      });

      console.log(this.referenciasPersonales);
    }

  }

  crearPerfil() {
    this.obtenerDepartamentos();
    this.obtenerComunidadesLinguisticas();
    // swal("Perfil Solicitud Empleo", "Cambios Guardados", "success")
  }
  cambioListaDependientes(e) {
    if (e.option.selected === true) {
      this.listaDependientes.push(e.option.value);
    } else {
      let index = this.listaDependientes.indexOf(e.option.value);
      this.listaDependientes.splice(index, 1);
    }

  }
  getMunicipio(event: any, valDepto: number) {
    if (event.isUserInput) {
      this.convocatoriasService.getListaMunicipioConv(valDepto).subscribe(
        data => {
          this.municipios = data;
        });
    }
  }

  parseDatePasantias(dateString: any): any {
    if (dateString) {
      if (this.valDatesPasantias()) {
        swal("Fecha Inválida", "Fecha Finalización de Pasantía no puede ser anterior a Fecha de Inicio de Pasantía.", "info")
      }
      return this.datePipe.transform(dateString, 'yyyy-MM-dd')
    } else {
      return null;
    }
  }

  valDatesPasantias() {
    let inicio = new Date(this.sixthFormGroup.value.inicioPasantia);
    let fin = new Date(this.sixthFormGroup.value.finPasantia);

    this.validaFecha = false;

    if (inicio.getFullYear() > fin.getFullYear()) {
      this.validaFecha = true;
    } else if (inicio.getMonth() == fin.getMonth() && inicio.getFullYear() == fin.getFullYear()) {
      this.validaFecha = inicio.getDate() > fin.getDate();
    } else if (inicio.getMonth() > fin.getMonth() && inicio.getFullYear() == fin.getFullYear()) {
      this.validaFecha = true;
    }

    return this.validaFecha;
  }

  parseDateExperienciaLaboralOJ(dateString: any): any {
    if (dateString) {
      if (this.valDatesExperienciaLaboralOJ()) {
        swal("Fecha Inválida", "Fecha Finalización Experiencia Laboral OJ no puede ser anterior a Fecha de Inicio Experiencia Laboral OJ.", "info")
      }
      return this.datePipe.transform(dateString, 'yyyy-MM-dd')
    } else {
      return null;
    }
  }

  valDatesExperienciaLaboralOJ() {
    let inicio = new Date(this.seventhFormGroup.value.fechaInicioExperienciaOJ);
    let fin = new Date(this.seventhFormGroup.value.fechaFinalizacionExperienciaOJ);

    this.validaFecha = false;

    if (inicio.getFullYear() > fin.getFullYear()) {
      this.validaFecha = true;
    } else if (inicio.getMonth() == fin.getMonth() && inicio.getFullYear() == fin.getFullYear()) {
      this.validaFecha = inicio.getDate() > fin.getDate();
    } else if (inicio.getMonth() > fin.getMonth() && inicio.getFullYear() == fin.getFullYear()) {
      this.validaFecha = true;
    }

    return this.validaFecha;
  }

  parseDateExperienciaLaboral(dateString: any): any {
    if (dateString) {
      if (this.valDatesExperienciaLaboral()) {
        swal("Fecha Inválida", "Fecha Finalización Experiencia Laboral no puede ser anterior a Fecha de Inicio Experiencia Laboral.", "info")
      }
      return this.datePipe.transform(dateString, 'yyyy-MM-dd')
    } else {
      return null;
    }
  }

  valDatesExperienciaLaboral() {
    let inicio = new Date(this.eighthFormGroup.value.fechaInicioExperienciaLaboral);
    let fin = new Date(this.eighthFormGroup.value.fechaFinalizacionExperienciaLaboral);

    this.validaFecha = false;

    if (inicio.getFullYear() > fin.getFullYear()) {
      this.validaFecha = true;
    } else if (inicio.getMonth() == fin.getMonth() && inicio.getFullYear() == fin.getFullYear()) {
      this.validaFecha = inicio.getDate() > fin.getDate();
    } else if (inicio.getMonth() > fin.getMonth() && inicio.getFullYear() == fin.getFullYear()) {
      this.validaFecha = true;
    }

    return this.validaFecha;
  }

  parseDate(dateString: any): any {
    if (dateString) {
      return this.datePipe.transform(dateString, 'yyyy-MM-dd')
    } else {
      return null;
    }
  }

  get f() { return this.firstFormGroup.controls; }
  get f2() { return this.secondFormGroup.controls; }
  get f3() { return this.thirdFormGroup.controls; }
  get f4() { return this.fourthFormGroup.controls; }
  get f5() { return this.fifthFormGroup.controls; }
  get f6() { return this.sixthFormGroup.controls; }
  get f7() { return this.seventhFormGroup.controls; }
  get f8() { return this.eighthFormGroup.controls; }
  get f9() { return this.nineFormGroup.controls; }

  valDpi(valor: any): boolean {

    if (valor !== null && valor !== undefined && valor !== "") {
      if (valor.length === 13) {
        console.log("dpi correcto");
        return true;
      }
    }
    return false;

  }
  validaIdioma(): boolean {
    let valor = this.secondFormGroup.value.idioma1;

    if (valor !== null && valor !== undefined && valor !== "") {
      let encontrado = this.idiomas.find(x => x.idioma == valor);
      console.log("idioma encontrado: " + encontrado);
      return encontrado != null ? true : false;
    }

    return false;
  }

  guardarData() {
    let PerfilUsuario = {
      NOMBRES: this.firstFormGroup.value.nombres,
      PRIMER_APELLDO: this.firstFormGroup.value.primerApellido,
      SEGUNDO_APELLIDO: this.firstFormGroup.value.segundoApellido,
      FECHA_NACIMIENTO: this.firstFormGroup.value.fechaNac,
      EDAD: this.firstFormGroup.value.edad,
      SEXO: this.firstFormGroup.value.sexo,
      ESTADO_CIVIL: this.firstFormGroup.value.estadoCivilAspirante,
      NACIONALIDAD: this.firstFormGroup.value.nacionalidad,
      PROFESION: this.firstFormGroup.value.profesion,
      DIRECCION: this.firstFormGroup.value.direccion,
      DEPARTAMENTO: this.firstFormGroup.value.selDepartamento,
      MUNICIPIO: this.firstFormGroup.value.selMunicipio,
      CORREO: this.firstFormGroup.value.correo,
      TELEFONO_CASA: this.firstFormGroup.value.telefonoCasa,
      TELEFONO_CELULAR: this.firstFormGroup.value.telefonoCelular,
      DPI: this.firstFormGroup.value.dpi,
      FECHA_VENC_DPI: this.firstFormGroup.value.fechaVencDPI,
      NIT: this.firstFormGroup.value.nit,
      CLASE_LICENCIA: this.firstFormGroup.value.nombreClase,
      NUMERO_LICENCIA: this.firstFormGroup.value.numeroLicencia,
      DISCAPACIDAD: this.firstFormGroup.value.discapacidad,
    }
    console.log("Perfil Usuario");
    console.log(PerfilUsuario);

  }

  submit() {
    console.log(this.firstFormGroup.value);
    console.log(this.secondFormGroup.value);
  }

  eliminarIdioma(index: number) {
    console.log("Se eliimnará el idioma: " + index);
    this.idiomas.splice(index, 1);
    console.log("Despues de eliminar");
    console.log(this.idiomas);
  }

  eliminarFamiliar(index: number) {
    console.log("Se eliimnará el familiar: " + index);
    this.familiares.splice(index, 1);
    console.log("Despues de eliminar");
    console.log(this.familiares);
  }

  eliminarFamiliarLaborandoOJ(index: number) {
    console.log("Se eliimnará el familiar: " + index);
    this.familiaresLaborandoOJ.splice(index, 1);
    console.log("Despues de eliminar");
    console.log(this.familiares);
  }

  eliminarPasantiaOJ(index: number) {
    console.log("Se eliimnará pasantia: " + index);
    this.pasantiasOJ.splice(index, 1);
    console.log("Despues de eliminar");
    console.log(this.pasantiasOJ);
  }

  eliminarExperienciaOJ(index: number) {
    console.log("Se eliimnará experiencia en oj: " + index);
    this.experienciaLaboralOJ.splice(index, 1);
    console.log("Despues de eliminar");
    console.log(this.experienciaLaboralOJ);
  }

  eliminarExperiencia(index: number) {
    console.log("Se eliimnará experiencia : " + index);
    this.experienciaLaboral.splice(index, 1);
    console.log("Despues de eliminar");
    console.log(this.experienciaLaboral);
  }

  eliminarReferenciaPersonal(index: number) {
    console.log("Se eliimnará referencia personal : " + index);
    this.referenciasPersonales.splice(index, 1);
    console.log("Despues de eliminar");
    console.log(this.referenciasPersonales);
  }
}
