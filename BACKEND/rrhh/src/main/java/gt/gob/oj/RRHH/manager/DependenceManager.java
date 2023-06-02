package gt.gob.oj.RRHH.manager;
import org.json.JSONObject;
import gt.gob.oj.utils.Config;

public class DependenceManager {
	
	public String getDependence() throws Exception{	

		String string = "{\r\n" + 
				"    \"data\": [\r\n" + 
				"        {\r\n" + 
				"            \"cargo\": 0,\r\n" + 
				"            \"codigoAsignacion\": 0,\r\n" + 
				"            \"codigo_asignacion\": 0,\r\n" + 
				"            \"colegiado\": 0,\r\n" + 
				"            \"correo\": \"SELEONM@OJ.GOB.GT\",\r\n" + 
				"            \"cui\": 1941307890608,\r\n" + 
				"            \"departamentoId\": 1,\r\n" + 
				"            \"departamentoNacimientoId\": 0,\r\n" + 
				"            \"departamentoNombre\": \"GUATEMALA\",\r\n" + 
				"            \"dependenciaDesc\": \"CENTRO DE INFORMATICA Y TELECOMUNICACIONES\",\r\n" + 
				"            \"dependenciaFuncionesDesc\": \"COORDINACION DE DESARROLLO DE SISTEMAS\",\r\n" + 
				"            \"dependenciaFuncionesId\": 301107,\r\n" + 
				"            \"dependenciaId\": 1019,\r\n" + 
				"            \"empleadoDesc\": \"STEVE EDWOOAR ALEE LEON MELGAR\",\r\n" + 
				"            \"empleadoId\": 28474,\r\n" + 
				"            \"fallecido\": 0,\r\n" + 
				"            \"fechaNacimiento\": \"1989-12-24T00:00:00-06:00\",\r\n" + 
				"            \"municipioId\": 1,\r\n" + 
				"            \"municipioNacimientoId\": 0,\r\n" + 
				"            \"municipioNombre\": \"GUATEMALA\",\r\n" + 
				"            \"nit\": \"57023174\",\r\n" + 
				"            \"plazaDesc\": \"JEFE\",\r\n" + 
				"            \"plazaId\": 13,\r\n" + 
				"            \"primerApellido\": \"LEON\",\r\n" + 
				"            \"primerNombre\": \"STEVE\",\r\n" + 
				"            \"puestoDesc\": \"JEFE DE DEPARTAMENTO\",\r\n" + 
				"            \"puestoId\": 7811,\r\n" + 
				"            \"renglon\": 11,\r\n" + 
				"            \"salarioBase\": 0.0,\r\n" + 
				"            \"segundoApellido\": \"MELGAR\",\r\n" + 
				"            \"segundoNombre\": \"EDWOOAR\",\r\n" + 
				"            \"sexo\": \"M\",\r\n" + 
				"            \"tercerNombre\": \"ALEE\",\r\n" + 
				"            \"usuario\": \"SELEONM\"\r\n" + 
				"        }\r\n" + 
				"    ],\r\n" + 
				"    \"message\": \"Proceso completado con éxito\",\r\n" + 
				"    \"status\": \"OK\"\r\n" + 
				"}";
		
		JSONObject json = new JSONObject(string);
		System.out.println(json);
		return string;
	}
	
}
