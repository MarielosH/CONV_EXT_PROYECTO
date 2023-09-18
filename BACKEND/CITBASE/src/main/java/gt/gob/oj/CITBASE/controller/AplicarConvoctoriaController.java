package gt.gob.oj.CITBASE.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
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
		     FileOutputStream out = new FileOutputStream(new File(fileLocation));  
		     int read = 0;  
		     byte[] bytes = new byte[1024];  
		     out = new FileOutputStream(new File(fileLocation));  
		     while ((read = fileInputStream.read(bytes)) != -1) {  
		         out.write(bytes, 0, read);  
		     }  
		     out.flush();  
		     out.close();  
		 } catch (IOException e) {e.printStackTrace();}  
		 String output = "File successfully uploaded to : " + fileLocation;  
		 return Response.status(200).entity(output).build();
	    }
}
