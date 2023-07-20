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

import gt.gob.oj.CITBASE.manager.ConvocatoriasExternasManager;
import gt.gob.oj.CITBASE.manager.LoginManager;
import gt.gob.oj.CITBASE.model.PerfilSolicitudEmpleo;
import gt.gob.oj.CITBASE.model.Usuario;
import gt.gob.oj.utils.jsonResult;

@Path("Convocatorias-Externas")
public class ConvocatoriasExternasController {

	ConvocatoriasExternasManager manager = new ConvocatoriasExternasManager();

	@GET
	@Path("/getConvocatoriasExt")
	@Produces("application/json")
	public Response getConvocatoriasExternas() {
		try {
			return Response.ok(manager.getConvocatoriasExternas()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}

	@GET
	@Path("/getDepartamentos")
	@Produces("application/json")
	public Response getDepartamentos() {
		try {
			return Response.ok(manager.getDepartamentos()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}

	@GET
	@Path("/getMunicipios/{departamento}")
	@Produces("application/json")
	public Response getMunicipios(@PathParam("departamento") Integer departamento) {
		try {
			return Response.ok(manager.getMunicipios(departamento)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}

	@GET
	@Path("/getComunidadLinguistica")
	@Produces("application/json")
	public Response getComunidadLinguistica() {
		try {
			return Response.ok(manager.getComunidadesLinguisticas()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}

	@POST
	// @Authorize
	@Path("/inPerfilSolicitudEmpleo")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inPerfilSolicitudEmpleo(@Context HttpServletRequest req, PerfilSolicitudEmpleo perfil) {
		try {
			return Response.ok(manager.inPerfilSolicitudEmpleo(perfil)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/getPerfilSolicitudDpi/{dpi}")
	@Produces("application/json")
	public Response getPerfilSolicitudDpi(@PathParam("dpi") String dpi) {
		try {
			return Response.ok(manager.getPerfilSolicitudDpi(dpi)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	
	@GET
	@Path("/getParentesco")
	@Produces("application/json")
	public Response getParentesco() {
		try {
			return Response.ok(manager.getParentesco()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/getEtnias")
	@Produces("application/json")
	public Response getEtnias() {
		try {
			return Response.ok(manager.getEtnias()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}

}
