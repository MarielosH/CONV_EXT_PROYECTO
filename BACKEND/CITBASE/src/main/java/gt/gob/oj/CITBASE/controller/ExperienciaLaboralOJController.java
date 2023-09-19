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

import gt.gob.oj.CITBASE.manager.ExperienciaLaboralOJManager;
import gt.gob.oj.CITBASE.model.ExperienciaLaboralOJ;
import gt.gob.oj.utils.jsonResult;

@Path("Experiencia-Laboral-OJ")
public class ExperienciaLaboralOJController {
	ExperienciaLaboralOJManager manager = new ExperienciaLaboralOJManager();
	
	@POST
	@Path("/inExperienciaLaboralOJ/usuario/{usuario}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inExperienciaLaboralOJ(@Context HttpServletRequest req, ExperienciaLaboralOJ experienciaLaboralOJ, @PathParam("usuario") Integer usuario) {
		try {
			return Response.ok(manager.inExperienciaLaboralOJ(experienciaLaboralOJ, usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/modExperienciaLaboralOJ/id/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response modIdioma(@Context HttpServletRequest req, ExperienciaLaboralOJ experienciaLaboralOJ, @PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modExperienciaLaboralOJ(experienciaLaboralOJ, id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/getExperienciaLaboralOJ/usuario/{usuario}")
	@Produces("application/json")
	public Response getIdiomas(@PathParam("usuario") Integer usuario) {
		try {
			return Response.ok(manager.getExperienciaLaboralOJ(usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/borExperienciaLaboralOJ/id/{id}")
	@Produces("application/json")
	public Response borIdioma(@PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modVisibilidadExperienciaLaboralOJ(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
}
