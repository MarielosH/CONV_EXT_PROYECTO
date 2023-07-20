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

import gt.gob.oj.CITBASE.manager.PasantiaOJManager;
import gt.gob.oj.CITBASE.model.PasantiasOJ;
import gt.gob.oj.utils.jsonResult;

@Path("Pasantia-OJ")
public class PasantiaOJController {
	PasantiaOJManager manager = new PasantiaOJManager();
	
	@POST
	@Path("/inPasantiaOJ/usuario/{usuario}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inPasantiaOJ(@Context HttpServletRequest req, PasantiasOJ pasantiaOJ, @PathParam("usuario") Integer usuario) {
		try {
			return Response.ok(manager.inPasantiaOJ(pasantiaOJ, usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/modPasantiaOJ/id/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response modPasantiaOJ(@Context HttpServletRequest req, PasantiasOJ pasantiaOJ, @PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modPasantiaOJ(pasantiaOJ, id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/getPasantiaOJ/usuario/{usuario}")
	@Produces("application/json")
	public Response getPasantiaOJ(@PathParam("usuario") Integer usuario) {
		try {
			return Response.ok(manager.getPasantiaOJ(usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/borPasantiaOJ/id/{id}")
	@Produces("application/json")
	public Response borPasantiaOJ(@PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modVisibilidadPasantiaOJ(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
}
