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

import gt.gob.oj.CITBASE.manager.HabilidadHumanaManager;
import gt.gob.oj.CITBASE.model.HabilidadesHumanas;
import gt.gob.oj.utils.jsonResult;

@Path("Habilidad-Humana-Conv")
public class HabilidadesHumanasController {
	HabilidadHumanaManager manager = new HabilidadHumanaManager();
	
	@POST
	@Path("/inHabilidadHumana/convocatoria/{convocatoria}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inHabilidadesHumanas(@Context HttpServletRequest req, HabilidadesHumanas habilidadesHumanas, @PathParam("convocatoria") Integer convocatoria) {
		try {
			return Response.ok(manager.inHabilidadHumana(habilidadesHumanas, convocatoria)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/modHabilidadHumana/id/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response modHabilidadesHumanas(@Context HttpServletRequest req, HabilidadesHumanas habilidadesHumanas, @PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modHabilidadHumana(habilidadesHumanas, id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/getHabilidadHumana/convocatoria/{convocatoria}")
	@Produces("application/json")
	public Response getHabilidadesHumanas(@PathParam("convocatoria") Integer convocatoria) {
		try {
			return Response.ok(manager.getHabilidadHumana(convocatoria)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/borHabilidadHumana/id/{id}")
	@Produces("application/json")
	public Response borHabilidadesHumanas(@PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modVisibilidadHabilidadHumana(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
}
