package gt.gob.oj.CITBASE.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import gt.gob.oj.CITBASE.manager.ConvocatoriaManager;
import gt.gob.oj.CITBASE.model.Convocatoria;
import gt.gob.oj.utils.jsonResult;

@Path("Convocatoria")
public class ConvocatoriaController {
	ConvocatoriaManager manager = new ConvocatoriaManager();
	
	@POST
	@Path("/inConvocatoria")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inConvocatoria(@Context HttpServletRequest req, Convocatoria convocatoria) {
		try {
			return Response.ok(manager.inConvocatoria(convocatoria)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/modConvocatoria/id/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response modConvocatoria(@Context HttpServletRequest req, Convocatoria convocatoria, @PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modConvocatoria(convocatoria, id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/getConvocatorias")
	@Produces("application/json")
	public Response getConvocatoria() {
		try {
			return Response.ok(manager.getConvocatoria()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/borConvocatoria/id/{id}")
	@Produces("application/json")
	public Response borConvocatoria(@PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modVisibilidadConvocatoria(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
}
