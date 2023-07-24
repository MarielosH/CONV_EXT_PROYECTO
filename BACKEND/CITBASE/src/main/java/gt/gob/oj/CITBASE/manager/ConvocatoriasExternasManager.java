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

import gt.gob.oj.CITBASE.model.PerfilSolicitudEmpleo;
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
				"call " + "C##CIT_BASE" + ".PKG_TC_COMUNIDAD_LINGUISTICA.PROC_GET_COMUNIDAD_LINGUISTICA(?,?)");
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

		CallableStatement call = conn.prepareCall("call " + "C##CIT_BASE" + ".PKG_TC_ETNIA.PROC_GET_ETNIAS(?,?)");
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
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
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
		call.setString("P_DOSIS_VACUNAS_COVID", "2");
		call.setString("P_ID_TIPO_VACUNA_COVID", "1");		
		call.setString("P_ID_DISCAPACIDAD", perfil.DISCAPACIDAD);
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
	    call.close();
	    return salida;

	}
	/*
	 * public jsonResult insDependencia(TtDependencia ttDependencia, Connection
	 * conn) throws Exception { jsonResult salida = new jsonResult(); System. out.
	 * println("dentro de llamra a insertar......"+this.SCHEMA+"\n");
	 * CallableStatement call = conn.prepareCall("call " + this.SCHEMA +
	 * ".PKG_DEPENDENCIA.PROC_INS_TT_GEST_DEPENDENCIA (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
	 * ); call.setString("p_codigo_dependencia", ttDependencia.CODIGO_DEPENDENCIA);
	 * call.setString("p_codigo_presupuestario",
	 * ttDependencia.CODIGO_PRESUPUESTARIO); call.setString("p_nombre_dependencia",
	 * ttDependencia.NOMBRE_DEPENDENCIA); call.setString("p_nombre_gafete",
	 * ttDependencia.NOMBRE_GAFETE); call.setString("p_nombre_abreviado",
	 * ttDependencia.NOMBRE_ABREVIADO); call.setString("p_nombre_documento",
	 * ttDependencia.NOMBRE_DOCUMENTO); call.setString("p_conector",
	 * ttDependencia.CONECTOR); call.setString("p_fecha_del_acuerdo",
	 * ttDependencia.FECHA_DEL_ACUERDO); call.setString("p_fecha_entra_vigencia",
	 * ttDependencia.FECHA_ENTRA_VIGENCIA); call.setString("p_fecha_anulacion",
	 * ttDependencia.FECHA_ANULACION); call.setString("p_referencia",
	 * ttDependencia.REFERENCIA); call.setString("p_funcion_unidad",
	 * ttDependencia.FUNCION_UNIDAD); call.setString("p_departamento",
	 * ttDependencia.DEPARTAMENTO); call.setString("p_municipio",
	 * ttDependencia.MUNICIPIO); call.setString("p_tipo_area",
	 * ttDependencia.TIPO_AREA); call.setString("p_ip", ttDependencia.IP);
	 * call.setString("p_id_usuario_registro", ttDependencia.ID_USUARIO_REGISTRO);
	 * call.setString("p_nombre_archivo", ttDependencia.NOMBRE_ARCHIVO);
	 * call.setString("p_ruta_archivo", ttDependencia.RUTA);
	 * call.setString("p_id_tipo_gestion", ttDependencia.ID_TIPO_GESTION);
	 * call.setString("p_fecha_publicacion", ttDependencia.FECHA_PUBLICACION);
	 * call.setString("p_obs_fecha_vigencia", ttDependencia.OBS_FECHA_VIGENCIA);
	 * call.setString("p_proceso_estado_area", ttDependencia.PROCESO_ESTADO_AREA);
	 * call.registerOutParameter("p_id_salida", OracleTypes.NUMBER);
	 * call.registerOutParameter("p_msj", OracleTypes.VARCHAR); call.execute();
	 * salida.id = call.getInt("p_id_salida"); salida.msj = call.getString("p_msj");
	 * if (salida.id > 0) salida.result = "OK"; System. out.
	 * println("todo ok......"+this.SCHEMA+"\n"); call.close(); return salida; }
	 */

	public List<Map<String, Object>> getParentesco() throws Exception {
		List<Map<String, Object>> salida = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		CallableStatement call = conn
				.prepareCall("call " + "C##CIT_BASE" + ".PKG_TC_PARENTESCO.PROC_MOSTRAR_TC_PARENTESCO(?,?)");
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
				.prepareCall("call " + "C##CIT_BASE" + ".PKG_TC_IDIOMA.PROC_GET_IDIOMAS(?,?)");
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
				.prepareCall("call " + "C##CIT_BASE" + ".PKG_TC_ESTADO_CIVIL.PROC_MOSTRAR_TC_ESTADO_CIVIL(?,?)");
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
