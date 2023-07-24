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

import gt.gob.oj.CITBASE.manager.EducacionConvManager;
import gt.gob.oj.CITBASE.model.EducacionConv;
import gt.gob.oj.utils.jsonResult;

@Path("Educacion-Conv")
public class EducacionConvController {
	EducacionConvManager manager = new EducacionConvManager();
	
	@POST
	@Path("/in/convocatoria/{convocatoria}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inEducacionConv(@Context HttpServletRequest req, EducacionConv educacionConv, @PathParam("convocatoria") Integer convocatoria) {
		try {
			return Response.ok(manager.inEducacionConv(educacionConv, convocatoria)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/mod/id/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response modEducacionConv(@Context HttpServletRequest req, EducacionConv educacionConv, @PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modEducacionConv(educacionConv, id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/get/convocatoria/{convocatoria}")
	@Produces("application/json")
	public Response getEducacionConv(@PathParam("convocatoria") Integer convocatoria) {
		try {
			return Response.ok(manager.getEducacionConv(convocatoria)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/bor/id/{id}")
	@Produces("application/json")
	public Response borEducacionConv(@PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modVisibilidadEducacionConv(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
}
