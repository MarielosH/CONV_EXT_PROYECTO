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
		List<Map<String, Object>> salida = new ArrayList<Map<String, Object>>();

		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();

		Statement stmt = null;
		ResultSet rset = null;
		stmt = conn.createStatement();
		String SQL = "SELECT DISTINCT DEPARTAMENTO, NOMBRE_DEPARTAMENTO FROM RRHH.RH_DEPARTAMENTO WHERE PAIS = 1 ORDER BY DEPARTAMENTO ASC";
		rset = stmt.executeQuery(SQL);

		while (rset.next()) {
			Map<String, Object> actual = new HashMap<String, Object>();
			actual.put("DEPARTAMENTO", rset.getString("DEPARTAMENTO"));
			actual.put("NOMBRE_DEPARTAMENTO", rset.getString("NOMBRE_DEPARTAMENTO"));
			salida.add(actual);
		}

		return salida;
	}

	public List<Map<String, Object>> getMunicipios(Integer departamento) throws Exception {
		List<Map<String, Object>> salida = new ArrayList<Map<String, Object>>();

		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();

		Statement stmt = null;
		ResultSet rset = null;
		stmt = conn.createStatement();
		String SQL = String.format("SELECT MUNICIPIO, NOMBRE_MUNICIPIO FROM RRHH.RH_MUNICIPIO WHERE DEPARTAMENTO = %d AND PAIS = 1 ORDER BY MUNICIPIO ASC", departamento);
		rset = stmt.executeQuery(SQL);

		while (rset.next()) {
			Map<String, Object> actual = new HashMap<String, Object>();
			actual.put("MUNICIPIO", rset.getString("MUNICIPIO"));
			actual.put("NOMBRE_MUNICIPIO", rset.getString("NOMBRE_MUNICIPIO"));
			salida.add(actual);
		}

		return salida;
	}
}
