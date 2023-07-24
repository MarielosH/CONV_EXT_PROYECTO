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

import gt.gob.oj.CITBASE.manager.OtroConvManager;
import gt.gob.oj.CITBASE.model.OtroConv;
import gt.gob.oj.utils.jsonResult;

@Path("Otro-Conv")
public class OtroConvController {
	OtroConvManager manager = new OtroConvManager();
	
	@POST
	@Path("/inOtroConv/convocatoria/{convocatoria}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inOtroConv(@Context HttpServletRequest req, OtroConv otroConv, @PathParam("convocatoria") Integer convocatoria) {
		try {
			return Response.ok(manager.inOtroConv(otroConv, convocatoria)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/modOtroConv/id/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response modOtroConv(@Context HttpServletRequest req, OtroConv otroConv, @PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modOtroConv(otroConv, id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/getOtroConv/convocatoria/{convocatoria}")
	@Produces("application/json")
	public Response getOtroConv(@PathParam("convocatoria") Integer convocatoria) {
		try {
			return Response.ok(manager.getOtroConv(convocatoria)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/borOtroConv/id/{id}")
	@Produces("application/json")
	public Response borOtroConv(@PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modVisibilidadOtroConv(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
}
