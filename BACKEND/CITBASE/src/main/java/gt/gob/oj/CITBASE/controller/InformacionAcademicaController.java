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
	@Path("/inInformacionAcademica")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inInformacionAcademica(@Context HttpServletRequest req, InformacionAcademica informacionAcademica) {
		try {
			return Response.ok(manager.inInformacionAcademica(informacionAcademica)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/modInformacionAcademica")
	@Consumes("application/json")
	@Produces("application/json")
	public Response modIdioma(@Context HttpServletRequest req, InformacionAcademica InformacionAcademica) {
		try {
			return Response.ok(manager.modInformacionAcademica(InformacionAcademica)).build();
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
	@Path("/borInformacionAcademica/{usuario}")
	@Produces("application/json")
	public Response borIdioma(@PathParam("usuario") Integer usuario) {
		try {
			return Response.ok(manager.modVisibilidadInformacionAcademica(usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
}
