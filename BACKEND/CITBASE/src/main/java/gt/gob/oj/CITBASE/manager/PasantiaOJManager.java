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

import gt.gob.oj.CITBASE.model.PasantiasOJ;
import gt.gob.oj.utils.Config;
import gt.gob.oj.utils.ConnectionsPool;
import gt.gob.oj.utils.jsonResult;
import oracle.jdbc.OracleTypes;

public class PasantiaOJManager {
	String SCHEMA = new Config().getDBSchema();
	
	public jsonResult inPasantiaOJ(PasantiasOJ pasantiaOJ, Integer usuario) throws Exception{
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a insertar pasantia OJ ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_PASANTIA_OJ.PROC_AGREGAR_TC_PASANTIA_OJ (?,?,?,?,?,?,?,?)");
		call.setString("P_FECHA_INICIO", pasantiaOJ.fechaInicio);
		call.setString("P_FECHA_FIN", pasantiaOJ.fechaFinalizacion);
		call.setString("P_DEPENDENCIA", pasantiaOJ.dependencia);
		call.setString("P_SECRETARIO_O_JUEZ", pasantiaOJ.secretarioJuez);
		call.setString("P_REGISTRO_PASANTIA", pasantiaOJ.registrada);
		call.setInt("P_FK_TC_PASANTIA_OJ_REF_TC_INFORMACION_PERSONAL_USUARIO", usuario);
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
	
	public List<PasantiasOJ> getPasantiaOJ(Integer usuario) throws Exception {
		List<PasantiasOJ> listaPasantias = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		CallableStatement call = conn
				.prepareCall("call " + "CIT_BASE" + ".PKG_TC_PASANTIA_OJ.PROC_MOSTRAR_TC_PASANTIA_OJ(?,?,?)");
		call.setInt("P_ID_PERSONA", usuario);
		call.registerOutParameter("P_CUR_DATASET", OracleTypes.CURSOR);
		call.registerOutParameter("P_MSJ", OracleTypes.VARCHAR);
		call.execute();
		ResultSet rset = (ResultSet) call.getObject("P_CUR_DATASET");
		ResultSetMetaData meta = rset.getMetaData();
		while (rset.next()) {
			PasantiasOJ nuevaPasantia = new PasantiasOJ();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String key = meta.getColumnName(i).toString();
				String value = Objects.toString(rset.getString(key), "");
				switch (key) {
				case "FECHA_INICIO":
					nuevaPasantia.fechaInicio = value;
					break;
				case "FECHA_FIN":
					nuevaPasantia.fechaFinalizacion = value;
					break;
				case "DEPENDENCIA":
					nuevaPasantia.dependencia = value;
					break;
				case "SECRETARIO_O_JUEZ":
					nuevaPasantia.secretarioJuez = value;
					break;
				case "REGISTRO_PASANTIA":
					nuevaPasantia.registrada = value;
					break;
				case "MOSTRAR":
					nuevaPasantia.mostrar = value;
					break;
				}
			}
			listaPasantias.add(nuevaPasantia);
		}
		rset.close();
		call.close();
		conn.close();

		return listaPasantias;
	}
	
	public jsonResult modPasantiaOJ(PasantiasOJ pasantiaOJ, Integer id) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a modificar pasantia OJ ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_PASANTIA_OJ.PROC_ACTUALIZAR_TC_PASANTIA_OJ (?,?,?,?,?,?,?,?)");
		call.setInt("P_ID_PASANTIA_OJ", id);
		call.setString("P_FECHA_INICIO", pasantiaOJ.fechaInicio);
		call.setString("P_FECHA_FIN", pasantiaOJ.fechaFinalizacion);
		call.setString("P_DEPENDENCIA", pasantiaOJ.dependencia);
		call.setString("P_SECRETARIO_O_JUEZ", pasantiaOJ.secretarioJuez);
		call.setString("P_REGISTRO_PASANTIA", pasantiaOJ.registrada);
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
	
	public jsonResult modVisibilidadPasantiaOJ(Integer id) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a eliminar pasantia OJ ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_PASANTIA_OJ.PROC_BORRAR_TC_PASANTIA_OJ (?,?,?)");
		call.setInt("P_ID_PASANTIA_OJ", id);
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
