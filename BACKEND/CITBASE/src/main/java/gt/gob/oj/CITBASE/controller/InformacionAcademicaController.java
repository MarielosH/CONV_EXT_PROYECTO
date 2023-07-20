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

import gt.gob.oj.CITBASE.manager.InformacionAcademicaManager;
import gt.gob.oj.CITBASE.model.InformacionAcademica;
import gt.gob.oj.utils.jsonResult;

@Path("Informacion-Academica")
public class InformacionAcademicaController {
	InformacionAcademicaManager manager = new InformacionAcademicaManager();
	
	@POST
	@Path("/inInformacionAcademica/usuario/{usuario}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inInformacionAcademica(@Context HttpServletRequest req, InformacionAcademica informacionAcademica, @PathParam("usuario") Integer usuario) {
		try {
			return Response.ok(manager.inInformacionAcademica(informacionAcademica, usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/modInformacionAcademica/id/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response modIdioma(@Context HttpServletRequest req, InformacionAcademica InformacionAcademica, @PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modInformacionAcademica(InformacionAcademica, id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/getInformacionAcademica/usuario/{usuario}")
	@Produces("application/json")
	public Response getIdiomas(@PathParam("usuario") Integer usuario) {
		try {
			return Response.ok(manager.getInformacionAcademica(usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/borInformacionAcademica/id/{id}")
	@Produces("application/json")
	public Response borIdioma(@PathParam("id") Integer id) {
		try {
			return Response.ok(manager.modVisibilidadInformacionAcademica(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
}
