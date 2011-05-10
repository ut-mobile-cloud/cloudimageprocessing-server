/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.cloudimageprocessingserver.servlets;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectResult;
import ee.ut.cs.cloudimageprocessingserver.fileupload.FileUploadTask;
import ee.ut.cs.cloudimageprocessingserver.fileupload.FileUploader;
import ee.ut.cs.cloudimageprocessingserver.fileupload.FileUploaderFactory;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author madis
 */
@WebServlet(name = "UploadResource", urlPatterns = {"/UploadResource"})
public class UploadResource extends HttpServlet {
	
	static final String TEMP_FOLDER_FOR_IMAGES = "/tmp/uploads";
	
    private File storeAndReturnFileItemContents(FileItem item) {
        File path = new File(TEMP_FOLDER_FOR_IMAGES);
        String fileName = item.getName();
        if (!path.exists()) {
            boolean status = path.mkdirs();
        }
        File uploadedFile = new File(path + "/" + fileName);
        try {
            item.write(uploadedFile);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return uploadedFile;
    }
    
    private void sendRequestToCloud(List<File>files) {
		// TODO: Carlos sent the name of this bucket to your e-mail
        String DefaultS3BucketName = null;//"ut-ee-cs-ds-mobilecloud-testbucket";
        AmazonS3 s3 = null;
        try {
			s3 = new AmazonS3Client(new PropertiesCredentials(
					this.getClass().getClassLoader().getResourceAsStream("AwsCredentials.properties")));
		} catch (IOException ex) {
			//Logger.getLogger(UploadPictureTask.class.getName()).log(Level.SEVERE, null, ex);
		}
        s3.createBucket(DefaultS3BucketName);
		for (File file : files) {
			// TODO: filename should be taken from somewhere. Otherwise all hell breaks loose.
			String fileName = null;
			PutObjectResult putObjectResult = s3.putObject(DefaultS3BucketName, fileName, file);
			System.out.println("File name : " + fileName);
		}
        
    }
	/** 
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		// Lets get the file from request
        String resourceID = null;
        List<File> files = new ArrayList<File>();
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        
        List items = null;
        try {
            items = upload.parseRequest(request);
            Iterator it = items.iterator();
            
            while(it.hasNext()) {
                FileItem item = (FileItem) it.next();
                if (!item.isFormField()) {
                    files.add(storeAndReturnFileItemContents(item));
                } else {
                    if (item.getFieldName().equals("resourceID")) {
                        resourceID = item.getString();
                    }
                }
            }
        } catch (Exception ex) {
            
        }
		
		FileUploader uploader = FileUploaderFactory.newMockFileUploader()
				.withFiles(files);
		
		Thread uploadThread = new Thread(new FileUploadTask(uploader));
		uploadThread.start();
		
		PrintWriter out = response.getWriter();
		try {
			/* TODO output your page here
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet UploadResource</title>");  
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet UploadResource at " + request.getContextPath () + "</h1>");
			out.println("</body>");
			out.println("</html>");
			 */
		} finally {			
			out.close();
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/** 
	 * Handles the HTTP <code>GET</code> method.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/** 
	 * Handles the HTTP <code>POST</code> method.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/** 
	 * Returns a short description of the servlet.
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}
