import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Observable, Subscription, BehaviorSubject } from '../../../../node_modules/rxjs';
import { HttpEvent, HttpRequest, HttpClient, HttpResponse } from '../../../../node_modules/@angular/common/http';
import { ngf } from "angular-file"
import { DatePipe, Location } from '@angular/common';
import swal from 'sweetalert2';
import { AuthService } from '../../recursos/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ConvocatoriasExternasService } from '../convocatorias-externas.service';
import { TiposLicencia, Discapacidad, Idioma, Familiar, FamiliarLaborandoOJ, PasantiaOJ, ExperienciaLaboralOJ, ExperienciaLaboral, ReferenciaPersonal, Sexo } from 'app/constantes';

import jsPDF from 'jspdf';
import pdfMake from 'pdfmake/build/pdfmake';
import pdfFonts from 'pdfmake/build/vfs_fonts';
pdfMake.vfs = pdfFonts.pdfMake.vfs;
import htmlToPdfmake from 'html-to-pdfmake';
@Component({
  selector: 'app-perfilwizard',
  templateUrl: './perfilwizard.component.html',
  styleUrls: ['./perfilwizard.component.scss']
})
export class PerfilwizardComponent implements OnInit {
  cantidiomas = 0;
  tiposLicencias = [];
  discapacidades = [];
  generos = [];
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
  listaIdiomasExtranjeros;
  listaParentescos;
  listaEstadoCivil;
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
  fechaVencLicencia;
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
  perfilUsuarioExistente;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  fourthFormGroup: FormGroup;
  fifthFormGroup: FormGroup;
  sixthFormGroup: FormGroup;
  seventhFormGroup: FormGroup;
  eighthFormGroup: FormGroup;
  nineFormGroup: FormGroup;
  startDate = new Date(1950, 0, 1);

  //title = 'htmltopdf';

  @ViewChild('pdfTable') pdfTable: ElementRef;

  constructor(private convocatoriasService: ConvocatoriasExternasService, public authService: AuthService,
    public HttpClient: HttpClient, private fb: FormBuilder, private _location: Location, private datePipe: DatePipe,
    private router: Router, private route: ActivatedRoute
  ) {
    this.session = this.authService.getsession().SESSION;
  }

  ngOnInit() {
    this.tiposLicencias = TiposLicencia;
    this.discapacidades = Discapacidad;
    this.generos = Sexo;

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
      fechaVencLicencia: [''],
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
      noHijo: ['', Validators.required],
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
    this.obtenerIdiomasExtranjeros();
    this.obtenerEstadoCivil();

    this.convocatoriasService.getPerfilUsuarioByDPI(localStorage.getItem('cui')).subscribe(
      (data: any) => {
        if (data != null && data != undefined) {
          console.log("Perfil Usuario");
          console.log(data);
          localStorage.setItem("informacion_personal_id", data.ID);
          this.firstFormGroup.patchValue({
            nombres: (data.NOMBRE != undefined && data.NOMBRE != null) ? data.NOMBRE : '',
            primerApellido: (data.PRIMER_APELLIDO != undefined && data.PRIMER_APELLIDO != null) ? data.PRIMER_APELLIDO : '',
            segundoApellido: (data.SEGUNDO_APELLIDO != undefined && data.SEGUNDO_APELLIDO != null) ? data.SEGUNDO_APELLIDO : '',
            fechaNac: (data.FECHA_NACIMIENTO != undefined && data.FECHA_NACIMIENTO != null) ? this.datePipe.transform(data.FECHA_NACIMIENTO, 'yyyy-MM-dd') : '',
            edad: (data.EDAD != undefined && data.EDAD != null) ? data.EDAD : '',
            sexo: (data.SEXO != undefined && data.SEXO != null) ? Number(data.SEXO) : '',
            estadoCivilAspirante: (data.ESTADO_CIVIL != undefined && data.ESTADO_CIVIL != null) ? data.ESTADO_CIVIL.toString() : '',
            nacionalidad: (data.NACIONALIDAD != undefined && data.NACIONALIDAD != null) ? data.NACIONALIDAD : '',
            profesion: (data.PROFESION != undefined && data.PROFESION != null) ? data.PROFESION : '',
            direccion: (data.DIRECCION != undefined && data.DIRECCION != null) ? data.DIRECCION : '',
            selDepartamento: (data.DEPARTAMENTO != undefined && data.DEPARTAMENTO != null) ? data.DEPARTAMENTO.toString() : '',
            selMunicipio: (data.MUNICIPIO != undefined && data.MUNICIPIO != null) ? data.MUNICIPIO.toString() : '',
            correo: (data.CORREO != undefined && data.CORREO != null) ? data.CORREO : '',
            telefonoCasa: (data.TELEFONO_CASA != undefined && data.TELEFONO_CASA != null) ? data.TELEFONO_CASA : '',
            telefonoCelular: (data.TELEFONO_CELULAR != undefined && data.TELEFONO_CELULAR != null) ? data.TELEFONO_CELULAR : '',
            dpi: (data.DPI != undefined && data.DPI != null) ? data.DPI : '',
            fechaVencDPI: (data.FECHA_VENC_DPI != undefined && data.FECHA_VENC_DPI != null) ? this.datePipe.transform(data.FECHA_VENC_DPI, 'yyyy-MM-dd') : '',
            fechaVencLicencia: (data.FECHA_VENC_LICENCIA != undefined && data.FECHA_VENC_LICENCIA != null) ? this.datePipe.transform(data.FECHA_VENC_LICENCIA, 'yyyy-MM-dd') : '',
            nit: (data.NIT != undefined && data.NIT != null) ? data.NIT : '',
            nombreClase: (data.CLASE_LICENCIA != undefined && data.CLASE_LICENCIA != null) ? Number(data.CLASE_LICENCIA) : '',
            numeroLicencia: (data.NUMERO_LICENCIA != undefined && data.NUMERO_LICENCIA != null) ? data.NUMERO_LICENCIA : '',
            discapacidad: (data.DISCAPACIDAD != undefined && data.DISCAPACIDAD != null) ? Number(data.DISCAPACIDAD) : ''
          })
        }
        this.secondFormGroup.patchValue({
          etnia: (data.ETNIA != undefined && data.ETNIA != null) ? data.ETNIA : '',
          comunidadLinguistica: (data.COMUNIDAD_LINGUISTICA != undefined && data.COMUNIDAD_LINGUISTICA != null) ? data.COMUNIDAD_LINGUISTICA : ''
        });

        this.idiomas = data.IDIOMAS;

        this.thirdFormGroup.patchValue({
          noHijo: (data.NO_HIJOS != undefined && data.NO_HIJOS != null) ? data.NO_HIJOS : ''
        });

        this.fifthFormGroup.patchValue({
          gradoAprobadoPrimaria: (data.GRADO_APRIMARIA != undefined && data.GRADO_APRIMARIA != null) ? data.GRADO_APRIMARIA : '',
          institucionEstudiosPrimaria: (data.INSTITUCION_PRIMARIA != undefined && data.INSTITUCION_PRIMARIA != null) ? data.INSTITUCION_PRIMARIA : '',
          constanciaPrimaria: (data.CONSTANCIA_PRIMARIA != undefined && data.CONSTANCIA_PRIMARIA != null) ? data.CONSTANCIA_PRIMARIA : '',
          gradoAprobadoBasicos: (data.GRADO_ABASICOS != undefined && data.GRADO_ABASICOS != null) ? data.GRADO_ABASICOS : '',
          institucionEstudiosBasicos: (data.INSTITUCION_BASICOS != undefined && data.INSTITUCION_BASICOS != null) ? data.INSTITUCION_BASICOS : '',
          constanciaBasicos: (data.CONSTANCIA_BASICOS != undefined && data.CONSTANCIA_BASICOS != null) ? data.CONSTANCIA_BASICOS : '',
          gradoAprobadoDiversificado: (data.GRADO_ADIVERSIFICADO != undefined && data.GRADO_ADIVERSIFICADO != null) ? data.GRADO_ADIVERSIFICADO : '',
          institucionEstudiosDiversificado: (data.INSTITUCION_DIVERSIFICADO != undefined && data.INSTITUCION_DIVERSIFICADO != null) ? data.INSTITUCION_DIVERSIFICADO : '',
          constanciaDiversificado: (data.CONSTANCIA_DIVERSIFICADO != undefined && data.CONSTANCIA_DIVERSIFICADO != null) ? data.CONSTANCIA_DIVERSIFICADO : '',
          anioGraduacionDiversificado: (data.ANIO_GRADUACION_DIVERSIFICADO != undefined && data.ANIO_GRADUACION_DIVERSIFICADO != null) ? data.ANIO_GRADUACION_DIVERSIFICADO : '',
          carreraDiversificado: (data.CARRERA_DIVERSIFICADO != undefined && data.CARRERA_DIVERSIFICADO != null) ? data.CARRERA_DIVERSIFICADO : '',
          carreraU: (data.CARRERA_U != undefined && data.CARRERA_U != null) ? data.CARRERA_U : '',
          universidad: (data.UNIVERSIDAD != undefined && data.UNIVERSIDAD != null) ? data.UNIVERSIDAD : '',
          constanciaUniversidad: (data.CONSTANCIA_UNIVERSIDAD != undefined && data.CONSTANCIA_UNIVERSIDAD != null) ? data.CONSTANCIA_UNIVERSIDAD : '',
          semestreA: (data.SEMESTRE_APROBADO != undefined && data.SEMESTRE_APROBADO != null) ? data.SEMESTRE_APROBADO : '',
          cierre: (data.CIERRE != undefined && data.CIERRE != null) ? Number(data.CIERRE) : '',
          graduadoTecnico: (data.GRADUADO_TECNICO != undefined && data.GRADUADO_TECNICO != null) ? Number(data.GRADUADO_TECNICO) : '',
          gradoLicenciatura: (data.GRADO_LICENCIATURA != undefined && data.GRADO_LICENCIATURA != null) ? Number(data.GRADO_LICENCIATURA) : '',
          colegiado: (data.COLEGIADO != undefined && data.COLEGIADO != null) ? data.COLEGIADO : '',
          vigenciaColegiado: (data.VIGENCIA_COLEGIADO != undefined && data.VIGENCIA_COLEGIADO != null) ? this.datePipe.transform(data.VIGENCIA_COLEGIADO, 'yyyy-MM-dd') : '',
          carreraPosgrado: (data.CARRERA_POSGRADO != undefined && data.CARRERA_POSGRADO != null) ? data.CARRERA_POSGRADO : '',
          universidadPosgrado: (data.UNIVERSIDAD_POSGRADO != undefined && data.UNIVERSIDAD_POSGRADO != null) ? data.UNIVERSIDAD_POSGRADO : '',
          constanciaUniversidadPosgrado: (data.CONSTANCIA_UNIVERSIDAD_POSGRADO != undefined && data.CONSTANCIA_UNIVERSIDAD_POSGRADO != null) ? data.CONSTANCIA_UNIVERSIDAD_POSGRADO : '',
          semestreAprobadoPosgrado: (data.SEMESTRE_APROBADO_POSGRADO != undefined && data.SEMESTRE_APROBADO_POSGRADO != null) ? data.SEMESTRE_APROBADO_POSGRADO : '',
          cierrePensumPosgrado: (data.CIERRE_PENSUM_POSGRADO != undefined && data.CIERRE_PENSUM_POSGRADO != null) ? Number(data.CIERRE_PENSUM_POSGRADO) : '',
          graduadoMaestria: (data.GRADUADO_MAESTRIA != undefined && data.GRADUADO_MAESTRIA != null) ? Number(data.GRADUADO_MAESTRIA) : '',
          graduadoDoctorado: (data.GRADUADO_DOCTORADO != undefined && data.GRADUADO_DOCTORADO != null) ? Number(data.GRADUADO_DOCTORADO) : ''
        });

        data.FAMILIARES.forEach(element => {
          element.fechaNacimiento = this.datePipe.transform(element.fechaNacimiento, 'yyyy-MM-dd');
          element.vive = Number(element.vive);
          element.trabaja = Number(element.trabaja);
        });

        data.PASANTIAS.forEach(element => {
          element.fechaFinalizacion = this.datePipe.transform(element.fechaFinalizacion, 'yyyy-MM-dd');
          element.fechaInicio = this.datePipe.transform(element.fechaInicio, 'yyyy-MM-dd');
          element.registrada = Number(element.registrada);
        });

        data.EXPERIENCIA_LABORAL_OJ.forEach(element => {
          element.fechaFinalizacion = this.datePipe.transform(element.fechaFinalizacion, 'yyyy-MM-dd');
          element.fechaInicio = this.datePipe.transform(element.fechaInicio, 'yyyy-MM-dd');
          element.motivoFinRelacionLaboral = Number(element.motivoFinRelacionLaboral);
        });

        data.EXPERIENCIA_LABORAL.forEach(element => {
          element.fechaFinalizacion = this.datePipe.transform(element.fechaFinalizacion, 'yyyy-MM-dd');
          element.fechaInicio = this.datePipe.transform(element.fechaInicio, 'yyyy-MM-dd');
        });

        this.familiaresLaborandoOJ = data.FAMILIARES_LABORANDO_OJ;
        this.referenciasPersonales = data.REFERENCIAS_PERSONALES;
        this.familiares = data.FAMILIARES;
        this.pasantiasOJ = data.PASANTIAS;
        this.experienciaLaboralOJ = data.EXPERIENCIA_LABORAL_OJ;
        this.experienciaLaboral = data.EXPERIENCIA_LABORAL;

      });
  }

  public downloadAsPDF() {
    const doc = new jsPDF();

    const pdfTable = this.pdfTable.nativeElement;

    var html = htmlToPdfmake(pdfTable.innerHTML);

    const documentDefinition = { content: html };
    pdfMake.createPdf(documentDefinition).open();

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

  obtenerIdiomasExtranjeros() {
    this.convocatoriasService
      .getListaIdiomasExtranjeros()
      .subscribe(
        data => {
          this.listaIdiomasExtranjeros = data;
        });
  }

  obtenerParentescos() {
    this.convocatoriasService
      .getListaParentescoConv()
      .subscribe(
        data => {
          this.listaParentescos = data;
        });

  }

  obtenerEstadoCivil() {
    this.convocatoriasService
      .getListaEstadoCivilConv
      ()
      .subscribe(
        data => {
          this.listaEstadoCivil = data;
        });

  }

  agregarIdioma() {
    if (this.secondFormGroup.value.idioma1 !== '' && this.secondFormGroup.value.idioma1 !== undefined && this.secondFormGroup.value.idioma1 !== null) {
      this.nuevoIdioma = {
        idiomaId: this.secondFormGroup.value.idioma1,
        habla: this.secondFormGroup.value.habla1,
        lee: this.secondFormGroup.value.lee1,
        escribe: this.secondFormGroup.value.escribe1,
        mostrar: 'T'
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
        dependeEconomicamente: this.thirdFormGroup.value.dependeEconomicamente == true ? '1' : '0'
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

  buscarNivelIdioma(id): string {
    switch (id) {
      case 0:
        return 'Excelente'
      case 1:
        return 'Muy bueno'
      case 2:
        return 'Bueno'
      case 3:
        return 'Regular'
      default:
        return '';
    }

  }
  buscarIdioma(id): string {
    if (this.listaIdiomasExtranjeros != null && this.listaIdiomasExtranjeros.length > 0) {
      let encontrado = this.listaIdiomasExtranjeros.find(x => x.ID_IDIOMA == id);
      return encontrado != null ? encontrado.IDIOMA : '';
    }
    return '';
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

  buscarEstadoCivil(id): string {
    if (this.listaEstadoCivil != null && this.listaEstadoCivil.length > 0) {
      let encontrado = this.listaEstadoCivil.find(x => x.ID_ESTADO_CIVIL == id);
      return encontrado != null ? encontrado.ESTADO_CIVIL : '';
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
    swal("Perfil Usuario Actualizado", "", "success");
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
      let date = this.datePipe.transform(dateString, 'yyyy-MM-dd', '+0430', 'en-ES');
      return date;
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
      let encontrado = this.idiomas.find(x => x.idiomaId == valor);
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
      ESTADO_CIVIL: this.firstFormGroup.value.estadoCivilAspirante,
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
      FECHA_VENC_LICENCIA: this.parseDate(this.firstFormGroup.value.fechaVencLicencia),
      NIT: this.firstFormGroup.value.nit,
      CLASE_LICENCIA: this.firstFormGroup.value.nombreClase,
      NUMERO_LICENCIA: this.firstFormGroup.value.numeroLicencia,
      DISCAPACIDAD: this.firstFormGroup.value.discapacidad,
      ETNIA: this.secondFormGroup.value.etnia,
      COMUNIDAD_LINGUISTICA: this.secondFormGroup.value.comunidadLinguistica,
      NO_HIJOS: this.thirdFormGroup.value.noHijo,
      NIVEL_APRIMARIA: 'PRIMARIA',
      NIVEL_ABASICOS: 'BASICOS',
      NIVEL_ADIVERSIFICADO: 'DIVERSIFICADO',
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
      GRADO_TECNICO: this.fifthFormGroup.value.gradoTecnico,
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
          localStorage.setItem("informacion_personal_id", data.id);
          swal("Perfil Usuario Guardado", data.msj, "success");
        } else {
          swal("Error", data.msj, "error")
        }
      })
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
