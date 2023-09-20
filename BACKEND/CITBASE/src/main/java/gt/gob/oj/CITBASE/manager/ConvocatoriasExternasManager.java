package gt.gob.oj.CITBASE.manager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import gt.gob.oj.CITBASE.model.ExperienciaLaboral;
import gt.gob.oj.CITBASE.model.ExperienciaLaboralOJ;
import gt.gob.oj.CITBASE.model.FamiliaPerfilSE;
import gt.gob.oj.CITBASE.model.FamiliaresLaborandoOJ;
import gt.gob.oj.CITBASE.model.IdiomasPerfilSE;
import gt.gob.oj.CITBASE.model.InformacionAcademica;
import gt.gob.oj.CITBASE.model.InformacionUniversitaria;
import gt.gob.oj.CITBASE.model.PasantiasOJ;
import gt.gob.oj.CITBASE.model.PerfilSolicitudEmpleo;
import gt.gob.oj.CITBASE.model.ReferenciasPersonales;
//import gt.gob.oj.SIGMA.model.Vinculacion;
import gt.gob.oj.utils.Config;
import gt.gob.oj.utils.ConnectionsPool;
import gt.gob.oj.utils.jsonResult;
import oracle.jdbc.OracleTypes;

public class ConvocatoriasExternasManager {
	String SCHEMA = new Config().getDBSchema();
	IdiomaUsuarioManager idiomaUsuarioManager = new IdiomaUsuarioManager();
	FamiliarManager familiarManager = new FamiliarManager();
	FamiliarLaborandoOJManager familiarLaborandoManager = new FamiliarLaborandoOJManager();
	PasantiaOJManager pasantiaManager = new PasantiaOJManager();
	ExperienciaLaboralManager experienciaLaboralManager = new ExperienciaLaboralManager();
	ExperienciaLaboralOJManager experienciaLaboralOJManager = new ExperienciaLaboralOJManager();
	ReferenciaPersonalManager referenciaPersonalManager = new ReferenciaPersonalManager();
	InformacionAcademicaManager informacionAcademicaManager = new InformacionAcademicaManager();
	InformacionUniversitariaManager informacionUniversitariaManager = new InformacionUniversitariaManager();

	public Map<String, Object> getConvocatoriasExternas() throws Exception {
		Map<String, Object> salida = new HashMap<String, Object>();

		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		// Prepare a PL/SQL call
		CallableStatement call = conn.prepareCall("{? = call " + SCHEMA + ".PKG_CONSTANTES.VERSION_SISTEMA()}");

		// Parametros
		call.registerOutParameter(1, OracleTypes.FLOAT);
		call.execute();

		float id = call.getFloat(1);

		call.close();
		conn.close();

		salida.put("VERSIONCONVOCATORIAS", id);

		return salida;
	}

	public List<Map<String, Object>> getDepartamentos() throws Exception {
		List<Map<String, Object>> salida = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		CallableStatement call = conn.prepareCall("call " + "RRHH" + ".PKG_CATALOGOS.PROC_GET_DEPARTAMENTO(?,?)");
		call.registerOutParameter("P_CUR_DATASET", OracleTypes.CURSOR);
		call.registerOutParameter("P_MSJ", OracleTypes.VARCHAR);
		call.execute();
		ResultSet rset = (ResultSet) call.getObject("P_CUR_DATASET");
		ResultSetMetaData meta = rset.getMetaData();
		while (rset.next()) {
			Map<String, Object> map = new HashMap<>();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String key = meta.getColumnName(i).toString();
				String value = Objects.toString(rset.getString(key), "");
				map.put(key, value);
			}
			salida.add(map);
		}
		rset.close();
		call.close();
		conn.close();
		return salida;
	}

	public List<Map<String, Object>> getMunicipios(Integer departamento) throws Exception {
		List<Map<String, Object>> salida = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();

		CallableStatement call = conn.prepareCall("call " + "RRHH" + ".PKG_CATALOGOS.PROC_GET_MUNICIPIO(?,?,?)");
		call.setInt("P_ID_DEPARTAMENTO", departamento);
		call.registerOutParameter("P_CUR_DATASET", OracleTypes.CURSOR);
		call.registerOutParameter("P_MSJ", OracleTypes.VARCHAR);
		call.execute();
		ResultSet rset = (ResultSet) call.getObject("P_CUR_DATASET");
		ResultSetMetaData meta = rset.getMetaData();
		while (rset.next()) {
			Map<String, Object> map = new HashMap<>();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String key = meta.getColumnName(i).toString();
				String value = Objects.toString(rset.getString(key), "");
				map.put(key, value);
			}
			salida.add(map);
		}
		rset.close();
		call.close();
		conn.close();
		return salida;
	}

	public List<Map<String, Object>> getComunidadesLinguisticas() throws Exception {
		List<Map<String, Object>> salida = new ArrayList<Map<String, Object>>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();

		CallableStatement call = conn.prepareCall(
				"call " + "CIT_BASE" + ".PKG_TC_COMUNIDAD_LINGUISTICA.PROC_GET_COMUNIDAD_LINGUISTICA(?,?)");
		call.registerOutParameter("P_CUR_DATASET", OracleTypes.CURSOR);
		call.registerOutParameter("P_MSJ", OracleTypes.VARCHAR);
		call.execute();
		ResultSet rset = (ResultSet) call.getObject("P_CUR_DATASET");
		ResultSetMetaData meta = rset.getMetaData();
		while (rset.next()) {
			Map<String, Object> map = new HashMap<>();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String key = meta.getColumnName(i).toString();
				String value = Objects.toString(rset.getString(key), "");
				map.put(key, value);
			}
			salida.add(map);
		}
		rset.close();
		call.close();
		conn.close();

		return salida;
	}

	public List<Map<String, Object>> getEtnias() throws Exception {
		List<Map<String, Object>> salida = new ArrayList<Map<String, Object>>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();

		CallableStatement call = conn.prepareCall("call " + "CIT_BASE" + ".PKG_TC_ETNIA.PROC_GET_ETNIAS(?,?)");
		call.registerOutParameter("P_CUR_DATASET", OracleTypes.CURSOR);
		call.registerOutParameter("P_MSJ", OracleTypes.VARCHAR);
		call.execute();
		ResultSet rset = (ResultSet) call.getObject("P_CUR_DATASET");
		ResultSetMetaData meta = rset.getMetaData();
		while (rset.next()) {
			Map<String, Object> map = new HashMap<>();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String key = meta.getColumnName(i).toString();
				String value = Objects.toString(rset.getString(key), "");
				map.put(key, value);
			}
			salida.add(map);
		}
		rset.close();
		call.close();
		conn.close();

		return salida;
	}

	public jsonResult inPerfilSolicitudEmpleo(PerfilSolicitudEmpleo perfil) throws Exception {
		// verificar existencia de usuario por dpi

		jsonResult salida = new jsonResult();
		if (existePerfilSolicitudDpi(perfil.DPI)) {
			// salida.result = "El usuario ya existe";
			System.out.println("El usuario ya existe, se va a modificar. ");
			PerfilSolicitudEmpleo perfilExistente = getPerfilSolicitudDpi(perfil.DPI);
			return ModPerfilSolicitudEmpleo(perfil, perfilExistente);
		}

		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();

		// usuario nuevo
		System.out.println("dentro de llamar a insertar perfil usuario ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + this.SCHEMA
				+ ".PKG_TC_INFORMACION_PERSONAL_USUARIO.PROC_AGREGAR_INFORMACION_PERSONAL (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		System.out.println(" SEXO DEL PERFIL  ......" + perfil.SEXO + "\n");
		call.setString("P_NOMBRE", perfil.NOMBRE);
		call.setString("P_PRIMER_APELLIDO", perfil.PRIMER_APELLIDO);
		call.setString("P_SEGUNDO_APELLIDO", perfil.SEGUNDO_APELLIDO);
		call.setString("P_FECHA_NACIMIENTO", perfil.FECHA_NACIMIENTO);
		call.setInt("P_EDAD", perfil.EDAD);
		call.setString("P_SEXO", perfil.SEXO);
		call.setString("P_PROFESION", perfil.PROFESION);
		call.setInt("P_ID_ESTADO_CIVIL", perfil.ESTADO_CIVIL);
		call.setString("P_NACIONALIDAD", perfil.NACIONALIDAD);
		call.setString("P_DIRECCION", perfil.DIRECCION);
		call.setInt("P_ID_MUNICIPIO", perfil.MUNICIPIO);
		call.setInt("P_ID_DEPARTAMENTO", perfil.DEPARTAMENTO);
		call.setString("P_CORREO", perfil.CORREO);
		call.setString("P_TELEFONO_CASA", perfil.TELEFONO_CASA);
		call.setString("P_TELEFONO_CELULAR", perfil.TELEFONO_CELULAR);
		call.setString("P_DPI", perfil.DPI);
		call.setString("P_FECHA_VENCIMIENTO_DPI", perfil.FECHA_VENC_DPI);
		call.setString("P_NIT", perfil.NIT);
		call.setString("P_NUMERO_LICENCIA", perfil.NUMERO_LICENCIA);
		call.setString("P_CLASE_LICENCIA", perfil.CLASE_LICENCIA);
		call.setString("P_FECHA_VENCIMIENTO_LICENCIA", perfil.FECHA_VENC_LICENCIA);
		call.setInt("P_DOSIS_VACUNAS_COVID", 2);
		call.setInt("P_ID_TIPO_VACUNA_COVID", 1);
		call.setInt("P_ID_DISCAPACIDAD", Integer.parseInt(perfil.DISCAPACIDAD));
		call.setInt("P_FK_TC_INFORMACION_PERSONAL_USUARIO_REF_TC_ESTADO_CIVIL", perfil.ESTADO_CIVIL);
		call.setString("P_FK_TC_INFORMACION_PERSONAL_USUARIO_REF_TC_ETNIA", perfil.ETNIA);
		call.setString("P_FK_TC_INFORMACION_PERSONAL_USUARIO_REF_TC_COMUNIDAD_LINGUISTICA",
				perfil.COMUNIDAD_LINGUISTICA);
		call.setInt("P_NO_HIJO", perfil.NO_HIJOS);
		call.registerOutParameter("p_id_salida", OracleTypes.NUMBER);
		call.registerOutParameter("p_msj", OracleTypes.VARCHAR);
		call.execute();
		salida.id = call.getInt("p_id_salida");
		salida.msj = call.getString("p_msj");
		System.out.println("mensaje al insertar:  ......" + salida.msj + "\n");
		System.out.println("id al insertar:  ......" + salida.id + "\n");

		// insertar idioma usuario
		for (IdiomasPerfilSE idioma : perfil.IDIOMAS) {
			idiomaUsuarioManager.inIdiomaUsuario(idioma, idioma.idiomaId, salida.id);
		}

		// insertar familiar
		for (FamiliaPerfilSE familiar : perfil.FAMILIARES) {
			familiarManager.inFamiliar(familiar, salida.id);
		}

		// insertar familiares laborando OJ
		for (FamiliaresLaborandoOJ familiarLaborandoOJ : perfil.FAMILIARES_LABORANDO_OJ) {
			familiarLaborandoManager.inFamiliarLaborandoOJ(familiarLaborandoOJ, salida.id);
		}

		// insertar pasantias
		for (PasantiasOJ pasantia : perfil.PASANTIAS) {
			pasantiaManager.inPasantiaOJ(pasantia, salida.id);
		}

		// insertar experiencia laboral
		for (ExperienciaLaboral experiencia : perfil.EXPERIENCIA_LABORAL) {
			experienciaLaboralManager.inExperienciaLaboral(experiencia, salida.id);
		}

		// insertar experiencia laboral OJ
		for (ExperienciaLaboralOJ experiencia : perfil.EXPERIENCIA_LABORAL_OJ) {
			experienciaLaboralOJManager.inExperienciaLaboralOJ(experiencia, salida.id);
		}

		// insertar referencias personales
		for (ReferenciasPersonales referencia : perfil.REFERENCIAS_PERSONALES) {
			referenciaPersonalManager.inReferenciaPersonal(referencia, salida.id);
		}

		// insertar informacion academica
		insertarInformacionAcademicaPerfil(perfil, salida.id);

		// insertar informacion universitaria
		insertarInformacionUniversitariaPerfil(perfil, salida.id);

		if (salida.id > 0)
			salida.result = "OK";
		System.out.println("todo ok perfil usuario......" + this.SCHEMA + "\n");
		call.close();

		return salida;

	}

	public jsonResult ModPerfilSolicitudEmpleo(PerfilSolicitudEmpleo perfil, PerfilSolicitudEmpleo perfilExistente)
			throws Exception {
		// verificar existencia de usuario por dpi
		jsonResult salida = new jsonResult();
		System.out.println("ID PERFIL USUARIO EN MOD  ......" + perfilExistente.ID + "\n");

		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();

		System.out.println("dentro de llamar a modificar perfil usuario ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + this.SCHEMA
				+ ".PKG_TC_INFORMACION_PERSONAL_USUARIO.PROC_ACTUALIZAR_TC_INFORMACION_PERSONAL_USUARIO (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		call.setInt("P_ID_INFORMACION_PERSONAL_USUARIO", perfilExistente.ID);
		call.setString("P_NOMBRE", perfil.NOMBRE);
		call.setString("P_PRIMER_APELLIDO", perfil.PRIMER_APELLIDO);
		call.setString("P_SEGUNDO_APELLIDO", perfil.SEGUNDO_APELLIDO);
		call.setString("P_FECHA_NACIMIENTO", perfil.FECHA_NACIMIENTO);
		call.setInt("P_EDAD", perfil.EDAD);
		call.setString("P_SEXO", perfil.SEXO);
		call.setString("P_PROFESION", perfil.PROFESION);
		call.setInt("P_ID_ESTADO_CIVIL", perfil.ESTADO_CIVIL);
		call.setString("P_NACIONALIDAD", perfil.NACIONALIDAD);
		call.setString("P_DIRECCION", perfil.DIRECCION);
		call.setInt("P_ID_MUNICIPIO", perfil.MUNICIPIO);
		call.setInt("P_ID_DEPARTAMENTO", perfil.DEPARTAMENTO);
		call.setString("P_CORREO", perfil.CORREO);
		call.setString("P_TELEFONO_CASA", perfil.TELEFONO_CASA);
		call.setString("P_TELEFONO_CELULAR", perfil.TELEFONO_CELULAR);
		call.setString("P_DPI", perfil.DPI);
		call.setString("P_FECHA_VENCIMIENTO_DPI", perfil.FECHA_VENC_DPI);
		call.setString("P_NIT", perfil.NIT);
		call.setString("P_NUMERO_LICENCIA", perfil.NUMERO_LICENCIA);
		call.setString("P_CLASE_LICENCIA", perfil.CLASE_LICENCIA);
		call.setString("P_FECHA_VENCIMIENTO_LICENCIA", perfil.FECHA_VENC_LICENCIA);
		call.setInt("P_DOSIS_VACUNAS_COVID", 2);
		call.setInt("P_ID_TIPO_VACUNA_COVID", 1);
		call.setInt("P_ID_DISCAPACIDAD", Integer.parseInt(perfil.DISCAPACIDAD));
		call.setInt("P_FK_TC_INFORMACION_PERSONAL_USUARIO_REF_TC_ESTADO_CIVIL", perfil.ESTADO_CIVIL);
		call.setString("P_FK_TC_INFORMACION_PERSONAL_USUARIO_REF_TC_ETNIA", perfil.ETNIA);
		call.setString("P_FK_TC_INFORMACION_PERSONAL_USUARIO_REF_TC_COMUNIDAD_LINGUISTICA",
				perfil.COMUNIDAD_LINGUISTICA);
		call.setInt("P_NO_HIJO", perfil.NO_HIJOS);
		call.registerOutParameter("p_id_salida", OracleTypes.NUMBER);
		call.registerOutParameter("p_msj", OracleTypes.VARCHAR);
		call.execute();
		salida.id = call.getInt("p_id_salida");
		salida.msj = call.getString("p_msj");
		if (salida.id > 0)
			salida.result = "OK";
		System.out.println("todo ok actualizar perfil usuario......" + this.SCHEMA + "\n");
		call.close();

		// actualizar idioma usuario
		for (IdiomasPerfilSE idioma : perfil.IDIOMAS) {
			if (perfilExistente.IDIOMAS.stream()
					.filter(actual -> (actual.idiomaId.equals(idioma.idiomaId)))
					.findFirst().orElse(null) != null) {
				idiomaUsuarioManager.modIdiomaUsuario(idioma, idioma.idiomaId, perfilExistente.ID);
			} else {
				idiomaUsuarioManager.inIdiomaUsuario(idioma, idioma.idiomaId, perfilExistente.ID);
			}
		}

		// actualizar familiares
		for (FamiliaPerfilSE familiar : perfil.FAMILIARES) {
			if (perfilExistente.FAMILIARES.stream()
					.filter(fam -> fam.nombreFamiliar.equals(familiar.nombreFamiliar) && fam.mostrar.equals("T"))
					.findFirst().orElse(null) != null) {
				familiarManager.modFamiliar(familiar, familiar.id);
			} else {
				familiarManager.inFamiliar(familiar, perfilExistente.ID);
			}
		}

		// actualizar familiares laborando OJ
		for (FamiliaresLaborandoOJ familiarLaborandoOJ : perfil.FAMILIARES_LABORANDO_OJ) {
			if (perfilExistente.FAMILIARES_LABORANDO_OJ.stream().filter(
					fam -> fam.nombreCompleto.equals(familiarLaborandoOJ.nombreCompleto) && fam.mostrar.equals("T"))
					.findFirst().orElse(null) != null) {
				familiarLaborandoManager.modFamiliarLaborandoOJ(familiarLaborandoOJ, familiarLaborandoOJ.id);
			} else {
				familiarLaborandoManager.inFamiliarLaborandoOJ(familiarLaborandoOJ, perfilExistente.ID);
			}
		}

		// actualizar pasantias
		for (PasantiasOJ pasantia : perfil.PASANTIAS) {
			if (perfilExistente.PASANTIAS.stream()
					.filter(actual -> (actual.dependencia.equals(pasantia.dependencia)
							&& actual.secretarioJuez.equals(pasantia.secretarioJuez) && actual.mostrar.equals("T")))
					.findFirst().orElse(null) != null) {
				pasantiaManager.modPasantiaOJ(pasantia, pasantia.id);
			} else {
				pasantiaManager.inPasantiaOJ(pasantia, perfilExistente.ID);
			}
		}

		// actualizar experiencia laboral
		for (ExperienciaLaboral experiencia : perfil.EXPERIENCIA_LABORAL) {
			if (perfilExistente.EXPERIENCIA_LABORAL.stream()
					.filter(actual -> (actual.institucionEmpresa.equals(experiencia.institucionEmpresa)
							&& actual.jefeInmediato.equals(experiencia.jefeInmediato)
							&& actual.puesto.equals(experiencia.puesto) && actual.mostrar.equals("T")))
					.findFirst().orElse(null) != null) {
				experienciaLaboralManager.modExperienciaLaboral(experiencia, experiencia.id);
			} else {
				experienciaLaboralManager.inExperienciaLaboral(experiencia, perfilExistente.ID);
			}
		}

		// actualizar experiencia laboral OJ
		for (ExperienciaLaboralOJ experiencia : perfil.EXPERIENCIA_LABORAL_OJ) {
			if (perfilExistente.EXPERIENCIA_LABORAL_OJ.stream()
					.filter(actual -> (actual.dependencia.equals(experiencia.dependencia)
							&& actual.jefeInmediato.equals(experiencia.jefeInmediato)
							&& actual.motivoFinRelacionLaboral.equals(experiencia.motivoFinRelacionLaboral)
							&& actual.mostrar.equals("T")))
					.findFirst().orElse(null) != null) {
				experienciaLaboralOJManager.modExperienciaLaboralOJ(experiencia, experiencia.id);
			} else {
				experienciaLaboralOJManager.inExperienciaLaboralOJ(experiencia, perfilExistente.ID);
			}
		}

		// actualizar referencias personales
		for (ReferenciasPersonales referencia : perfil.REFERENCIAS_PERSONALES) {
			if (perfilExistente.REFERENCIAS_PERSONALES.stream()
					.filter(actual -> actual.nombre.equals(referencia.nombre) && actual.mostrar.equals("T")).findFirst()
					.orElse(null) != null) {
				referenciaPersonalManager.modReferenciaPersonal(referencia, referencia.id);
			} else {
				referenciaPersonalManager.inReferenciaPersonal(referencia, perfilExistente.ID);
			}
		}

		// actualizar informacion academica
		modificarInformacionAcademicaPerfil(perfil, perfilExistente.ID);

		// actualizar informacion universitaria
		modificarInformacionUniversitariaPerfil(perfil, perfilExistente.ID);

		salida.result = "OK";
		return salida;

	}

	public void modificarInformacionAcademicaPerfil(PerfilSolicitudEmpleo perfil, Integer usuario) throws Exception {

		List<InformacionAcademica> listaInfoAcademica = new ArrayList<InformacionAcademica>();
		if (!perfil.NIVEL_APRIMARIA.equals("") && !perfil.GRADO_APRIMARIA.equals("")) {
			System.out.println("nivel primaria, grado ......" + perfil.GRADO_APRIMARIA + " institucion: "
					+ perfil.INSTITUCION_PRIMARIA + "\n");
			InformacionAcademica nivelPrimario = new InformacionAcademica();
			nivelPrimario.nivelAcademico = perfil.NIVEL_APRIMARIA;
			nivelPrimario.gradoAprobado = perfil.GRADO_APRIMARIA;
			nivelPrimario.institucionEstudio = perfil.INSTITUCION_PRIMARIA;
			nivelPrimario.constancia = perfil.CONSTANCIA_PRIMARIA;
			listaInfoAcademica.add(nivelPrimario);
		}
		if (!perfil.NIVEL_ABASICOS.equals("") && !perfil.GRADO_ABASICOS.equals("")) {
			System.out.println("nivel primaria, grado ......" + perfil.GRADO_ABASICOS + " institucion: "
					+ perfil.INSTITUCION_BASICOS + "\n");
			InformacionAcademica nivelBasico = new InformacionAcademica();
			nivelBasico.nivelAcademico = perfil.NIVEL_ABASICOS;
			nivelBasico.gradoAprobado = perfil.GRADO_ABASICOS;
			nivelBasico.institucionEstudio = perfil.INSTITUCION_BASICOS;
			nivelBasico.constancia = perfil.CONSTANCIA_BASICOS;
			listaInfoAcademica.add(nivelBasico);
		}
		if (!perfil.NIVEL_ADIVERSIFICADO.equals("") && !perfil.GRADO_ADIVERSIFICADO.equals("")) {
			System.out.println("nivel primaria, grado ......" + perfil.GRADO_ADIVERSIFICADO + " institucion: "
					+ perfil.INSTITUCION_DIVERSIFICADO + "\n");
			InformacionAcademica nivelDiversificado = new InformacionAcademica();
			nivelDiversificado.nivelAcademico = perfil.NIVEL_ADIVERSIFICADO;
			nivelDiversificado.gradoAprobado = perfil.GRADO_ADIVERSIFICADO;
			nivelDiversificado.institucionEstudio = perfil.INSTITUCION_DIVERSIFICADO;
			nivelDiversificado.constancia = perfil.CONSTANCIA_DIVERSIFICADO;
			nivelDiversificado.anioGraduacion = perfil.ANIO_GRADUACION_DIVERSIFICADO;
			nivelDiversificado.carrera = perfil.CARRERA_DIVERSIFICADO;
			listaInfoAcademica.add(nivelDiversificado);
		}

		for (InformacionAcademica info : listaInfoAcademica) {
			System.out.println("Dentro de armar lista informacion académica a modificar ......" + "\n");
			informacionAcademicaManager.modInformacionAcademica(info, usuario);
		}
	}

	public void insertarInformacionAcademicaPerfil(PerfilSolicitudEmpleo perfil, Integer usuario) throws Exception {

		List<InformacionAcademica> listaInfoAcademica = new ArrayList<InformacionAcademica>();
		if (!perfil.NIVEL_APRIMARIA.equals("") && !perfil.GRADO_APRIMARIA.equals("")) {
			InformacionAcademica nivelPrimario = new InformacionAcademica();
			nivelPrimario.nivelAcademico = perfil.NIVEL_APRIMARIA;
			nivelPrimario.gradoAprobado = perfil.GRADO_APRIMARIA;
			nivelPrimario.institucionEstudio = perfil.INSTITUCION_PRIMARIA;
			nivelPrimario.constancia = perfil.CONSTANCIA_PRIMARIA;
			listaInfoAcademica.add(nivelPrimario);
		}
		if (!perfil.NIVEL_ABASICOS.equals("") && !perfil.GRADO_ABASICOS.equals("")) {
			InformacionAcademica nivelBasico = new InformacionAcademica();
			nivelBasico.nivelAcademico = perfil.NIVEL_ABASICOS;
			nivelBasico.gradoAprobado = perfil.GRADO_ABASICOS;
			nivelBasico.institucionEstudio = perfil.INSTITUCION_BASICOS;
			nivelBasico.constancia = perfil.CONSTANCIA_BASICOS;
			listaInfoAcademica.add(nivelBasico);
		}
		if (!perfil.NIVEL_ADIVERSIFICADO.equals("") && !perfil.GRADO_ADIVERSIFICADO.equals("")) {
			InformacionAcademica nivelDiversificado = new InformacionAcademica();
			nivelDiversificado.nivelAcademico = perfil.NIVEL_ADIVERSIFICADO;
			nivelDiversificado.gradoAprobado = perfil.GRADO_ADIVERSIFICADO;
			nivelDiversificado.institucionEstudio = perfil.INSTITUCION_DIVERSIFICADO;
			nivelDiversificado.constancia = perfil.CONSTANCIA_DIVERSIFICADO;
			nivelDiversificado.anioGraduacion = perfil.ANIO_GRADUACION_DIVERSIFICADO;
			nivelDiversificado.carrera = perfil.CARRERA_DIVERSIFICADO;
			listaInfoAcademica.add(nivelDiversificado);
		}

		for (InformacionAcademica info : listaInfoAcademica) {
			System.out.println("Dentro de armar lista informacion académica  ......" + "\n");
			informacionAcademicaManager.inInformacionAcademica(info, usuario);
		}
	}

	public boolean existePerfilSolicitudDpi(String dpi) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		System.out.println("dentro de llamar a existe perfil usuario dpi  ......" + dpi + "\n");
		CallableStatement call = conn.prepareCall("call " + this.SCHEMA
				+ ".PKG_TC_INFORMACION_PERSONAL_USUARIO.PROC_MOSTRAR_TC_INFORMACION_PERSONAL_USUARIO_DPI(?,?,?)");
		call.setString("P_DPI", dpi);
		call.registerOutParameter("p_cur_dataset", OracleTypes.CURSOR);
		call.registerOutParameter("p_msj", OracleTypes.VARCHAR);
		call.execute();

		String msj = call.getString("p_msj");
		System.out.println("mensaje:  ......" + msj + "\n");

		if (msj == null) {
			return true;
		} else {
			return msj.compareTo("No existe el usuario con el DPI especificado") == 0 ? false : true;
		}

	}

	public void insertarInformacionUniversitariaPerfil(PerfilSolicitudEmpleo perfil, Integer usuario) throws Exception {

		List<InformacionUniversitaria> listaInfoUniversitaria = new ArrayList<InformacionUniversitaria>();

		if (!perfil.CARRERA_U.equals("") && !perfil.UNIVERSIDAD.equals("")) {
			InformacionUniversitaria carreraU = new InformacionUniversitaria();
			carreraU.carrera = perfil.CARRERA_U;
			carreraU.universidad = perfil.UNIVERSIDAD;
			carreraU.constancia = perfil.CONSTANCIA_UNIVERSIDAD;
			carreraU.semestreAprobado = perfil.SEMESTRE_APROBADO;
			carreraU.cierrePensum = perfil.CIERRE;
			carreraU.graduadoTecnicoUniversitario = perfil.GRADUADO_TECNICO;
			carreraU.graduadoLicenciatura = perfil.GRADO_LICENCIATURA;
			carreraU.noColegiado = perfil.COLEGIADO;
			carreraU.vigenciaColegiado = perfil.VIGENCIA_COLEGIADO;
			listaInfoUniversitaria.add(carreraU);
		}
		if (!perfil.CARRERA_POSGRADO.equals("") && !perfil.UNIVERSIDAD_POSGRADO.equals("")) {
			InformacionUniversitaria posgrado = new InformacionUniversitaria();
			posgrado.carrera = perfil.CARRERA_POSGRADO;
			posgrado.universidad = perfil.UNIVERSIDAD_POSGRADO;
			posgrado.constancia = perfil.CONSTANCIA_UNIVERSIDAD_POSGRADO;
			posgrado.semestreAprobado = perfil.SEMESTRE_APROBADO_POSGRADO;
			posgrado.cierrePensum = perfil.GRADUADO_MAESTRIA;
			posgrado.graduadoDoctorado = perfil.GRADUADO_DOCTORADO;
			listaInfoUniversitaria.add(posgrado);
		}

		for (InformacionUniversitaria info : listaInfoUniversitaria) {
			System.out.println("Dentro de armar lista informacion universitaria ......" + "\n");
			informacionUniversitariaManager.inInformacionUniversitaria(info, usuario);
		}
	}

	public void modificarInformacionUniversitariaPerfil(PerfilSolicitudEmpleo perfil, Integer usuario)
			throws Exception {

		List<InformacionUniversitaria> listaInfoUniversitaria = new ArrayList<InformacionUniversitaria>();

		if (!perfil.CARRERA_U.equals("") && !perfil.UNIVERSIDAD.equals("")) {
			InformacionUniversitaria carreraU = new InformacionUniversitaria();
			carreraU.carrera = perfil.CARRERA_U;
			carreraU.universidad = perfil.UNIVERSIDAD;
			carreraU.constancia = perfil.CONSTANCIA_UNIVERSIDAD;
			carreraU.semestreAprobado = perfil.SEMESTRE_APROBADO;
			carreraU.cierrePensum = perfil.CIERRE;
			carreraU.graduadoTecnicoUniversitario = perfil.GRADUADO_TECNICO;
			carreraU.graduadoLicenciatura = perfil.GRADO_LICENCIATURA;
			carreraU.noColegiado = perfil.COLEGIADO;
			carreraU.vigenciaColegiado = perfil.VIGENCIA_COLEGIADO;
			listaInfoUniversitaria.add(carreraU);
		}
		if (!perfil.CARRERA_POSGRADO.equals("") && !perfil.UNIVERSIDAD_POSGRADO.equals("")) {
			InformacionUniversitaria posgrado = new InformacionUniversitaria();
			posgrado.carrera = perfil.CARRERA_POSGRADO;
			posgrado.universidad = perfil.UNIVERSIDAD_POSGRADO;
			posgrado.constancia = perfil.CONSTANCIA_UNIVERSIDAD_POSGRADO;
			posgrado.semestreAprobado = perfil.SEMESTRE_APROBADO_POSGRADO;
			posgrado.cierrePensum = perfil.GRADUADO_MAESTRIA;
			posgrado.graduadoDoctorado = perfil.GRADUADO_DOCTORADO;
			listaInfoUniversitaria.add(posgrado);
		}

		for (InformacionUniversitaria info : listaInfoUniversitaria) {
			System.out.println("Dentro de armar lista informacion universitaria ......" + "\n");
			informacionUniversitariaManager.modInformacionUniversitaria(info, usuario);
		}
	}

	public PerfilSolicitudEmpleo getPerfilSolicitudDpi(String dpi) throws Exception {

		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		System.out.println("dentro de llamar a obtener perfil usuario dpi  ......" + dpi + "\n");

		PerfilSolicitudEmpleo perfil = new PerfilSolicitudEmpleo();

		CallableStatement call = conn.prepareCall("call " + this.SCHEMA
				+ ".PKG_TC_INFORMACION_PERSONAL_USUARIO.PROC_MOSTRAR_TC_INFORMACION_PERSONAL_USUARIO_DPI(?,?,?)");
		call.setString("P_DPI", dpi);
		call.registerOutParameter("p_cur_dataset", OracleTypes.CURSOR);
		call.registerOutParameter("p_msj", OracleTypes.VARCHAR);
		call.execute();

		String msj = call.getString("p_msj");
		System.out.println("mensaje:  ......" + msj + "\n");

		if (msj != null) {
			if (msj.compareTo("No existe el usuario con el DPI especificado") == 0) {
				return null;
			}
		}

		ResultSet rset = (ResultSet) call.getObject("p_cur_dataset");

		ResultSetMetaData meta = rset.getMetaData();
		while (rset.next()) {
			// System.out.println("construyendo perfil ......" + "\n");
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String key = meta.getColumnName(i).toString();
				String value = Objects.toString(rset.getString(key), "");
				switch (key) {
				case "ID_INFORMACION_PERSONAL_USUARIO":
					perfil.ID = Integer.parseInt(value);
					System.out.println("ID INFORMACION PERSONAL USUARIO:  ......" + value + "\n");
					break;
				case "NOMBRE":
					perfil.NOMBRE = value;
					break;
				case "PRIMER_APELLIDO":
					perfil.PRIMER_APELLIDO = value;
					break;
				case "SEGUNDO_APELLIDO":
					perfil.SEGUNDO_APELLIDO = value;
					break;
				case "FECHA_NACIMIENTO":
					perfil.FECHA_NACIMIENTO = value;
					break;
				case "EDAD":
					perfil.EDAD = Integer.parseInt(value);
					break;
				case "SEXO":
					perfil.SEXO = value;
					break;
				case "PROFESION":
					perfil.PROFESION = value;
					break;
				case "ID_ESTADO_CIVIL":
					perfil.ESTADO_CIVIL = Integer.parseInt(value);
					break;
				case "NACIONALIDAD":
					perfil.NACIONALIDAD = value;
					break;
				case "DIRECCION":
					perfil.DIRECCION = value;
					break;
				case "ID_MUNICIPIO":
					perfil.MUNICIPIO = Integer.parseInt(value);
					break;
				case "ID_DEPARTAMENTO":
					perfil.DEPARTAMENTO = Integer.parseInt(value);
					break;
				case "CORREO":
					perfil.CORREO = value;
					break;
				case "TELEFONO_CASA":
					perfil.TELEFONO_CASA = value;
					break;
				case "TELEFONO_CELULAR":
					perfil.TELEFONO_CELULAR = value;
					break;
				case "DPI":
					perfil.DPI = value;
					break;
				case "FECHA_VENCIMIENTO_DPI":
					perfil.FECHA_VENC_DPI = value;
					break;
				case "NIT":
					perfil.NIT = value;
				case "NUMERO_LICENCIA":
					perfil.NUMERO_LICENCIA = value;
					break;
				case "CLASE_LICENCIA":
					perfil.CLASE_LICENCIA = value;
					break;
				case "FECHA_VENCIMIENTO_LICENCIA":
					perfil.FECHA_VENC_LICENCIA = value;
					break;
				case "ID_DISCAPACIDAD":
					perfil.DISCAPACIDAD = value;
					break;
				case "FK_TC_INFORMACION_PERSONAL_USUARIO_REF_TC_ETNIA":
					System.out.println("ETNIA:  ......" + value + "\n");
					perfil.ETNIA = value;
					break;
				case "FK_TC_INFORMACION_PERSONAL_USUARIO_REF_TC_COMUNIDAD_LINGUISTICA":
					perfil.COMUNIDAD_LINGUISTICA = value;
					break;
				case "NO_HIJO":
					perfil.NO_HIJOS = Integer.parseInt(value);
					break;

				}

			}

		}
		rset.close();
		call.close();
		conn.close();

		// informacion academica
		List<Map<String, Object>> infoAcademica = new ArrayList<>();
		infoAcademica = informacionAcademicaManager.getInformacionAcademica(perfil.ID);
		System.out.println("INFORMACION ACADEMICA:  ......" + infoAcademica.size() + "\n");
		for (Map<String, Object> map : infoAcademica) {
			if (map.get("NIVEL_ACADEMICO").toString().equals("PRIMARIA")) {
				perfil.NIVEL_APRIMARIA = map.get("NIVEL_ACADEMICO").toString();
				perfil.GRADO_APRIMARIA = map.get("GRADO_APROBADO").toString();
				perfil.INSTITUCION_PRIMARIA = map.get("INSTITUCION_ESTUDIO").toString();
				perfil.CONSTANCIA_PRIMARIA = map.get("CONTANCIA").toString();

			} else if (map.get("NIVEL_ACADEMICO").toString().equals("BASICOS")) {
				perfil.NIVEL_ABASICOS = map.get("NIVEL_ACADEMICO").toString();
				perfil.GRADO_ABASICOS = map.get("GRADO_APROBADO").toString();
				perfil.INSTITUCION_BASICOS = map.get("INSTITUCION_ESTUDIO").toString();
				perfil.CONSTANCIA_BASICOS = map.get("CONTANCIA").toString();

			} else if (map.get("NIVEL_ACADEMICO").toString().equals("DIVERSIFICADO")) {
				perfil.NIVEL_ADIVERSIFICADO = map.get("NIVEL_ACADEMICO").toString();
				perfil.GRADO_ADIVERSIFICADO = map.get("GRADO_APROBADO").toString();
				perfil.INSTITUCION_DIVERSIFICADO = map.get("INSTITUCION_ESTUDIO").toString();
				perfil.CONSTANCIA_DIVERSIFICADO = map.get("CONTANCIA").toString();
				perfil.ANIO_GRADUACION_DIVERSIFICADO = map.get("ANIO_GRADUACION").toString();
				perfil.CARRERA_DIVERSIFICADO = map.get("CARRERA").toString();

			}
		}

		// informacion universitaria
		List<Map<String, Object>> infoUniversitaria = new ArrayList<>();
		infoUniversitaria = informacionUniversitariaManager.getInformacionUniversitaria(perfil.ID);
		System.out.println("INFORMACION UNIVERSITARIA:  ......" + infoUniversitaria.size() + "\n");
		for (Map<String, Object> map : infoUniversitaria) {
			if (map.get("GRADUADO_MAESTRIA").toString().equals("0")
					|| map.get("GRADUADO_DOCTORADO").toString().equals("0")) { // si esta graduado de maestria o
																				// doctorado es de posgrado

				perfil.CARRERA_POSGRADO = map.get("CARRERA").toString();
				perfil.UNIVERSIDAD_POSGRADO = map.get("UNIVERSIDAD").toString();
				perfil.CONSTANCIA_UNIVERSIDAD_POSGRADO = map.get("CONSTANCIA").toString();
				perfil.SEMESTRE_APROBADO_POSGRADO = map.get("SEMESTRE_APROBADO").toString();
				perfil.CIERRE_PENSUM_POSGRADO = map.get("CIERRE_PENSUM").toString();
				perfil.GRADUADO_MAESTRIA = map.get("GRADUADO_MAESTRIA").toString();
				perfil.GRADUADO_DOCTORADO = map.get("GRADUADO_DOCTORADO").toString();
			} else {
				perfil.CARRERA_U = map.get("CARRERA").toString();
				perfil.UNIVERSIDAD = map.get("UNIVERSIDAD").toString();
				perfil.CONSTANCIA_UNIVERSIDAD = map.get("CONSTANCIA").toString();
				perfil.SEMESTRE_APROBADO = map.get("SEMESTRE_APROBADO").toString();
				perfil.CIERRE = map.get("CIERRE_PENSUM").toString();
				perfil.GRADUADO_TECNICO = map.get("GRADUADO_TECNICO_UNIVERSITARIO").toString();
				perfil.GRADO_LICENCIATURA = map.get("GRADUADO_LICENCIATURA").toString();
				perfil.COLEGIADO = map.get("NO_COLEGIADO").toString();
				perfil.VIGENCIA_COLEGIADO = map.get("VIGENCIA_COLEGIADO").toString();
			}
		}

		// detalles
		System.out.println(" armando detalles  ......" + "idiomas" + "\n");
		perfil.IDIOMAS = idiomaUsuarioManager.getIdiomasUsuario(perfil.ID);
		System.out.println(" armando detalles  ......" + "familiares" + "\n");
		perfil.FAMILIARES = familiarManager.getFamiliares(perfil.ID);
		System.out.println(" armando detalles  ......" + "familiares LABORANDO OJ" + "\n");
		perfil.FAMILIARES_LABORANDO_OJ = familiarLaborandoManager.getFamiliarLaborandoOJ(perfil.ID);
		System.out.println(" armando detalles  ......" + "PASANTIA OJ" + "\n");
		perfil.PASANTIAS = pasantiaManager.getPasantiaOJ(perfil.ID);
		System.out.println(" armando detalles  ......" + "EXPERIENCIA LABORAL" + "\n");
		perfil.EXPERIENCIA_LABORAL = experienciaLaboralManager.getExperienciaLaboral(perfil.ID);
		System.out.println(" armando detalles  ......" + "EXPERIENCIA LABORAL OJ" + "\n");
		perfil.EXPERIENCIA_LABORAL_OJ = experienciaLaboralOJManager.getExperienciaLaboralOJ(perfil.ID);
		System.out.println(" armando detalles  ......" + "referencia personal" + "\n");
		perfil.REFERENCIAS_PERSONALES = referenciaPersonalManager.getReferenciaPersonal(perfil.ID);

		return perfil;
	}

	public List<Map<String, Object>> getParentesco() throws Exception {
		List<Map<String, Object>> salida = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		System.out.println("Entró a obtener parentesco ......" + "\n");
		CallableStatement call = conn
				.prepareCall("call " + "CIT_BASE" + ".PKG_TC_PARENTESCO.PROC_MOSTRAR_TC_PARENTESCO(?,?)");
		call.registerOutParameter("P_CUR_DATASET", OracleTypes.CURSOR);
		call.registerOutParameter("P_MSJ", OracleTypes.VARCHAR);
		call.execute();
		ResultSet rset = (ResultSet) call.getObject("P_CUR_DATASET");
		ResultSetMetaData meta = rset.getMetaData();
		while (rset.next()) {
			Map<String, Object> map = new HashMap<>();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String key = meta.getColumnName(i).toString();
				String value = Objects.toString(rset.getString(key), "");
				map.put(key, value);
			}
			salida.add(map);
		}
		rset.close();
		call.close();
		conn.close();
		return salida;
	}

	public List<Map<String, Object>> getIdiomas() throws Exception {
		List<Map<String, Object>> salida = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE" + ".PKG_TC_IDIOMA.PROC_GET_IDIOMAS(?,?)");
		call.registerOutParameter("P_CUR_DATASET", OracleTypes.CURSOR);
		call.registerOutParameter("P_MSJ", OracleTypes.VARCHAR);
		call.execute();
		ResultSet rset = (ResultSet) call.getObject("P_CUR_DATASET");
		ResultSetMetaData meta = rset.getMetaData();
		while (rset.next()) {
			Map<String, Object> map = new HashMap<>();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String key = meta.getColumnName(i).toString();
				String value = Objects.toString(rset.getString(key), "");
				if (key.equals("ID_IDIOMA")) {
					map.put(key, Integer.parseInt(value));
				} else {
					map.put(key, value);
				}

			}
			salida.add(map);
		}
		rset.close();
		call.close();
		conn.close();

		return salida;
	}

	public List<Map<String, Object>> getEstadoCivil() throws Exception {
		List<Map<String, Object>> salida = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		CallableStatement call = conn
				.prepareCall("call " + "CIT_BASE" + ".PKG_TC_ESTADO_CIVIL.PROC_MOSTRAR_TC_ESTADO_CIVIL(?,?)");
		call.registerOutParameter("P_CUR_DATASET", OracleTypes.CURSOR);
		call.registerOutParameter("P_MSJ", OracleTypes.VARCHAR);
		call.execute();
		ResultSet rset = (ResultSet) call.getObject("P_CUR_DATASET");
		ResultSetMetaData meta = rset.getMetaData();
		while (rset.next()) {
			Map<String, Object> map = new HashMap<>();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String key = meta.getColumnName(i).toString();
				String value = Objects.toString(rset.getString(key), "");
				map.put(key, value);
			}
			salida.add(map);
		}
		rset.close();
		call.close();
		conn.close();

		return salida;
	}
}
