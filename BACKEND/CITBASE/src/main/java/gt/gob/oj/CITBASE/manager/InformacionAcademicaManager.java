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

import gt.gob.oj.CITBASE.model.InformacionAcademica;
import gt.gob.oj.utils.Config;
import gt.gob.oj.utils.ConnectionsPool;
import gt.gob.oj.utils.jsonResult;
import oracle.jdbc.OracleTypes;


public class InformacionAcademicaManager {
	String SCHEMA = new Config().getDBSchema();

	public jsonResult inInformacionAcademica(InformacionAcademica informacionAcademica) throws Exception{
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a insertar informacion academica ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "C##CIT_BASE"
				+ ".PKG_TC_INFORMACION_ACADEMICA.PROC_AGREGAR_TC_INFORMACION_ACADEMICA (?,?,?,?,?,?,?,?,?)");
		call.setString("P_NIVEL_ACADEMICO", informacionAcademica.nivelAcademico);
		call.setString("P_GRADO_APROBADO", informacionAcademica.gradoAprobado);
		call.setString("P_INSTITUCION_ESTUDIO", informacionAcademica.institucionEstudio);
		call.setString("P_CONTANCIA", informacionAcademica.constancia);
		call.setString("P_ANIO_GRADUACION", informacionAcademica.anioGraduacion);
		call.setString("P_CARRERA", informacionAcademica.carrera);
		call.setString("P_FK_TC_INFORMACION_ACADEMICA_REF_TC_INFORMACION_PERSONAL_USUARIO", informacionAcademica.usuario);
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
	
	public List<Map<String, Object>> getInformacionAcademica(Integer Usuario) throws Exception {
		List<Map<String,Object>> salida = new ArrayList<>();
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		CallableStatement call = conn
				.prepareCall("call " + "C##CIT_BASE" +
						".PKG_TC_INFORMACION_ACADEMICA.PROC_MOSTRAR_TC_INFORMACION_ACADEMICA(?,?,?)");
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
	
	public jsonResult modInformacionAcademica(InformacionAcademica informacionAcademica) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a modificar informacion academica ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "C##CIT_BASE"
				+ ".PKG_TC_INFORMACION_ACADEMICA.PROC_ACTUALIZAR_TC_INFORMACION_ACADEMICA (?,?,?,?,?,?,?,?,?)");
		call.setString("P_ID_INFORMACION_ACADEMICA", informacionAcademica.informacionAcademica);
		call.setString("P_NIVEL_ACADEMICO", informacionAcademica.nivelAcademico);
		call.setString("P_GRADO_APROBADO", informacionAcademica.gradoAprobado);
		call.setString("P_INSTITUCION_ESTUDIO", informacionAcademica.institucionEstudio);
		call.setString("P_CONTANCIA", informacionAcademica.constancia);
		call.setString("P_ANIO_GRADUACION", informacionAcademica.anioGraduacion);
		call.setString("P_CARRERA", informacionAcademica.carrera);
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
	
	public jsonResult modVisibilidadInformacionAcademica(Integer usuario) throws Exception {
		ConnectionsPool c = new ConnectionsPool();
		Connection conn = c.conectar();
		jsonResult salida = new jsonResult();
		System.out.println("dentro de llamar a eliminar informacion academica ......" + this.SCHEMA + "\n");
		CallableStatement call = conn.prepareCall("call " + "C##CIT_BASE"
				+ ".PKG_TC_INFORMACION_ACADEMICA.PROC_BORRAR_TC_INFORMACION_ACADEMICA (?,?,?)");
		call.setInt("P_ID_INFORMACION_ACADEMICA", usuario);
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
