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

import gt.gob.oj.CITBASE.manager.ReferenciaPersonalManager;
import gt.gob.oj.CITBASE.model.ReferenciasPersonales;
import gt.gob.oj.utils.jsonResult;

@Path("Referencia-Personal")
public class ReferenciaPersonalController {
	ReferenciaPersonalManager manager = new ReferenciaPersonalManager();
	
	@POST
	@Path("/inReferenciaPersonal/usuario/{usuario}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inReferenciaPersonal(@Context HttpServletRequest req, ReferenciasPersonales referenciaPersonal, @PathParam("usuario") Integer usuario) {
		try {
			return Response.ok(manager.inReferenciaPersonal(referenciaPersonal, usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/modReferenciaPersonal/id/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response modReferenciaPersonal(@Context HttpServletRequest req, ReferenciasPersonales referenciaPersonal, @PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modReferenciaPersonal(referenciaPersonal, id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/getReferenciaPersonal/usuario/{usuario}")
	@Produces("application/json")
	public Response getReferenciaPersonal(@PathParam("usuario") Integer usuario) {
		try {
			return Response.ok(manager.getReferenciaPersonal(usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/borReferenciaPersonal/id/{id}")
	@Produces("application/json")
	public Response borReferenciaPersonal(@PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modVisibilidadReferenciaPersonal(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
}
