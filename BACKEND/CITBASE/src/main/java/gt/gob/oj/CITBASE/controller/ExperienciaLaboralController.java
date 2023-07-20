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

import gt.gob.oj.CITBASE.manager.ExperienciaLaboralManager;
import gt.gob.oj.CITBASE.model.ExperienciaLaboral;
import gt.gob.oj.utils.jsonResult;

@Path("Experiencia-Laboral")
public class ExperienciaLaboralController {
	ExperienciaLaboralManager manager = new ExperienciaLaboralManager();
	
	@POST
	@Path("/inExperienciaLaboral/usuario/{usuario}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inExperienciaLaboral(@Context HttpServletRequest req, ExperienciaLaboral experienciaLaboral, @PathParam("usuario") Integer usuario) {
		try {
			return Response.ok(manager.inExperienciaLaboral(experienciaLaboral, usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/modExperienciaLaboral/id/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response modIdioma(@Context HttpServletRequest req, ExperienciaLaboral experienciaLaboral, @PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modExperienciaLaboral(experienciaLaboral, id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/getExperienciaLaboral/usuario/{usuario}")
	@Produces("application/json")
	public Response getIdiomas(@PathParam("usuario") Integer usuario) {
		try {
			return Response.ok(manager.getExperienciaLaboral(usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/borIdiomasUsuario/id/{id}")
	@Produces("application/json")
	public Response borIdioma(@PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modVisibilidadExperienciaLaboral(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
}
