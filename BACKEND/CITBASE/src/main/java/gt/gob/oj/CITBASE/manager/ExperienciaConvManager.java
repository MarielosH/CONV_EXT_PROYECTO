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

import gt.gob.oj.CITBASE.model.ExperienciaConv;
import gt.gob.oj.utils.Config;
import gt.gob.oj.utils.ConnectionsPool;
import gt.gob.oj.utils.jsonResult;
import oracle.jdbc.OracleTypes;

public class ExperienciaConvManager {
	String SCHEMA = new Config().getDBSchema();
	
	public jsonResult inExperienciaConv(ExperienciaConv experienciaConv, Integer convocatoria) throws Exception{
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a insertar experiencia conv......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_EXPERIENCIA_CONVOCATORIA.PROC_AGREGAR_TC_EXPERIENCIA_CONVOCATORIA (?,?,?,?)");
		call.setString("p_requisito_experiencia", experienciaConv.requisitoExperiencia);
		call.setInt("p_fk_tc_experiencia_convocatoria_ref_tc_convocatoria", convocatoria);
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
	
	public List<Map<String, Object>> getExperienciaConv(Integer convocatoria) throws Exception {
		List<Map<String,Object>> salida = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		CallableStatement call = conn
				.prepareCall("call " + "CIT_BASE" +
						".PKG_TC_EXPERIENCIA_CONVOCATORIA.PROC_MOSTRAR_TC_EXPERIENCIA_CONVOCATORIA(?,?,?)");
		call.setInt("p_id_convocatoria", convocatoria);
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
	
	public jsonResult modExperienciaConv(ExperienciaConv experienciaConv, Integer id) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a modificar experiencia conv......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_EXPERIENCIA_CONVOCATORIA.PROC_ACTUALIZAR_TC_EXPERIENCIA_CONVOCATORIA (?,?,?,?)");
		call.setInt("p_id_experiencia_convocatoria", id);
		call.setString("p_requisito_experiencia", experienciaConv.requisitoExperiencia);
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
	
	public jsonResult modVisibilidadExperienciaConv(Integer id) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a eliminar experiencia conv......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_EXPERIENCIA_CONVOCATORIA.PROC_BORRAR_TC_EXPERIENCIA_CONVOCATORIA (?,?,?)");
		call.setInt("p_id_experiencia_convocatoria", id);
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
