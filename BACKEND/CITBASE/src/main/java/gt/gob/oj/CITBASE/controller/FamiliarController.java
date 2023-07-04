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

import gt.gob.oj.CITBASE.manager.FamiliarManager;
import gt.gob.oj.CITBASE.model.FamiliaPerfilSE;
import gt.gob.oj.utils.jsonResult;

@Path("Familiar")
public class FamiliarController {

	FamiliarManager manager = new FamiliarManager();
	
	@GET
	@Path("/getFamiliares/usuario/{usuario}")
	@Produces("application/json")
	public Response getFamiliar(@PathParam("usuario") Integer usuario) {
		try {
			return Response.ok(manager.getFamiliares(usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/inFamiliar/usuario/{usaurio}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inFamiliar(@Context HttpServletRequest req, @PathParam("usuario") Integer usuario, FamiliaPerfilSE familiar) {
		try {
			return Response.ok(manager.inFamiliar(familiar, usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}

	@POST
	@Path("/modFamiliar/id/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response modFamiliar(@Context HttpServletRequest req, @PathParam("id") Integer id,FamiliaPerfilSE familiar) {
		try {
			return Response.ok(manager.modFamiliar(familiar, id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/borFamiliar/id/{id}")
	@Produces("application/json")
	public Response borFamiliar(@PathParam("id") Integer id) {
		try {
			return Response.ok(manager.borFamiliar(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
}
