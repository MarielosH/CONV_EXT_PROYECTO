package gt.gob.oj.CITBASE.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import gt.gob.oj.CITBASE.manager.ConvocatoriasExternasManager;
import gt.gob.oj.CITBASE.manager.EstadoAplicacionConvManager;
import gt.gob.oj.CITBASE.manager.LoginManager;
import gt.gob.oj.CITBASE.model.EstadoAplicacionConv;
import gt.gob.oj.CITBASE.model.ExperienciaLaboralOJ;
import gt.gob.oj.CITBASE.model.PerfilSolicitudEmpleo;
import gt.gob.oj.CITBASE.model.Usuario;
import gt.gob.oj.utils.jsonResult;

@Path("Convocatorias-Externas")
public class ConvocatoriasExternasController {

	ConvocatoriasExternasManager manager = new ConvocatoriasExternasManager();
	EstadoAplicacionConvManager managerEstadoAplicacion = new EstadoAplicacionConvManager();
	@POST
	 @Path("/guardarPerfilPdf")
		@Produces("application/json")
	    @Consumes(MediaType.MULTIPART_FORM_DATA)
	    public Response uploadFile(
	        @Context HttpServletRequest request,
	        @FormDataParam("file") InputStream fileInputStream,
	        @FormDataParam("file") FormDataContentDisposition fileFormDataContentDisposition,
				@FormDataParam("directory") String directory) throws Exception {
		 String fileLocation = "C://SAN//" + directory + "//perfil//Informacion_firmada.pdf";  
			// saving file
			try {
				File directoryFile = new File("C://SAN//" + directory + "//perfil");
				if (!directoryFile.exists()) {
					directoryFile.mkdirs();
				}

				int read = 0;
				byte[] bytes = new byte[1024];
				FileOutputStream out = new FileOutputStream(new File(fileLocation));
				while ((read = fileInputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				out.flush();
				out.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
			String output = "File successfully uploaded to : " + fileLocation;
			return Response.status(200).entity(output).build();
		}
	
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

	@GET
	@Path("/getIdiomas")
	@Produces("application/json")
	public Response getIdiomas() {
		try {
			return Response.ok(manager.getIdiomas()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}

	@GET
	@Path("/getEstadoCivil")
	@Produces("application/json")
	public Response getEstadoCivil() {
		try {
			return Response.ok(manager.getEstadoCivil()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
		}
	}

}
