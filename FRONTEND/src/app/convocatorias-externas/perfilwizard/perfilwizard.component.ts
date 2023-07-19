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
  listaParentescos;
  listaEtnias;
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
  parentesco;
  familiar;
  fechaNacFamiliar;
  telFamiliar;
  viveFamiliar;
  profesionFamiliar;
  trabajaFamiliar;
  lugarTrabajoFamiliar;
  dependeEconomicamente;
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
  registrada;
  listaDependientes = [];
  listarazaPerfil;
  listaComunidadesLinguisticas;
  noHijo;
  dependientes;
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
      noHijo: [''],
      dependientes: [''],
      parentesco: [''],
      familiar: [''],
      fechaNacFamiliar: [''],
      telFamiliar: [''],
      viveFamiliar: [''],
      profesionFamiliar: [''],
      trabajaFamiliar: [''],
      lugarTrabajoFamiliar: [''],
      dependeEconomicamente: ['']
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
      secrectarioJuezPasantia: [''],
      registrada: ['']
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
    this.obtenerParentescos();
    this.obtenerEtnias();
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

  obtenerEtnias() {
    this.convocatoriasService
      .getListaEtniasConv()
      .subscribe(
        data => {
          this.listaEtnias = data;
        });
  }

  obtenerParentescos() {
    this.convocatoriasService
      .getListaParentescoConv()
      .subscribe(
        data => {
          this.listaParentescos = data;
        });
        console.log("parentescos");
        console.log(this.listaParentescos);
  }

  agregarIdioma() {
    if (this.secondFormGroup.value.idioma1 !== '' && this.secondFormGroup.value.idioma1 !== undefined && this.secondFormGroup.value.idioma1 !== null) {
      this.nuevoIdioma = {
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
    if (this.thirdFormGroup.value.familiar !== '' && this.thirdFormGroup.value.familiar !== undefined && this.thirdFormGroup.value.familiar !== null) {
      this.nuevoFamiliar = {
        nombreFamiliar: this.thirdFormGroup.value.familiar,
        parentesco: this.thirdFormGroup.value.parentesco,
        fechaNacimiento: this.parseDate(this.thirdFormGroup.value.fechaNacFamiliar),
        telefono: this.thirdFormGroup.value.telFamiliar,
        vive: this.thirdFormGroup.value.viveFamiliar,
        profesion: this.thirdFormGroup.value.profesionFamiliar,
        trabaja: this.thirdFormGroup.value.trabajaFamiliar,
        lugarTrabajo: this.thirdFormGroup.value.lugarTrabajoFamiliar,
        dependeEconomicamente: this.thirdFormGroup.value.dependeEconomicamente
      };

      this.familiares.push(this.nuevoFamiliar);
      this.thirdFormGroup = this.fb.group({
        noHijo: [this.thirdFormGroup.value.noHijo],
        dependientes: [''],
        parentesco: [''],
        familiar: [''],
        fechaNacFamiliar: [''],
        telFamiliar: [''],
        viveFamiliar: [''],
        profesionFamiliar: [''],
        trabajaFamiliar: [''],
        lugarTrabajoFamiliar: [''],
        dependeEconomicamente: ['']
      });
    }

  }

  agregarFamiliarLaborandoOJ() {
    if (this.fourthFormGroup.value.nombreFOJ !== '' && this.fourthFormGroup.value.nombreFOJ !== undefined && this.fourthFormGroup.value.nombreFOJ !== null) {

      this.nuevoFamiliarLaborandoOJ = {
        nombreCompleto: this.fourthFormGroup.value.nombreFOJ,
        parentesco: this.fourthFormGroup.value.parentescoFOJ,
        dependencia: this.fourthFormGroup.value.dependenciaFOJ,
        puesto: this.fourthFormGroup.value.puestoFOJ
      };
      this.familiaresLaborandoOJ.push(this.nuevoFamiliarLaborandoOJ);
      this.fourthFormGroup = this.fb.group({
        parentescoFOJ: [''],
        nombreFOJ: [''],
        dependenciaFOJ: [''],
        puestoFOJ: ['']
      });

    }

  }

  agregarPasantia() {
    if (this.sixthFormGroup.value.dependenciaPasantia !== '' && this.sixthFormGroup.value.dependenciaPasantia !== undefined && this.sixthFormGroup.value.dependenciaPasantia !== null) {

      this.nuevaPasantiaOJ = {
        dependencia: this.sixthFormGroup.value.dependenciaPasantia,
        fechaInicio: this.parseDate(this.sixthFormGroup.value.inicioPasantia),
        fechaFinalizacion: this.parseDate(this.sixthFormGroup.value.finPasantia),
        secretarioJuez: this.sixthFormGroup.value.secrectarioJuezPasantia,
        registrada: this.sixthFormGroup.value.registrada
      };
      this.pasantiasOJ.push(this.nuevaPasantiaOJ);
      this.sixthFormGroup = this.fb.group({
        dependenciaPasantia: [''],
        inicioPasantia: [''],
        finPasantia: [''],
        secrectarioJuezPasantia: [''],
        registrada: ['']
      });

    }

  }

  agregarExperienciaLaboralOJ() {
    if (this.seventhFormGroup.value.puestoExperienciaOJ !== '' && this.seventhFormGroup.value.puestoExperienciaOJ !== undefined && this.seventhFormGroup.value.puestoExperienciaOJ !== null) {
      this.nuevaExperienciaLaboralOJ = {
        dependencia: this.seventhFormGroup.value.dependenciaExperienciaOJ,
        fechaInicio: this.parseDate(this.seventhFormGroup.value.fechaInicioExperienciaOJ),
        fechaFinalizacion: this.parseDate(this.seventhFormGroup.value.fechaFinalizacionExperienciaOJ),
        jefeInmediato: this.seventhFormGroup.value.jefeInmediatoExperienciaOJ,
        puesto: this.seventhFormGroup.value.puestoExperienciaOJ,
        renglonPresupuestario: this.seventhFormGroup.value.renglonPresupuestarioExperienciaOJ,
        motivoFinRelacionLaboral: this.seventhFormGroup.value.motivoFinRelacionLaboralExperienciaOJ
      };
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
    }

  }

  agregarExperienciaLaboral() {
    if (this.eighthFormGroup.value.institucionEmpresaExperienciaLaboral !== '' && this.eighthFormGroup.value.institucionEmpresaExperienciaLaboral !== undefined && this.eighthFormGroup.value.institucionEmpresaExperienciaLaboral !== null) {
      this.nuevaExperienciaLaboral = {
        institucionEmpresa: this.eighthFormGroup.value.institucionEmpresaExperienciaLaboral,
        fechaInicio: this.parseDate(this.eighthFormGroup.value.fechaInicioExperienciaLaboral),
        fechaFinalizacion: this.parseDate(this.eighthFormGroup.value.fechaFinalizacionExperienciaLaboral),
        renglonPresupuestario: this.eighthFormGroup.value.renglonPresupuestarioExperienciaLaboral,
        puesto: this.eighthFormGroup.value.puestoExperienciaLaboral,
        jefeInmediato: this.eighthFormGroup.value.jefeInmediatoExperienciaLaboral,
        telefono: this.eighthFormGroup.value.telefonoExperienciaLaboral,
        motivoFinRelacionLaboral: this.eighthFormGroup.value.motivoFinRelacionLaboralExperienciaLaboral
      };
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
    }

  }

  agregarReferenciaPersonal() {
    if (this.nineFormGroup.value.nombreReferenciaPersonal !== '' && this.nineFormGroup.value.nombreReferenciaPersonal !== undefined && this.nineFormGroup.value.nombreReferenciaPersonal !== null) {
      this.nuevaReferenciaPersonal = {
        nombre: this.nineFormGroup.value.nombreReferenciaPersonal,
        tipoRelacion: this.nineFormGroup.value.tipoRelacionReferenciaPersonal,
        aniosConocerlo: this.nineFormGroup.value.aniosConocerloReferenciaPersonal,
        telefono: this.nineFormGroup.value.telefonoReferenciaPersonal
      };
      this.referenciasPersonales.push(this.nuevaReferenciaPersonal);
      this.nineFormGroup = this.fb.group({
        nombreReferenciaPersonal: [''],
        tipoRelacionReferenciaPersonal: [''],
        aniosConocerloReferenciaPersonal: [''],
        telefonoReferenciaPersonal: ['']
      });
    }

  }

  buscarEstadoCivil(id): string {
    let encontrado = this.estadoCivil.find(x => x.ID == id);
    return encontrado != null ? encontrado.DESCRIPCION : '';
  }

  buscarDepartamento(id): string {
    if (this.listaDepartamentos != null && this.listaDepartamentos.length > 0) {
      let encontrado = this.listaDepartamentos.find(x => x.DEPARTAMENTO == id);
      return encontrado != null ? encontrado.NOMBRE_DEPARTAMENTO : '';
    }
    return '';
  }

  buscarMunicipio(id): string {
    if (this.municipios != null && this.municipios.length > 0) {
      let encontrado = this.municipios.find(x => x.MUNICIPIO == id);
      return encontrado != null ? encontrado.NOMBRE_MUNICIPIO : '';
    }
    return '';
  }

  buscarClaseLicencia(id): string {
    let encontrado = this.tiposLicencias.find(x => x.ID == id);
    return encontrado != null ? encontrado.DESCRIPCION : '';
  }

  buscarDiscapacidad(id): string {
    let encontrado = this.discapacidades.find(x => x.ID == id);
    return encontrado != null ? encontrado.DESCRIPCION : '';
  }


  buscarComunidadLinguistica(id): string {
    if (this.listaComunidadLinguistica != null && this.listaComunidadLinguistica.length > 0) {
      let encontrado = this.listaComunidadLinguistica.find(x => x.ID_COMUNIDAD_LINGUISTICA == id);
      return encontrado != null ? encontrado.COMUNIDAD : '';
    }
    return '';
  }

  buscarparentesco(id): string {
    if (this.listaParentescos != null && this.listaParentescos.length > 0) {
      let encontrado = this.listaParentescos.find(x => x.ID == id);
      return encontrado != null ? encontrado.PARENTESCO : '';
    }
    return '';
  }

   buscarEtnia(id): string {
    if (this.listaEtnias != null && this.listaEtnias.length > 0) {
      let encontrado = this.listaEtnias.find(x => x.ID_ETNIA == id);
      return encontrado != null ? encontrado.ETNIA : '';
    }
    return '';
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
      return this.datePipe.transform(dateString, 'dd/MM/yyyy')
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
      return this.datePipe.transform(dateString, 'dd/MM/yyyy')
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
      return this.datePipe.transform(dateString, 'dd/MM/yyyy')
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
      return this.datePipe.transform(dateString, 'dd/MM/yyyy')
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
      return encontrado != null ? true : false;
    }

    return false;
  }

  guardarData() {
    let PerfilUsuario = {
      NOMBRE: this.firstFormGroup.value.nombres.toUpperCase(),
      PRIMER_APELLIDO: this.firstFormGroup.value.primerApellido.toUpperCase(),
      SEGUNDO_APELLIDO: this.firstFormGroup.value.segundoApellido.toUpperCase(),
      FECHA_NACIMIENTO: this.parseDate(this.firstFormGroup.value.fechaNac),
      EDAD: this.firstFormGroup.value.edad,
      SEXO: this.firstFormGroup.value.sexo,
      //ESTADO_CIVIL: this.firstFormGroup.value.estadoCivilAspirante,
      ESTADO_CIVIL: 1,
      NACIONALIDAD: this.firstFormGroup.value.nacionalidad.toUpperCase(),
      PROFESION: this.firstFormGroup.value.profesion.toUpperCase(),
      DIRECCION: this.firstFormGroup.value.direccion.toUpperCase(),
      DEPARTAMENTO: this.firstFormGroup.value.selDepartamento,
      MUNICIPIO: this.firstFormGroup.value.selMunicipio,
      CORREO: this.firstFormGroup.value.correo,
      TELEFONO_CASA: this.firstFormGroup.value.telefonoCasa,
      TELEFONO_CELULAR: this.firstFormGroup.value.telefonoCelular,
      DPI: this.firstFormGroup.value.dpi,
      FECHA_VENC_DPI: this.parseDate(this.firstFormGroup.value.fechaVencDPI),
      NIT: this.firstFormGroup.value.nit,
      CLASE_LICENCIA: this.firstFormGroup.value.nombreClase,
      NUMERO_LICENCIA: this.firstFormGroup.value.numeroLicencia,
      DISCAPACIDAD: this.firstFormGroup.value.discapacidad,
      ETNIA: this.secondFormGroup.value.etnia,
      COMUNIDAD_LINGUISTICA: this.secondFormGroup.value.comunidadLinguistica,
      NO_HIJOS: this.thirdFormGroup.value.no_hijo,
      NIVEL_APRIMARIA: this.fifthFormGroup.value.nivelAcademicoPrimaria.toUpperCase(),
      NIVEL_ABASICOS: this.fifthFormGroup.value.nivelAcademicoBasicos.toUpperCase(),
      NIVEL_ADIVERSIFICADO: this.fifthFormGroup.value.nivelAcademicoDiversificado.toUpperCase(),
      GRADO_APRIMARIA: this.fifthFormGroup.value.gradoAprobadoPrimaria.toUpperCase(),
      INSTITUCION_PRIMARIA: this.fifthFormGroup.value.institucionEstudiosPrimaria.toUpperCase(),
      CONSTANCIA_PRIMARIA: this.fifthFormGroup.value.constanciaPrimaria.toUpperCase(),
      GRADO_ABASICOS: this.fifthFormGroup.value.gradoAprobadoBasicos.toUpperCase(),
      INSTITUCION_BASICOS: this.fifthFormGroup.value.institucionEstudiosBasicos.toUpperCase(),
      CONSTANCIA_BASICOS: this.fifthFormGroup.value.constanciaBasicos.toUpperCase(),
      GRADO_ADIVERSIFICADO: this.fifthFormGroup.value.gradoAprobadoDiversificado.toUpperCase(),
      INSTITUCION_DIVERSIFICADO: this.fifthFormGroup.value.institucionEstudiosDiversificado.toUpperCase(),
      CONSTANCIA_DIVERSIFICADO: this.fifthFormGroup.value.constanciaDiversificado.toUpperCase(),
      ANIO_GRADUACION_DIVERSIFICADO: this.fifthFormGroup.value.anioGraduacionDiversificado.toUpperCase(),
      CARRERA_DIVERSIFICADO: this.fifthFormGroup.value.carreraDiversificado.toUpperCase(),
      CARRERA_U: this.fifthFormGroup.value.carreraU.toUpperCase(),
      UNIVERSIDAD: this.fifthFormGroup.value.universidad.toUpperCase(),
      CONSTANCIA_UNIVERSIDAD: this.fifthFormGroup.value.constanciaUniversidad.toUpperCase(),
      SEMESTRE_APROBADO: this.fifthFormGroup.value.semestreA.toUpperCase(),
      CIERRE: this.fifthFormGroup.value.cierre,
      GRADO_TECNICO: this.fifthFormGroup.value.gradoTecnico.toUpperCase(),
      GRADUADO_TECNICO: this.fifthFormGroup.value.graduadoTecnico,
      GRADO_LICENCIATURA: this.fifthFormGroup.value.gradoLicenciatura,
      COLEGIADO: this.fifthFormGroup.value.colegiado,
      VIGENCIA_COLEGIADO: this.fifthFormGroup.value.vigenciaColegiado,
      CARRERA_POSGRADO: this.fifthFormGroup.value.carreraPosgrado.toUpperCase(),
      UNIVERSIDAD_POSGRADO: this.fifthFormGroup.value.universidadPosgrado.toUpperCase(),
      CONSTANCIA_UNIVERSIDAD_POSGRADO: this.fifthFormGroup.value.constanciaUniversidadPosgrado.toUpperCase(),
      SEMESTRE_APROBADO_POSGRADO: this.fifthFormGroup.value.semestreAprobadoPosgrado.toUpperCase(),
      CIERRE_PENSUM_POSGRADO: this.fifthFormGroup.value.cierrePensumPosgrado,
      GRADUADO_MAESTRIA: this.fifthFormGroup.value.graduadoMaestria,
      GRADUADO_DOCTORADO: this.fifthFormGroup.value.graduadoDoctorado,
      DEPENDIENTES: this.listaDependientes,
      IDIOMAS: this.idiomas,
      FAMILIARES: this.familiares,
      FAMILIARES_LABORANDO_OJ: this.familiaresLaborandoOJ,
      PASANTIAS: this.pasantiasOJ,
      EXPERIENCIA_LABORAL_OJ: this.experienciaLaboralOJ,
      EXPERIENCIA_LABORAL: this.experienciaLaboral,
      REFERENCIAS_PERSONALES: this.referenciasPersonales
    }
    console.log("Perfil Usuario");
    console.log(PerfilUsuario);
    this.creacionPerfilUsuario(PerfilUsuario);

  }

  creacionPerfilUsuario(perfilUsuario) {
    this.convocatoriasService.insPerfilUsuario(perfilUsuario).subscribe(
      data => {
        if (data.result == 'OK') {
          console.log("res: " + data.msj);
          swal("Perfil Usuario Guardado", data.msj, "success");
        } else {
          swal("Error", data.msj, "error")
        }
      })
  }

  submit() {
    console.log(this.firstFormGroup.value);
    console.log(this.secondFormGroup.value);
  }

  eliminarIdioma(index: number) {
    this.idiomas.splice(index, 1);
  }

  eliminarFamiliar(index: number) {
    this.familiares.splice(index, 1);
  }

  eliminarFamiliarLaborandoOJ(index: number) {
    this.familiaresLaborandoOJ.splice(index, 1);
  }

  eliminarPasantiaOJ(index: number) {
    this.pasantiasOJ.splice(index, 1);
  }

  eliminarExperienciaOJ(index: number) {
    this.experienciaLaboralOJ.splice(index, 1);
  }

  eliminarExperiencia(index: number) {
    this.experienciaLaboral.splice(index, 1);
  }

  eliminarReferenciaPersonal(index: number) {
    this.referenciasPersonales.splice(index, 1);
  }
}
