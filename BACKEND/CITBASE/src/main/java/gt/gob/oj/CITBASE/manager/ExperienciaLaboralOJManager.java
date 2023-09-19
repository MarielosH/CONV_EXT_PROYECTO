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

import gt.gob.oj.CITBASE.model.ExperienciaLaboralOJ;
import gt.gob.oj.utils.Config;
import gt.gob.oj.utils.ConnectionsPool;
import gt.gob.oj.utils.jsonResult;
import oracle.jdbc.OracleTypes;

public class ExperienciaLaboralOJManager {
	String SCHEMA = new Config().getDBSchema();
	
	public jsonResult inExperienciaLaboralOJ(ExperienciaLaboralOJ experienciaLaboralOJ, Integer usuario) throws Exception{
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a insertar experiencia laboral OJ  ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_EXPERIENCIA_LABORAL_OJ.PROC_AGREGAR_TC_EXPERIENCIA_LABORAL_OJ (?,?,?,?,?,?,?,?,?,?)");
		call.setString("P_PUESTO", experienciaLaboralOJ.puesto);
		call.setString("P_FECHA_INICIO", experienciaLaboralOJ.fechaInicio);
		call.setString("P_FECHA_FIN", experienciaLaboralOJ.fechaFinalizacion);
		System.out.println("fecha inicio experiencia laboral OJ ......" + experienciaLaboralOJ.fechaInicio + "\n");
		System.out.println("fecha fin experiencia laboral OJ ......" + experienciaLaboralOJ.fechaFinalizacion + "\n");
		call.setString("P_RENGLON", experienciaLaboralOJ.renglonPresupuestario);
		call.setString("P_DEPENDENCIA", experienciaLaboralOJ.dependencia);
		call.setString("P_JEFE_INMEDIATO", experienciaLaboralOJ.jefeInmediato);
		call.setString("P_MOTIVO_FIN", experienciaLaboralOJ.motivoFinRelacionLaboral);
		call.setInt("P_FK_TC_EXPERIENCIA_LABORAL_OJ_REF_TC_INFORMACION_PERSONAL_USUARIO", usuario);
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
	
	public List<ExperienciaLaboralOJ> getExperienciaLaboralOJ(Integer usuario) throws Exception {
		List<ExperienciaLaboralOJ> listaExperienciaLaboralOj = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		CallableStatement call = conn.prepareCall(
				"call " + "CIT_BASE" + ".PKG_TC_EXPERIENCIA_LABORAL_OJ.PROC_MOSTRAR_TC_EXPERIENCIA_LABORAL_OJ(?,?,?)");
		call.setInt("P_ID_PERSONA", usuario);
		call.registerOutParameter("P_CUR_DATASET", OracleTypes.CURSOR);
		call.registerOutParameter("P_MSJ", OracleTypes.VARCHAR);
		call.execute();
		ResultSet rset = (ResultSet) call.getObject("P_CUR_DATASET");
		ResultSetMetaData meta = rset.getMetaData();
		while (rset.next()) {
			ExperienciaLaboralOJ nuevaExperienciaOJ = new ExperienciaLaboralOJ();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String key = meta.getColumnName(i).toString();
				String value = Objects.toString(rset.getString(key), "");
				switch (key) {
				case "ID_EXPERIENCIA_LABORAL_OJ":
					nuevaExperienciaOJ.id = Integer.parseInt(value);
					break;
				case "PUESTO":
					nuevaExperienciaOJ.puesto = value;
					break;
				case "FECHA_INICIO":
					nuevaExperienciaOJ.fechaInicio = value;
					break;
				case "FECHA_FIN":
					nuevaExperienciaOJ.fechaFinalizacion = value;
					break;
				case "RENGLON":
					nuevaExperienciaOJ.renglonPresupuestario = value;
				case "DEPENDENCIA":
					nuevaExperienciaOJ.dependencia = value;
				case "JEFE_INMEDIATO":
					nuevaExperienciaOJ.jefeInmediato = value;
				case "MOTIVO_FIN":
					nuevaExperienciaOJ.motivoFinRelacionLaboral = value;
					break;
				case "MOSTRAR":
					nuevaExperienciaOJ.mostrar = value;
					break;
				}
			}
			listaExperienciaLaboralOj.add(nuevaExperienciaOJ);
		}
		rset.close();
		call.close();
		conn.close();

		return listaExperienciaLaboralOj;
	}
	
	public jsonResult modExperienciaLaboralOJ(ExperienciaLaboralOJ experienciaLaboralOJ, Integer id) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a modificar experiencia laboral OJ ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_EXPERIENCIA_LABORAL_OJ.PROC_ACTUALIZAR_TC_EXPERIENCIA_LABORAL_OJ (?,?,?,?,?,?,?,?,?,?)");
		call.setInt("P_ID_EXPERIENCIA_LABORAL_OJ", id);
		call.setString("P_PUESTO", experienciaLaboralOJ.puesto);
		call.setString("P_FECHA_INICIO", experienciaLaboralOJ.fechaInicio);
		call.setString("P_FECHA_FIN", experienciaLaboralOJ.fechaFinalizacion);
		call.setString("P_RENGLON", experienciaLaboralOJ.renglonPresupuestario);
		call.setString("P_DEPENDENCIA", experienciaLaboralOJ.dependencia);
		call.setString("P_JEFE_INMEDIATO", experienciaLaboralOJ.jefeInmediato);
		call.setString("P_MOTIVO_FIN", experienciaLaboralOJ.motivoFinRelacionLaboral);
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
	
	public jsonResult modVisibilidadExperienciaLaboralOJ(Integer id) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a eliminar experiencia laboral OJ ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_EXPERIENCIA_LABORAL_OJ.PROC_BORRAR_TC_EXPERIENCIA_LABORAL_OJ (?,?,?)");
		call.setInt("P_ID_EXPERIENCIA_LABORAL_OJ", id);
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
