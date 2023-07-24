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

import gt.gob.oj.CITBASE.model.ReferenciasPersonales;
import gt.gob.oj.utils.Config;
import gt.gob.oj.utils.ConnectionsPool;
import gt.gob.oj.utils.jsonResult;
import oracle.jdbc.OracleTypes;

public class ReferenciaPersonalManager {
String SCHEMA = new Config().getDBSchema();
	
	public jsonResult inReferenciaPersonal(ReferenciasPersonales referenciaPersonal, Integer usuario) throws Exception{
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a insertar referencia personal ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "C##CIT_BASE"
				+ ".PKG_TC_REFERENCIA_PERSONAL.PROC_AGREGAR_TC_REFERENCIA_PERSONAL (?,?,?,?,?,?,?)");
		call.setString("P_NOMBRE", referenciaPersonal.nombre);
		call.setString("P_TIPO_RELACION", referenciaPersonal.tipoRelacion);
		call.setString("P_ANIO_CONOCERLO", referenciaPersonal.aniosConocerlo);
		call.setString("P_TELEFONO", referenciaPersonal.telefono);
		call.setInt("P_FK_TC_REFERENCIA_PERSONAL_REF_TC_INFORMACION_PERSONAL_USUARIO", usuario);
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
	
	public List<Map<String, Object>> getReferenciaPersonal(Integer usuario) throws Exception {
		List<Map<String,Object>> salida = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		CallableStatement call = conn
				.prepareCall("call " + "C##CIT_BASE" +
						".PKG_TC_REFERENCIA_PERSONAL.PROC_MOSTRAR_TC_REFERENCIA_PERSONAL(?,?,?)");
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
	
	public jsonResult modReferenciaPersonal(ReferenciasPersonales referenciaPersonal, Integer id) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a modificar referencia personal ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "C##CIT_BASE"
				+ ".PKG_TC_REFERENCIA_PERSONAL.PROC_ACTUALIZAR_TC_REFERENCIA_PERSONAL (?,?,?,?,?,?,?)");
		call.setInt("P_ID_REFERENCIA_PERSONAL", id);
		call.setString("P_NOMBRE", referenciaPersonal.nombre);
		call.setString("P_TIPO_RELACION", referenciaPersonal.tipoRelacion);
		call.setString("P_ANIO_CONOCERLO", referenciaPersonal.aniosConocerlo);
		call.setString("P_TELEFONO", referenciaPersonal.telefono);
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
	
	public jsonResult modVisibilidadReferenciaPersonal(Integer id) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a eliminar referencia personal ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "C##CIT_BASE"
				+ ".PKG_TC_REFERENCIA_PERSONAL.PROC_BORRAR_TC_REFERENCIA_PERSONAL (?,?,?)");
		call.setInt("P_ID_REFERENCIA_PERSONAL", id);
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
