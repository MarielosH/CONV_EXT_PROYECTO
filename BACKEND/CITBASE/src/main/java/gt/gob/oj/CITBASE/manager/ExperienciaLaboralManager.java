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
import gt.gob.oj.utils.Config;
import gt.gob.oj.utils.ConnectionsPool;
import gt.gob.oj.utils.jsonResult;
import oracle.jdbc.OracleTypes;


public class ExperienciaLaboralManager {
	String SCHEMA = new Config().getDBSchema();
	
	public jsonResult inExperienciaLaboral(ExperienciaLaboral experienciaLaboral, Integer usuario) throws Exception{
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a insertar experiencia laboral ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_EXPERIENCIA_LABORAL.PROC_AGREGAR_TC_EXPERIENCIA_LABORAL (?,?,?,?,?,?,?,?,?,?,?)");
		call.setString("P_INSTITUCION_EMPRESA", experienciaLaboral.institucionEmpresa);
		call.setString("P_FECHA_INICIO", experienciaLaboral.fechaInicio);
		call.setString("P_FECHA_FIN", experienciaLaboral.fechaFinalizacion);
		call.setString("P_RENGLON_PRESUPUESTARIO", experienciaLaboral.renglonPresupuestario);
		call.setString("P_PUESTO", experienciaLaboral.puesto);
		call.setString("P_JEFE_INMEDIATO", experienciaLaboral.jefeInmediato);
		call.setString("P_TELEFONO", experienciaLaboral.telefono);
		call.setString("P_MOTIVO_FIN_RELACION", experienciaLaboral.motivoFinRelacionLaboral);
		call.setInt("P_fk_tc_experiencia_laboral_ref_tc_informacion_personal_usuario", usuario);
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
	
	public List<Map<String, Object>> getExperienciaLaboral(Integer Usuario) throws Exception {
		List<Map<String,Object>> salida = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		CallableStatement call = conn
				.prepareCall("call " + "CIT_BASE" +
						".PKG_TC_EXPERIENCIA_LABORAL.PROC_MOSTRAR_TC_EXPERIENCIA_LABORAL(?,?,?)");
		call.setInt("P_ID_PERSONA", Usuario);
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
	
	public jsonResult modExperienciaLaboral(ExperienciaLaboral experienciaLaboral, Integer id) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a modificar experiencia laboral......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_EXPERIENCIA_LABORAL.PROC_ACTUALIZAR_TC_EXPERIENCIA_LABORAL (?,?,?,?,?,?,?,?,?,?,?)");
		call.setInt("P_ID_EXPERIENCIA_LABORAL", id);
		call.setString("P_INSTITUCION_EMPRESA", experienciaLaboral.institucionEmpresa);
		call.setString("P_FECHA_INICIO", experienciaLaboral.fechaInicio);
		call.setString("P_FECHA_FIN", experienciaLaboral.fechaFinalizacion);
		call.setString("P_RENGLON_PRESUPUESTARIO", experienciaLaboral.renglonPresupuestario);
		call.setString("P_PUESTO", experienciaLaboral.puesto);
		call.setString("P_JEFE_INMEDIATO", experienciaLaboral.jefeInmediato);
		call.setString("P_TELEFONO", experienciaLaboral.telefono);
		call.setString("P_MOTIVO_FIN_RELACION", experienciaLaboral.motivoFinRelacionLaboral);
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
	
	public jsonResult modVisibilidadExperienciaLaboral(Integer id) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a eliminar experiencia laboral ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_EXPERIENCIA_LABORAL.PROC_BORRAR_TC_EXPERIENCIA_LABORAL (?,?,?)");
		call.setInt("P_ID_EXPERIENCIA_LABORAL", id);
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
