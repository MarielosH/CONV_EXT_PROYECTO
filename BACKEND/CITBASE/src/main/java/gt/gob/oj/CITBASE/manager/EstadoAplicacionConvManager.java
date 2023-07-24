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

import gt.gob.oj.CITBASE.model.EstadoAplicacionConv;
import gt.gob.oj.utils.Config;
import gt.gob.oj.utils.ConnectionsPool;
import gt.gob.oj.utils.jsonResult;
import oracle.jdbc.OracleTypes;


public class EstadoAplicacionConvManager {
	String SCHEMA = new Config().getDBSchema();
	
	public jsonResult inEstadoAplicacionConv(EstadoAplicacionConv estadoAplicacionConv, Integer usuario, Integer convocatoria) throws Exception{
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a insertar otro conv......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "C##CIT_BASE"
				+ ".PKG_TC_ESTADO_APLICACION_CONV.PROC_AGREGAR_TC_ESTADO_APLICACION_CONV (?,?,?,?,?)");
		call.registerOutParameter("p_id_salida", OracleTypes.NUMBER);
		call.setInt("P_FK_TC_ESTADO_APLICACION_CONV_REF_TC_INFORMACION_PERSONAL_USUARIO", usuario);
		call.setInt("P_FK_TC_ESTADO_APLICACION_CONV_REF_TC_CONV", convocatoria);
		call.setString("P_ESTADO", estadoAplicacionConv.estado);
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
	
	public List<Map<String, Object>> getEstadoAplicacionConv(Integer usuario) throws Exception {
		List<Map<String,Object>> salida = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		CallableStatement call = conn
				.prepareCall("call " + "C##CIT_BASE" +
						".PKG_TC_ESTADO_APLICACION_CONV.PROC_MOSTRAR_TC_ESTADO_APLICACION_CONV_FILTER_USUARIO(?,?,?)");
		call.setInt("p_id_usuario", usuario);
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
				map.put(key, value);
			}
			salida.add(map);
		}
		rset.close();
		call.close();
		conn.close();
		
		return salida;
	}
	
	public jsonResult modEstadoAplicacionConv(EstadoAplicacionConv estadoAplicacionConv, Integer id) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a modificar otro conv......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "C##CIT_BASE"
				+ ".PKG_TC_ESTADO_APLICACION_CONV.PROC_ACTUALIZAR_TC_ESTADO_APLICACION_CONV (?,?,?,?)");
		call.registerOutParameter("p_id_salida", OracleTypes.NUMBER);
		call.setInt("P_ID_APLICACION_CONVOCATORIA", id);
		call.setString("P_ESTADO", estadoAplicacionConv.estado);
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
	
	public jsonResult modVisibilidadEstadoAplicacionConv(Integer id) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a eliminar otro conv......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "C##CIT_BASE"
				+ ".PKG_TC_ESTADO_APLICACION_CONV.PROC_BORRAR_TC_ESTADO_APLICACION_CONV (?,?,?)");
		call.setInt("P_ID_APLICACION_CONVOCATORIA", id);
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
