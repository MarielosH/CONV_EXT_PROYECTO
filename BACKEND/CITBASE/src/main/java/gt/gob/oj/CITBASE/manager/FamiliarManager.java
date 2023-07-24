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

import gt.gob.oj.CITBASE.model.FamiliaPerfilSE;
import gt.gob.oj.utils.Config;
import gt.gob.oj.utils.ConnectionsPool;
import gt.gob.oj.utils.jsonResult;
import oracle.jdbc.OracleTypes;

public class FamiliarManager {
	String SCHEMA = new Config().getDBSchema();

	public List<Map<String, Object>> getFamiliares(Integer usuario) throws Exception {
		List<Map<String, Object>> salida = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		CallableStatement call = conn.prepareCall("call " + "C##CIT_BASE" + ".PKG_TC_FAMILIAR.PROC_MOSTRAR_TC_FAMILIARES(?,?,?)");
		call.setInt("P_ID_PERSONA", usuario);
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

	public jsonResult inFamiliar(FamiliaPerfilSE familiar, Integer usuario) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a insertar familiar usuario ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "C##CIT_BASE" 
				+ ".PKG_TC_FAMILIAR.PROC_AGREGAR_TC_FAMILIAR(?,?,?,?,?,?,?,?,?,?,?,?)");
		call.setString("P_PROFESION", familiar.profesion);
		call.setString("P_FECHA_NACIMIENTO", familiar.fechaNacimiento);
		call.setString("P_TELEFONO", familiar.telefono);
		call.setString("P_VIVE", familiar.vive);
		call.setString("P_TRABAJA", familiar.trabaja);
		call.setString("P_LUGAR_TRABAJO", familiar.lugarTrabajo);
		call.setString("P_FK_TC_FAMILIAR_REF_TC_PARENTESCO", familiar.parentesco);
		call.setInt("P_FK_TC_FAMILIAR_REF_TC_INFORMACION_PERSONAL_USUARIO", usuario);
		call.setString("P_DEPENDE_ECONOMICAMENTE", familiar.dependeEconomicamente);
		call.setString("P_NOMBRE", familiar.nombreFamiliar);
		call.registerOutParameter("P_ID_SALIDA", OracleTypes.NUMBER);
		call.registerOutParameter("P_MSJ", OracleTypes.VARCHAR);
		call.execute();
	    salida.id = call.getInt("p_id_salida");
	    salida.msj = call.getString("p_msj");
	    if (salida.id > 0)
	      salida.result = "OK"; 
		  System. out. println("todo ok......"+this.SCHEMA+"\n");
	    call.close();
		return salida;
	}

	public jsonResult modFamiliar(FamiliaPerfilSE familiar, Integer id) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a modificar familiar usuario ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "C##CIT_BASE" 
				+ ".PKG_TC_FAMILIAR.PROC_ACTUALIZAR_TC_FAMILIAR(?,?,?,?,?,?,?,?,?,?,?,?)");
		call.setInt("P_ID_FAMILIAR", id);
		call.setString("P_PROFESION", familiar.profesion);
		call.setString("P_FECHA_NACIMIENTO", familiar.fechaNacimiento);
		call.setString("P_TELEFONO", familiar.telefono);
		call.setString("P_VIVE", familiar.vive);
		call.setString("P_TRABAJA", familiar.trabaja);
		call.setString("P_LUGAR_TRABAJO", familiar.lugarTrabajo);
		call.setString("P_FK_TC_FAMILIAR_REF_TC_PARENTESCO", familiar.parentesco);
		call.setString("P_DEPENDE_ECONOMICAMENTE", familiar.dependeEconomicamente);
		call.setString("P_NOMBRE", familiar.nombreFamiliar);
		call.registerOutParameter("P_ID_SALIDA", OracleTypes.NUMBER);
		call.registerOutParameter("P_MSJ", OracleTypes.VARCHAR);
		call.execute();
	    salida.id = call.getInt("p_id_salida");
	    salida.msj = call.getString("p_msj");
	    if (salida.id > 0)
	      salida.result = "OK"; 
		  System. out. println("todo ok......"+this.SCHEMA+"\n");
	    call.close();
		return salida;
	}

	public jsonResult borFamiliar(Integer familiar) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a modificar visibilidad familiar usuario ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "C##CIT_BASE"
				+ ".PKG_TC_FAMILIAR.PROC_BORRAR_TC_FAMILIAR (?,?,?)");
		call.setInt("P_ID_FAMILIAR", familiar);
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
}
	

