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

import gt.gob.oj.CITBASE.manager.EstadoAplicacionConvManager;
import gt.gob.oj.CITBASE.model.EstadoAplicacionConv;
import gt.gob.oj.utils.jsonResult;

@Path("Estado-Aplicacion-Conv")
public class EstadoAplicacionConvController {
	EstadoAplicacionConvManager manager = new EstadoAplicacionConvManager();
	
	@POST
	@Path("/inEstadoAplicacionConv/usuario/{usuario}/convocatoria/{convocatoria}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inEstadoAplicacionConv(@Context HttpServletRequest req, EstadoAplicacionConv estadoAplicacionConv, @PathParam("usuario") Integer usuario, @PathParam("convocatoria") Integer convocatoria) {
		try {
			return Response.ok(manager.inEstadoAplicacionConv(estadoAplicacionConv, usuario, convocatoria)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/modEstadoAplicacionConv/id/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response modEstadoAplicacionConv(@Context HttpServletRequest req, EstadoAplicacionConv EstadoAplicacionConv, @PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modEstadoAplicacionConv(EstadoAplicacionConv, id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/getEstadoAplicacionConv/usuario/{usuario}")
	@Produces("application/json")
	public Response getEstadoAplicacionConv(@PathParam("usuario") Integer usuario) {
		try {
			return Response.ok(manager.getEstadoAplicacionConv(usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/getEstadoAplicacionConv/conv/{conv}")
	@Produces("application/json")
	public Response getAplicantesConv(@PathParam("conv") Integer conv) {
		try {
			return Response.ok(manager.getAplicantesConv(conv)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/borEstadoAplicacionConv/id/{id}")
	@Produces("application/json")
	public Response borEstadoAplicacionConv(@PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modVisibilidadEstadoAplicacionConv(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
}
