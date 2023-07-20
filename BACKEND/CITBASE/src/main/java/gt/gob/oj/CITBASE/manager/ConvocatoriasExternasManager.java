package gt.gob.oj.CITBASE.manager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
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
import gt.gob.oj.CITBASE.model.PasantiasOJ;
import gt.gob.oj.CITBASE.model.PerfilSolicitudEmpleo;
import gt.gob.oj.CITBASE.model.ReferenciasPersonales;
import gt.gob.oj.CITBASE.model.Usuario;
//import gt.gob.oj.SIGMA.model.Vinculacion;
import gt.gob.oj.utils.Config;
import gt.gob.oj.utils.ConnectionsPool;
import gt.gob.oj.utils.jsonResult;
import oracle.jdbc.OracleTypes;

public class ConvocatoriasExternasManager {
	String SCHEMA = new Config().getDBSchema();

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
		//verificar existencia de usuario por dpi
		jsonResult salida = new jsonResult();
		List<Map<String, Object>> perfilExistente = getPerfilSolicitudDpi(perfil.DPI);
		System.out.println("dentro get usuario ......" + perfilExistente.get(0) + "\n");
		
		Integer idUsuario = Integer.parseInt(perfilExistente.get(0).get("ID_INFORMACION_PERSONAL_USUARIO").toString());
		System.out.println("ID PERFIL USUARIO ......" + idUsuario + "\n");
		
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		
		//insertar familiar
		for (FamiliaPerfilSE familiar : perfil.FAMILIARES) {
			CallableStatement call = conn.prepareCall("call " + this.SCHEMA + ".PKG_TC_FAMILIAR.PROC_AGREGAR_TC_FAMILIAR (?,?,?,?,?,?,?,?,?,?,?,?)");
			call.setString("P_PROFESION", familiar.profesion);
			call.setString("P_FECHA_NACIMIENTO", familiar.fechaNacimiento);
			call.setString("P_TELEFONO", familiar.telefono);
			call.setString("P_VIVE", familiar.vive);			
			call.setString("P_TRABAJA", familiar.trabaja);
			call.setString("P_LUGAR_TRABAJO", familiar.lugarTrabajo);
			call.setString("P_FK_TC_FAMILIAR_REF_TC_PARENTESCO", familiar.parentesco);
			call.setInt("P_FK_TC_FAMILIAR_REF_TC_INFORMACION_PERSONAL_USUARIO",idUsuario);
			call.setString("P_DEPENDE_ECONOMICAMENTE", "1");
			call.setString("P_NOMBRE", familiar.nombreFamiliar);
			call.registerOutParameter("p_id_salida", OracleTypes.NUMBER);
		    call.registerOutParameter("p_msj", OracleTypes.VARCHAR);
		    call.execute();
		    salida.id = call.getInt("p_id_salida");
		    salida.msj = call.getString("p_msj");
		    if (salida.id > 0) {
		      salida.result = "OK"; 
			  System. out. println("todo ok familiar......"+ salida.id + " ... "+this.SCHEMA+"\n");
		    }
		    call.close();
		}
		
		//insertar familiares laborando OJ 
		for (FamiliaresLaborandoOJ familiarLaborandoOJ : perfil.FAMILIARES_LABORANDO_OJ) {
			CallableStatement call = conn.prepareCall("call " + this.SCHEMA + ".PKG_TC_FAMILIAR_LABORANDO_OJ.PROC_AGREGAR_TC_FAMILIAR_LABORANDO_OJ (?,?,?,?,?,?,?)");
			call.setString("P_NOMBRE_COMPLETO", familiarLaborandoOJ.nombreCompleto);
			call.setString("P_PARENTESCO", familiarLaborandoOJ.parentesco);
			call.setString("P_DEPENDENCIA", familiarLaborandoOJ.dependencia);
			call.setString("P_PUESTO", familiarLaborandoOJ.puesto);			
			call.setInt("P_FK_TC_FAMILIAR_LABORANDO_OJ_REF_TC_INFORMACION_PERSONAL_USUARIO", idUsuario);
			call.registerOutParameter("p_id_salida", OracleTypes.NUMBER);
		    call.registerOutParameter("p_msj", OracleTypes.VARCHAR);
		    call.execute();
		    salida.id = call.getInt("p_id_salida");
		    salida.msj = call.getString("p_msj");
		    if (salida.id > 0) {
		      salida.result = "OK"; 
			  System. out. println("todo ok. familiar laborando oj....."+ salida.id + " ... "+this.SCHEMA+"\n");
		    }
		    call.close();
		}

		// insertar pasantias
		for (PasantiasOJ pasantia : perfil.PASANTIAS) {
			CallableStatement call = conn.prepareCall("call " + this.SCHEMA
					+ ".PKG_TC_PASANTIA_OJ.PROC_AGREGAR_TC_PASANTIA_OJ (?,?,?,?,?,?,?,?)");
			call.setString("P_FECHA_INICIO", pasantia.fechaInicio);
			call.setString("P_FECHA_FIN", pasantia.fechaFinalizacion);
			call.setString("P_DEPENDENCIA", pasantia.dependencia);
			call.setString("P_SECRETARIO_O_JUEZ", pasantia.secretarioJuez);
			call.setString("P_REGISTRO_PASANTIA", pasantia.registrada);
			call.setInt("P_FK_TC_PASANTIA_OJ_REF_TC_INFORMACION_PERSONAL_USUARIO", idUsuario);			
			call.registerOutParameter("p_id_salida", OracleTypes.NUMBER);
			call.registerOutParameter("p_msj", OracleTypes.VARCHAR);
			call.execute();
			salida.id = call.getInt("p_id_salida");
			salida.msj = call.getString("p_msj");
			if (salida.id > 0) {
				salida.result = "OK";
				System.out.println("todo ok. pasantias....." + salida.id + " ... " + this.SCHEMA + "\n");
			}
			call.close();
		}

		// insertar experiencia laboral
		for (ExperienciaLaboral experiencia : perfil.EXPERIENCIA_LABORAL) {
			CallableStatement call = conn.prepareCall(
					"call " + this.SCHEMA + ".PKG_TC_EXPERIENCIA_LABORAL.PROC_AGREGAR_TC_EXPERIENCIA_LABORAL (?,?,?,?,?,?,?,?,?,?,?)");
			call.setString("P_INSTITUCION_EMPRESA", experiencia.institucionEmpresa);
			call.setString("P_FECHA_INICIO", experiencia.fechaInicio);
			call.setString("P_FECHA_FIN",experiencia.fechaFinalizacion);
			call.setString("P_RENGLON_PRESUPUESTARIO", experiencia.renglonPresupuestario);
			call.setString("P_PUESTO", experiencia.puesto);
			call.setString("P_JEFE_INMEDIATO", experiencia.jefeInmediato);
			call.setString("P_TELEFONO", experiencia.telefono);
			call.setString("P_MOTIVO_FIN_RELACION", experiencia.motivoFinRelacionLaboral);
			call.setInt("P_fk_tc_experiencia_laboral_ref_tc_informacion_personal_usuario", idUsuario);
			call.registerOutParameter("p_id_salida", OracleTypes.NUMBER);
			call.registerOutParameter("p_msj", OracleTypes.VARCHAR);
			call.execute();
			salida.id = call.getInt("p_id_salida");
			salida.msj = call.getString("p_msj");
			if (salida.id > 0) {
				salida.result = "OK";
				System.out.println("todo ok. experiencia laboral ....." + salida.id + " ... " + this.SCHEMA + "\n");
			}
			call.close();
		}
		
		// insertar experiencia laboral OJ
		for (ExperienciaLaboralOJ experiencia : perfil.EXPERIENCIA_LABORAL_OJ) {
			CallableStatement call = conn.prepareCall("call " + this.SCHEMA
					+ ".PKG_TC_EXPERIENCIA_LABORAL_OJ.PROC_AGREGAR_TC_EXPERIENCIA_LABORAL_OJ (?,?,?,?,?,?,?,?,?,?,?)");
			call.setString("P_PUESTO", experiencia.puesto);
			call.setString("P_FECHA_INICIO", experiencia.fechaInicio);
			call.setString("P_FECHA_FIN", experiencia.fechaFinalizacion);
			call.setString("P_RENGLON_PRESUPUESTARIO", experiencia.renglonPresupuestario);
			call.setString("P_DEPENDENCIA", experiencia.dependencia);	
			call.setString("P_JEFE_INMEDIATO", experiencia.jefeInmediato);
			call.setString("P_MOTIVO_FIN", experiencia.motivoFinRelacionLaboral);
			call.setInt("P_FK_TC_EXPERIENCIA_LABORAL_OJ_REF_TC_INFORMACION_PERSONAL_USUARIO", idUsuario);
			call.registerOutParameter("p_id_salida", OracleTypes.NUMBER);
			call.registerOutParameter("p_msj", OracleTypes.VARCHAR);
			call.execute();
			salida.id = call.getInt("p_id_salida");
			salida.msj = call.getString("p_msj");
			if (salida.id > 0) {
				salida.result = "OK";
				System.out.println("todo ok experiencia laboral oj......" + salida.id + " ... " + this.SCHEMA + "\n");
			}
			call.close();
		}
				
		// insertar referencias personales
		for (ReferenciasPersonales referencia : perfil.REFERENCIAS_PERSONALES) {
			CallableStatement call = conn.prepareCall("call " + this.SCHEMA
					+ ".PKG_TC_REFERENCIA_PERSONAL.PROC_AGREGAR_TC_REFERENCIA_PERSONAL (?,?,?,?,?,?,?)");
			call.setString("P_NOMBRE", referencia.nombre);
			call.setString("P_TIPO_RELACION", referencia.tipoRelacion);
			call.setString("P_ANIO_CONOCERLO", referencia.aniosConocerlo);
			call.setString("P_TELEFONO", referencia.telefono);
			call.setInt("P_FK_TC_REFERENCIA_PERSONAL_REF_TC_INFORMACION_PERSONAL_USUARIO", idUsuario);
			call.registerOutParameter("p_id_salida", OracleTypes.NUMBER);
			call.registerOutParameter("p_msj", OracleTypes.VARCHAR);
			call.execute();
			salida.id = call.getInt("p_id_salida");
			salida.msj = call.getString("p_msj");
			if (salida.id > 0) {
				salida.result = "OK";
				System.out.println("todo ok referencia personal......" + salida.id + " ... " + this.SCHEMA + "\n");
			}
			call.close();
		}
		/*ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		
		System.out.println("dentro de llamar a insertar perfil usuario ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + this.SCHEMA
				+ ".PKG_TC_INFORMACION_PERSONAL_USUARIO.PROC_AGREGAR_INFORMACION_PERSONAL (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

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
		call.setString("P_FECHA_VENCIMIENTO_DPI",perfil.FECHA_VENC_DPI);
		call.setString("P_NIT", perfil.NIT);
		call.setString("P_NUMERO_LICENCIA", perfil.NUMERO_LICENCIA);
		call.setString("P_CLASE_LICENCIA", perfil.CLASE_LICENCIA);
		call.setString("P_FECHA_VENCIMIENTO_LICENCIA", perfil.FECHA_VENC_DPI);
		call.setInt("P_DOSIS_VACUNAS_COVID", 2);
		call.setInt("P_ID_TIPO_VACUNA_COVID", 1);		
		call.setInt("P_ID_DISCAPACIDAD", Integer.parseInt(perfil.DISCAPACIDAD));
		call.setInt("P_FK_TC_INFORMACION_PERSONAL_USUARIO_REF_TC_ESTADO_CIVIL", perfil.ESTADO_CIVIL);
		call.setString("P_FK_TC_INFORMACION_PERSONAL_USUARIO_REF_TC_ETNIA", perfil.ETNIA);
		call.setString("P_FK_TC_INFORMACION_PERSONAL_USUARIO_REF_TC_COMUNIDAD_LINGUISTICA", perfil.COMUNIDAD_LINGUISTICA);
		call.setInt("P_NO_HIJO", perfil.NO_HIJOS);
		call.registerOutParameter("p_id_salida", OracleTypes.NUMBER);
	    call.registerOutParameter("p_msj", OracleTypes.VARCHAR);
	    call.execute();
	    salida.id = call.getInt("p_id_salida");
	    salida.msj = call.getString("p_msj");
	    if (salida.id > 0)
	      salida.result = "OK"; 
		  System. out. println("todo ok......"+this.SCHEMA+"\n");
	    call.close();*/
		
		//call.close();
		salida.result = "OK"; 
	    return salida;

	}


	public List<Map<String, Object>> getPerfilSolicitudDpi(String dpi) throws Exception {
		List<Map<String, Object>> salida = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		System.out.println("dentro de llamar a obtener perfil usuario dpi  ......" + dpi + "\n");
		CallableStatement call = conn.prepareCall("call " +  this.SCHEMA + ".PKG_TC_INFORMACION_PERSONAL_USUARIO.PROC_MOSTRAR_TC_INFORMACION_PERSONAL_USUARIO_DPI(?,?,?)");
		call.setString("P_DPI", dpi);
		call.registerOutParameter("p_cur_dataset", OracleTypes.CURSOR);
		call.registerOutParameter("p_msj", OracleTypes.VARCHAR);
		call.execute();
		ResultSet rset = (ResultSet) call.getObject("p_cur_dataset");
		ResultSetMetaData meta = rset.getMetaData();
		while (rset.next()) {
			Map<String, Object> map = new HashMap<>();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String key = meta.getColumnName(i).toString();
				String value = Objects.toString(rset.getString(key), "");
				System. out. println("todo ok......"+this.SCHEMA+"\n");
				map.put(key, value);
			}
			salida.add(map);
		}
		rset.close();
		call.close();
		conn.close();
		return salida;
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
		List<Map<String,Object>> salida = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		CallableStatement call = conn
				.prepareCall("call " + "CIT_BASE" + ".PKG_TC_IDIOMA.PROC_GET_IDIOMAS(?,?)");
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

	public List<Map<String, Object>> getEstadoCivil() throws Exception {
		List<Map<String,Object>> salida = new ArrayList<>();
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
