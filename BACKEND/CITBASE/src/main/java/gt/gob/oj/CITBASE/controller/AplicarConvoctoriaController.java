package gt.gob.oj.CITBASE.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import gt.gob.oj.CITBASE.manager.ConvocatoriaManager;
import gt.gob.oj.CITBASE.model.Convocatoria;
import gt.gob.oj.utils.jsonResult;

@Path("AplicarConvocatoria")
public class AplicarConvoctoriaController {
	ConvocatoriaManager manager = new ConvocatoriaManager();
	
	 @POST
	 @Path("/aplicar")
		@Produces("application/json")
	    @Consumes(MediaType.MULTIPART_FORM_DATA)
	    public Response uploadFile(
	        @Context HttpServletRequest request,
	        @FormDataParam("file") InputStream fileInputStream,
	        @FormDataParam("file") FormDataContentDisposition fileFormDataContentDisposition,
	        @FormDataParam("directory") String directory) {
         String fileLocation = "C://SAN//" + directory + "//" + fileFormDataContentDisposition.getFileName();  
         //saving file  
		 try {  
			 File directoryFile = new File("C://SAN//" + directory);
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
		 } catch (IOException e) {e.printStackTrace();}  
		 String output = "File successfully uploaded to : " + fileLocation;  
		 return Response.status(200).entity(output).build();
	    }
	 
	 @POST
	    @Produces("application/pdf")
	 	@Consumes(MediaType.MULTIPART_FORM_DATA)
	    @Path("/mostrar")
	    public Response mostrarPdf(
	    		@Context HttpServletRequest request,
	    		@FormDataParam("path") String filePath) {
		 	Response response = null;


	        File pdfFile = new File(filePath);

	        InputStream inputStream = null;
	        if (!pdfFile.exists()) {
	            return Response.status(404).build();
	        }

	        try {
	        	inputStream = new FileInputStream(pdfFile);
	            Response.ResponseBuilder builder = Response.ok(inputStream);
	            String filename = pdfFile.getName().replace(",","");
	            builder.header("Content-type", Files.probeContentType(pdfFile.toPath()));
	            builder.header("Content-Disposition", "filename" + filename);
	            response = builder.build();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return Response.status(400).entity("Error al cargar el archivo").build();
	        }
	        return response;
	    }
}
