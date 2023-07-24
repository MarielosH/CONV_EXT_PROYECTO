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

import gt.gob.oj.CITBASE.manager.PapeleriaManager;
import gt.gob.oj.CITBASE.model.Papeleria;
import gt.gob.oj.utils.jsonResult;

@Path("Papeleria-Conv")
public class PapeleriaController {
	PapeleriaManager manager = new PapeleriaManager();
	
	@POST
	@Path("/in/convocatoria/{convocatoria}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inPapeleria(@Context HttpServletRequest req, Papeleria papeleria, @PathParam("convocatoria") Integer convocatoria) {
		try {
			return Response.ok(manager.inPapeleria(papeleria, convocatoria)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/mod/id/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response modPapeleria(@Context HttpServletRequest req, Papeleria papeleria, @PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modPapeleria(papeleria, id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/get/convocatoria/{convocatoria}")
	@Produces("application/json")
	public Response getPapeleria(@PathParam("convocatoria") Integer convocatoria) {
		try {
			return Response.ok(manager.getPapeleria(convocatoria)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/bor/id/{id}")
	@Produces("application/json")
	public Response borPapeleria(@PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modVisibilidadPapeleria(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
}
