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

import gt.gob.oj.CITBASE.model.IdiomasPerfilSE;
//import gt.gob.oj.SIGMA.model.Vinculacion;
import gt.gob.oj.utils.Config;
import gt.gob.oj.utils.ConnectionsPool;
import gt.gob.oj.utils.jsonResult;
import oracle.jdbc.OracleTypes;


public class IdiomaUsuarioManager {
	String SCHEMA = new Config().getDBSchema();

	public List<Map<String, Object>> getIdiomasUsuario(Integer Usuario) throws Exception {
		List<Map<String,Object>> salida = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		CallableStatement call = conn
				.prepareCall("call " + "C##CIT_BASE" + ".PKG_TC_IDIOMA_USUARIO.PROC_MOSTRAR_IDIOMA_USUARIO(?,?,?)");
		call.setInt("P_ID_USUARIO", Usuario);
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
	
	public jsonResult inIdiomaUsuario(IdiomasPerfilSE idiomasUsuario, Integer idioma, Integer usuario) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a insertar idioma usuario ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "C##CIT_BASE"
				+ ".PKG_TC_IDIOMA_USUARIO.PROC_AGREGAR_IDIOMA_USUARIO (?,?,?,?,?,?,?)");
		call.setInt("P_FK_TC_IDIOMA_USUARIO_REF_TC_IDIOMA", idioma);
		call.setInt("P_FK_TC_IDIOMA_USUARIO_REF_TC_INFORMACION_PERSONAL_USUARIO", usuario);
		call.setString("P_HABLA", idiomasUsuario.habla);
		call.setString("P_LEE", idiomasUsuario.lee);
		call.setString("P_ESCRIBE", idiomasUsuario.escribe);
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
	
	public jsonResult modIdiomaUsuario(IdiomasPerfilSE idiomaUsuario, Integer idioma, Integer usuario) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a modificar idioma usuario ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "C##CIT_BASE"
				+ ".PKG_TC_IDIOMA_USUARIO.PROC_ACTUALIZAR_IDIOMA_USUARIO (?,?,?,?,?,?,?)");
		call.setInt("P_FK_TC_IDIOMA_USUARIO_REF_TC_IDIOMA", idioma);
		call.setInt("P_FK_TC_IDIOMA_USUARIO_REF_TC_INFORMACION_PERSONAL_USUARIO", usuario);
		call.setString("P_HABLA", idiomaUsuario.habla);
		call.setString("P_LEE", idiomaUsuario.lee);
		call.setString("P_ESCRIBE", idiomaUsuario.escribe);
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
	
	public jsonResult modVisibilidadIdiomaUsuario(Integer usuario, Integer idioma) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a modificar idioma usuario ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "C##CIT_BASE"
				+ ".PKG_TC_IDIOMA_USUARIO.PROC_BORRRAR_IDIOMA_USUARIO (?,?,?,?)");
		call.setInt("P_FK_TC_IDIOMA_USUARIO_REF_TC_IDIOMA", idioma);
		call.setInt("P_FK_TC_IDIOMA_USUARIO_REF_TC_INFORMACION_PERSONAL_USUARIO", usuario);
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
