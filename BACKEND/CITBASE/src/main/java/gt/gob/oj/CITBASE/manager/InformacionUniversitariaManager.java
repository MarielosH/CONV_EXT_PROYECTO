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

import gt.gob.oj.CITBASE.model.InformacionUniversitaria;
import gt.gob.oj.utils.Config;
import gt.gob.oj.utils.ConnectionsPool;
import gt.gob.oj.utils.jsonResult;
import oracle.jdbc.OracleTypes;

public class InformacionUniversitariaManager {
	String SCHEMA = new Config().getDBSchema();

	public jsonResult inInformacionUniversitaria(InformacionUniversitaria informacionUniversitaria, Integer usuario)
			throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a insertar informacion universitaria ......"
				+ informacionUniversitaria.carrera + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_INFORMACION_UNIVERSITARIA.PROC_AGREGAR_TC_INFORMACION_UNIVERSITARIA (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		call.setString("P_CARRERA", informacionUniversitaria.carrera);
		call.setString("P_UNIVERSIDAD", informacionUniversitaria.universidad);
		call.setString("P_CONSTANCIA", informacionUniversitaria.constancia);
		call.setString("P_SEMESTRE_APROBADO", informacionUniversitaria.semestreAprobado);
		call.setString("P_CIERRE_PENSUM", informacionUniversitaria.cierrePensum);
		call.setString("P_GRADUADO_TECNICO_UNIVERSITARIO", informacionUniversitaria.graduadoTecnicoUniversitario);
		call.setString("P_GRADUADO_LICENCIATURA", informacionUniversitaria.graduadoLicenciatura);
		call.setString("P_GRADUADO_MAESTRIA", informacionUniversitaria.graduadoMaestria);
		call.setString("P_GRADUADO_DOCTORADO", informacionUniversitaria.graduadoDoctorado);
		call.setString("P_NO_COLEGIADO", informacionUniversitaria.noColegiado);
		call.setString("P_VIGENCIA_COLEGIADO", informacionUniversitaria.vigenciaColegiado);
		call.setInt("P_FK_TC_INFORMACION_UNIVERSITARIA_REF_TC_INFORMACION_PERSONAL_USUARIO", usuario);
		call.registerOutParameter("p_id_salida", OracleTypes.NUMBER);
		call.registerOutParameter("p_msj", OracleTypes.VARCHAR);
		call.execute();
		salida.id = call.getInt("p_id_salida");
		salida.msj = call.getString("p_msj");
		if (salida.id > 0)
			salida.result = "OK";
		System.out.println("todo ok información universitaria ......" + this.SCHEMA + "\n");
		call.close();
		return salida;
	}

	public List<Map<String, Object>> getInformacionUniversitaria(Integer usuario) throws Exception {
		List<Map<String, Object>> salida = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		System.out.println(" ENTRÓ A OBTENER INFORMACIÓN UNIVERSITARIA  ...... id usuario " + usuario + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_INFORMACION_UNIVERSITARIA.PROC_MOSTRAR_TC_INFORMACION_UNIVERSITARIA(?,?,?)");
		call.setInt("P_ID_PERSONA", usuario);
		call.registerOutParameter("P_CUR_DATASET", OracleTypes.CURSOR);
		call.registerOutParameter("P_MSJ", OracleTypes.VARCHAR);
		call.execute();
		ResultSet rset = (ResultSet) call.getObject("P_CUR_DATASET");
		ResultSetMetaData meta = rset.getMetaData();
		while (rset.next()) {
			System.out.println("Encontró información academica ......" +"\n");
			Map<String, Object> map = new HashMap<>();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String key = meta.getColumnName(i).toString();
				String value = Objects.toString(rset.getString(key), "");
				map.put(key, value);
				System.out.println(" valor ......" + value + "\n");
			}
			salida.add(map);
		}
		rset.close();
		call.close();
		conn.close();

		return salida;
	}

	public jsonResult modInformacionUniversitaria(InformacionUniversitaria informacionUniversitaria, Integer id)
			throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a modificar informacion universitaria ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_INFORMACION_UNIVERSITARIA.PROC_ACTUALIAR_TC_INFORMACION_UNIVERSITARIA (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		call.setInt("P_ID_INFORMACION_UNIVERSITARIA", id);
		call.setString("P_CARRERA", informacionUniversitaria.carrera);
		call.setString("P_UNIVERSIDAD", informacionUniversitaria.universidad);
		call.setString("P_CONSTANCIA", informacionUniversitaria.constancia);
		call.setString("P_SEMESTRE_APROBADO", informacionUniversitaria.semestreAprobado);
		call.setString("P_CIERRE_PENSUM", informacionUniversitaria.cierrePensum);
		call.setString("P_GRADUADO_TECNICO_UNIVERSITARIO", informacionUniversitaria.graduadoTecnicoUniversitario);
		call.setString("P_GRADUADO_LICENCIATURA", informacionUniversitaria.graduadoLicenciatura);
		call.setString("P_GRADUADO_MAESTRIA", informacionUniversitaria.graduadoMaestria);
		call.setString("P_GRADUADO_DOCTORADO", informacionUniversitaria.graduadoDoctorado);
		call.setString("P_NO_COLEGIADO", informacionUniversitaria.noColegiado);
		call.setString("P_VIGENCIA_COLEGIADO", informacionUniversitaria.vigenciaColegiado);
		call.registerOutParameter("p_id_salida", OracleTypes.NUMBER);
		call.registerOutParameter("p_msj", OracleTypes.VARCHAR);
		call.execute();
		salida.id = call.getInt("p_id_salida");
		salida.msj = call.getString("p_msj");
		if (salida.id > 0)
			salida.result = "OK";
		System.out.println("todo ok......" + this.SCHEMA + "\n");
		call.close();
		return salida;
	}

	public jsonResult modVisibilidadInformacionUniversitaria(Integer id) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a eliminar informacion universitaria ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_INFORMACION_UNIVERSITARIA.PROC_BORRAR_TC_INFORMACION_UNIVERSITARIA (?,?,?)");
		call.setInt("P_ID_INFORMACION_UNIVERSITARIA", id);
		call.registerOutParameter("p_id_salida", OracleTypes.NUMBER);
		call.registerOutParameter("p_msj", OracleTypes.VARCHAR);
		call.execute();
		salida.id = call.getInt("p_id_salida");
		salida.msj = call.getString("p_msj");
		if (salida.id > 0)
			salida.result = "OK";
		System.out.println("todo ok......" + this.SCHEMA + "\n");
		call.close();
		return salida;
	}
}
