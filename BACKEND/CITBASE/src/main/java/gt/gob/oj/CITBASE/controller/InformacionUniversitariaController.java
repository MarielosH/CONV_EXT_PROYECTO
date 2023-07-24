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

import gt.gob.oj.CITBASE.manager.InformacionUniversitariaManager;
import gt.gob.oj.CITBASE.model.InformacionUniversitaria;
import gt.gob.oj.utils.jsonResult;

@Path("Informacion-Universitaria")
public class InformacionUniversitariaController {
	InformacionUniversitariaManager manager = new InformacionUniversitariaManager();
	
	@POST
	@Path("/inInformacionUniversitaria/usuario/{usuario}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inInformacionUniversitaria(@Context HttpServletRequest req, InformacionUniversitaria informacionUniversitaria, @PathParam("usuario") Integer usuario) {
		try {
			return Response.ok(manager.inInformacionUniversitaria(informacionUniversitaria, usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/modInformacionUniversitaria/id/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response modInformacionUniversitaria(@Context HttpServletRequest req, InformacionUniversitaria informacionUniversitaria, @PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modInformacionUniversitaria(informacionUniversitaria, id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/getInformacionUniversitaria/usuario/{usuario}")
	@Produces("application/json")
	public Response getInformacionUniversitaria(@PathParam("usuario") Integer usuario) {
		try {
			return Response.ok(manager.getInformacionUniversitaria(usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/borInformacionUniversitaria/id/{id}")
	@Produces("application/json")
	public Response borInformacionUniversitaria(@PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modVisibilidadInformacionUniversitaria(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
}
