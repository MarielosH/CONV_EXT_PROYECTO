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

import gt.gob.oj.CITBASE.manager.IdiomaUsuarioManager;
import gt.gob.oj.CITBASE.model.IdiomasPerfilSE;
import gt.gob.oj.CITBASE.model.PerfilSolicitudEmpleo;
import gt.gob.oj.utils.jsonResult;

@Path("Idiomas-Usuario")
public class IdiomaPerfilController {
	IdiomaUsuarioManager manager = new IdiomaUsuarioManager();

	@POST
	// @Authorize
	@Path("/inIdiomaUsuario")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inPerfilSolicitudEmpleo(@Context HttpServletRequest req, IdiomasPerfilSE idiomaUsuario) {
		try {
			return Response.ok(manager.inIdiomaUsuario(idiomaUsuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}

	@POST
	// @Authorize
	@Path("/modIdiomaUsuario")
	@Consumes("application/json")
	@Produces("application/json")
	public Response modIdioma(@Context HttpServletRequest req, IdiomasPerfilSE idiomaUsuario) {
		try {
			return Response.ok(manager.modIdiomaUsuario(idiomaUsuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/getIdiomasUsuario/{usuario}")
	@Produces("application/json")
	public Response getIdiomas(@PathParam("usuario") Integer usuario) {
		try {
			return Response.ok(manager.getIdiomasUsuario(usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/modIdiomasUsuario/{usuario}/{idioma}")
	@Produces("application/json")
	public Response borIdioma(@PathParam("usuario") Integer usuario, @PathParam("idioma") Integer idioma) {
		try {
			return Response.ok(manager.modVisibilidadIdiomaUsuario(usuario, idioma)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
}
