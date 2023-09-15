package gt.gob.oj.CITBASE.model;

import java.util.ArrayList;
import java.util.List;

public class PerfilSolicitudEmpleo {
	
	public Integer ID;
	public String NOMBRE;
	public String PRIMER_APELLIDO;
	public String SEGUNDO_APELLIDO;
	public String FECHA_NACIMIENTO;
	public Integer EDAD;
	public String SEXO;
	public String PROFESION;
	public Integer ESTADO_CIVIL;
	public String NACIONALIDAD;
	public String DIRECCION;
	public Integer MUNICIPIO;
	public Integer DEPARTAMENTO;
	public String CORREO;
	public String TELEFONO_CASA;
	public String TELEFONO_CELULAR;
	public String DPI;
	public String FECHA_VENC_DPI;
	public String NIT;
	public String NUMERO_LICENCIA;
	public String CLASE_LICENCIA;
	public String FECHA_VENC_LICENCIA;
	public String DISCAPACIDAD;
	public String ETNIA;
	public String COMUNIDAD_LINGUISTICA;
	public String TIENE_HIJOS;
	public Integer NO_HIJOS;
	public String NIVEL_APRIMARIA;
	public String NIVEL_ABASICOS;
	public String NIVEL_ADIVERSIFICADO;
	public String GRADO_APRIMARIA;
	public String INSTITUCION_PRIMARIA;
	public String CONSTANCIA_PRIMARIA;
	public String GRADO_ABASICOS;
	public String INSTITUCION_BASICOS;
	public String CONSTANCIA_BASICOS;
	public String GRADO_ADIVERSIFICADO;
	public String INSTITUCION_DIVERSIFICADO;
	public String CONSTANCIA_DIVERSIFICADO;
	public String ANIO_GRADUACION_DIVERSIFICADO;
	public String CARRERA_DIVERSIFICADO;
	public String CARRERA_U;
	public String UNIVERSIDAD;
	public String CONSTANCIA_UNIVERSIDAD;
	public String SEMESTRE_APROBADO;
	public String CIERRE;
	public String GRADO_TECNICO;
	public String GRADUADO_TECNICO;
	public String GRADO_LICENCIATURA;
	public String COLEGIADO;
	public String VIGENCIA_COLEGIADO;
	public String CARRERA_POSGRADO;
	public String UNIVERSIDAD_POSGRADO;
	public String CONSTANCIA_UNIVERSIDAD_POSGRADO;
	public String SEMESTRE_APROBADO_POSGRADO;
	public String CIERRE_PENSUM_POSGRADO;
	public String GRADUADO_MAESTRIA;
	public String GRADUADO_DOCTORADO;
	public List<IdiomasPerfilSE> IDIOMAS = new ArrayList <IdiomasPerfilSE>();
	public List<FamiliaPerfilSE> FAMILIARES = new ArrayList <FamiliaPerfilSE>();
	public List<FamiliaresLaborandoOJ> FAMILIARES_LABORANDO_OJ = new ArrayList <FamiliaresLaborandoOJ>();
	public List<PasantiasOJ> PASANTIAS = new ArrayList <PasantiasOJ>();
	public List<ExperienciaLaboral> EXPERIENCIA_LABORAL = new ArrayList <ExperienciaLaboral>();
	public List<ExperienciaLaboralOJ> EXPERIENCIA_LABORAL_OJ = new ArrayList <ExperienciaLaboralOJ>();
	public List<ReferenciasPersonales> REFERENCIAS_PERSONALES = new ArrayList <ReferenciasPersonales>();
	
}
