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

import gt.gob.oj.CITBASE.model.Convocatoria;
import gt.gob.oj.utils.Config;
import gt.gob.oj.utils.ConnectionsPool;
import gt.gob.oj.utils.jsonResult;
import oracle.jdbc.OracleTypes;

public class ConvocatoriaManager {
	String SCHEMA = new Config().getDBSchema();
	
	public jsonResult inConvocatoria(Convocatoria convocatoria) throws Exception{
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a insertar convocatoria ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_CONVOCATORIA.PROC_AGREGAR_TC_CONVOCATORIA (?,?,?,?,?,?,?,?)");
		call.setString("p_titulo", convocatoria.titulo);
		call.setString("p_descripcion", convocatoria.descripcion);
		call.setString("p_fecha_inicio_vigencia", convocatoria.fechaInicioVigencia);
		call.setString("p_fecha_fin_vigencia", convocatoria.fechaFinVigencia);
		call.setString("p_objetivo", convocatoria.objetivo);
		call.setString("p_experiencia", convocatoria.experiencia);
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
	
	public List<Map<String, Object>> getConvocatoria() throws Exception {
		List<Map<String,Object>> salida = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		CallableStatement call = conn
				.prepareCall("call " + "CIT_BASE" +
						".PKG_TC_CONVOCATORIA.PROC_MOSTRAR_TC_CONVOCATORIA(?,?)");
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
	
	public jsonResult modConvocatoria(Convocatoria convocatoria, Integer id) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a modificar convocatoria ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_CONVOCATORIA.PROC_ACTUALIZAR_TC_CONVOCATORIA (?,?,?,?,?,?,?,?,?)");
		call.setInt("p_id_convocatoria", id);
		call.setString("p_titulo", convocatoria.titulo);
		call.setString("p_descripcion", convocatoria.descripcion);
		call.setString("p_fecha_inicio_vigencia", convocatoria.fechaInicioVigencia);
		call.setString("p_fecha_fin_vigencia", convocatoria.fechaFinVigencia);
		call.setString("p_objetivo", convocatoria.objetivo);
		call.setString("p_experiencia", convocatoria.experiencia);
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
	
	public jsonResult modVisibilidadConvocatoria(Integer id) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a eliminar convocatoria ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_CONVOCATORIA.PROC_BORRAR_TC_CONVOCATORIA (?,?,?)");
		call.setInt("p_id_convocatoria", id);
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
