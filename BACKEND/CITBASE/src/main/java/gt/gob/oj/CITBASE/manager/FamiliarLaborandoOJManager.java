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

import gt.gob.oj.CITBASE.model.FamiliaresLaborandoOJ;
import gt.gob.oj.utils.Config;
import gt.gob.oj.utils.ConnectionsPool;
import gt.gob.oj.utils.jsonResult;
import oracle.jdbc.OracleTypes;

public class FamiliarLaborandoOJManager {
	String SCHEMA = new Config().getDBSchema();
	
	public jsonResult inFamiliarLaborandoOJ(FamiliaresLaborandoOJ familiarLaborandoOJ, Integer usuario) throws Exception{
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a insertar familiar laborando OJ ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_FAMILIAR_LABORANDO_OJ.PROC_AGREGAR_TC_FAMILIAR_LABORANDO_OJ (?,?,?,?,?,?,?)");
		call.setString("P_NOMBRE_COMPLETO", familiarLaborandoOJ.nombreCompleto);
		call.setString("P_PARENTESCO", familiarLaborandoOJ.parentesco);
		call.setString("P_DEPENDENCIA", familiarLaborandoOJ.dependencia);
		call.setString("P_PUESTO", familiarLaborandoOJ.puesto);
		call.setInt("P_FK_TC_FAMILIAR_LABORANDO_OJ_REF_TC_INFORMACION_PERSONAL_USUARIO", usuario);
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
	
	public List<FamiliaresLaborandoOJ> getFamiliarLaborandoOJ(Integer usuario) throws Exception {
		List<FamiliaresLaborandoOJ> familiaresLaborandoOJ = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		CallableStatement call = conn
				.prepareCall("call " + "CIT_BASE" +
						".PKG_TC_FAMILIAR_LABORANDO_OJ.PROC_MOSTRAR_TC_FAMILIARES_LABORANDO_OJ(?,?,?)");
		call.setInt("P_ID_PERSONA", usuario);
		call.registerOutParameter("P_CUR_DATASET", OracleTypes.CURSOR);
		call.registerOutParameter("P_MSJ", OracleTypes.VARCHAR);
		call.execute();
		ResultSet rset = (ResultSet) call.getObject("P_CUR_DATASET");
		ResultSetMetaData meta = rset.getMetaData();
		while (rset.next()) {
			FamiliaresLaborandoOJ nuevoFamiliarLaborandoOJ = new FamiliaresLaborandoOJ();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String key = meta.getColumnName(i).toString();
				String value = Objects.toString(rset.getString(key), "");
				switch (key) {
				case "NOMBRE_COMPLETO":
					nuevoFamiliarLaborandoOJ.nombreCompleto = value;
					break;
				case "PARENTESCO":
					nuevoFamiliarLaborandoOJ.parentesco = value;					
					break;
				case "DEPENDENCIA":
					nuevoFamiliarLaborandoOJ.dependencia = value;					
					break;
				case "PUESTO":
					nuevoFamiliarLaborandoOJ.puesto = value;	
					break;
				case "MOSTRAR":
					nuevoFamiliarLaborandoOJ.mostrar = value;	
					break;
				}

			}
			familiaresLaborandoOJ.add(nuevoFamiliarLaborandoOJ);
		}
		rset.close();
		call.close();
		conn.close();
		
		return familiaresLaborandoOJ;
	}
	
	public jsonResult modFamiliarLaborandoOJ(FamiliaresLaborandoOJ familiarLaborandoOJ, Integer id) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a modificar  familiar Laborando OJ ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_FAMILIAR_LABORANDO_OJ.PROC_ACTUALIZAR_TC_FAMILIAR_LABORANDO_OJ (?,?,?,?,?,?,?)");
		call.setInt("P_ID_FAMILIAR_LABORANDO_OJ", id);
		call.setString("P_NOMBRE_COMPLETO", familiarLaborandoOJ.nombreCompleto);
		call.setString("P_PARENTESCO", familiarLaborandoOJ.parentesco);
		call.setString("P_DEPENDENCIA", familiarLaborandoOJ.dependencia);
		call.setString("P_PUESTO", familiarLaborandoOJ.puesto);
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
	
	public jsonResult modVisibilidadFamiliarLaborandoOJ(Integer id) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a eliminar familiar laborando OJ ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "CIT_BASE"
				+ ".PKG_TC_FAMILIAR_LABORANDO_OJ.PROC_BORRAR_TC_FAMILIAR_LABORANDO_OJ (?,?,?)");
		call.setInt("P_ID_FAMILIAR_LABORANDO_OJ", id);
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
