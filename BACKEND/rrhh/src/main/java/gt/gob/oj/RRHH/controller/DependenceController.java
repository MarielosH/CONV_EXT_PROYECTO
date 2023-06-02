package gt.gob.oj.RRHH.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import gt.gob.oj.RRHH.manager.DependenceManager;
import gt.gob.oj.utils.jsonResult;

@Path("catalogo")
public class DependenceController {
	DependenceManager manager = new DependenceManager();
	
	@GET
	//@Authorize
	@Path("empleado/{Id_empleado}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDependence() {	
		try {
			return Response.ok(manager.getDependence()).build();
		} catch (Exception e) {
			e.printStackTrace(); 
			return Response.ok(new jsonResult(-1,"Error",e.getMessage())).build();
		}
	}


}
