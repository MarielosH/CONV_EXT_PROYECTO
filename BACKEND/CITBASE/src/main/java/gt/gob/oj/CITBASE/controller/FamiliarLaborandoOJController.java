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

import gt.gob.oj.CITBASE.manager.FamiliarLaborandoOJManager;
import gt.gob.oj.CITBASE.model.FamiliaresLaborandoOJ;
import gt.gob.oj.utils.jsonResult;

@Path("Familiar-Laborando-OJ")
public class FamiliarLaborandoOJController {
	FamiliarLaborandoOJManager manager = new FamiliarLaborandoOJManager();
	
	@POST
	@Path("/inFamiliarLaborandoOJ/usuario/{usuario}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inFamiliarLaborandoOJ(@Context HttpServletRequest req, FamiliaresLaborandoOJ familiarLaborandoOJ, @PathParam("usuario") Integer usuario) {
		try {
			return Response.ok(manager.inFamiliarLaborandoOJ(familiarLaborandoOJ, usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/modFamiliarLaborandoOJ/id/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response modFamiliarLaborandoOJ(@Context HttpServletRequest req, FamiliaresLaborandoOJ familiarLaborandoOJ, @PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modFamiliarLaborandoOJ(familiarLaborandoOJ, id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/getFamiliarLaborandoOJ/usuario/{usuario}")
	@Produces("application/json")
	public Response getFamiliarLaborandoOJ(@PathParam("usuario") Integer usuario) {
		try {
			return Response.ok(manager.getFamiliarLaborandoOJ(usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/borFamiliarLaborandoOJ/id/{id}")
	@Produces("application/json")
	public Response borFamiliarLaborandoOJ(@PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modVisibilidadFamiliarLaborandoOJ(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
}
