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

import gt.gob.oj.CITBASE.manager.ExperienciaConvManager;
import gt.gob.oj.CITBASE.model.ExperienciaConv;
import gt.gob.oj.utils.jsonResult;

@Path("Experiencia-Conv")
public class ExperienciaConvController {
	ExperienciaConvManager manager = new ExperienciaConvManager();
	
	@POST
	@Path("/in/convocatoria/{convocatoria}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inExperienciaConv(@Context HttpServletRequest req, ExperienciaConv experienciaConv, @PathParam("convocatoria") Integer convocatoria) {
		try {
			return Response.ok(manager.inExperienciaConv(experienciaConv, convocatoria)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/mod/id/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response modExperienciaConv(@Context HttpServletRequest req, ExperienciaConv experienciaConv, @PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modExperienciaConv(experienciaConv, id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/get/convocatoria/{convocatoria}")
	@Produces("application/json")
	public Response getExperienciaConv(@PathParam("convocatoria") Integer convocatoria) {
		try {
			return Response.ok(manager.getExperienciaConv(convocatoria)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/bor/id/{id}")
	@Produces("application/json")
	public Response borExperienciaConv(@PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modVisibilidadExperienciaConv(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
}
