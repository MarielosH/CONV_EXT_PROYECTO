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

import gt.gob.oj.CITBASE.manager.HabilidadTecnicaManager;
import gt.gob.oj.CITBASE.model.HabilidadTecnica;
import gt.gob.oj.utils.jsonResult;

@Path("Habilidad-Tecnica-Conv")
public class HabilidadTecnicaController {
	HabilidadTecnicaManager manager = new HabilidadTecnicaManager();
	
	@POST
	@Path("/inHabilidadTecnica/convocatoria/{convocatoria}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inHabilidadTecnica(@Context HttpServletRequest req, HabilidadTecnica HabilidadTecnica, @PathParam("convocatoria") Integer convocatoria) {
		try {
			return Response.ok(manager.inHabilidadTecnica(HabilidadTecnica, convocatoria)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/modHabilidadTecnica/id/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response modHabilidadTecnica(@Context HttpServletRequest req, HabilidadTecnica HabilidadTecnica, @PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modHabilidadTecnica(HabilidadTecnica, id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/getHabilidadTecnica/convocatoria/{convocatoria}")
	@Produces("application/json")
	public Response getHabilidadTecnica(@PathParam("convocatoria") Integer convocatoria) {
		try {
			return Response.ok(manager.getHabilidadTecnica(convocatoria)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/borHabilidadTecnica/id/{id}")
	@Produces("application/json")
	public Response borHabilidadTecnica(@PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modVisibilidadHabilidadTecnica(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
}
