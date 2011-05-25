/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.cloudimageprocessingserver.servlets;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ee.ut.cs.cloudimageprocessingserver.ResourceManager;
import ee.ut.cs.cloudimageprocessingserver.model.Resource;
import ee.ut.cs.cloudimageprocessingserver.model.VideoResource;
import ee.ut.cs.cloudimageprocessingserver.notifications.NotificationCentre;
import ee.ut.cs.cloudimageprocessingserver.notifications.NotificationProvider;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author madis
 */
@WebServlet(name = "ListResources", urlPatterns = {"/ListResources"})
public class ListResources extends HttpServlet {

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
//		NotificationProvider iphoneNotifier = NotificationCentre.getProviderForDevice("iphone")
//				.withDeviceData("5324075b4c07afa9e2dbe15b74dbda2227a74d5537f0d4af24cc0aecda697f1c")
//				.withKeyValuePairs("sita", "rida")
//				.withMessage("Vaata aga vaata");
//		iphoneNotifier.send();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().create();
		try {
			//out.print(gson.toJson(ResourceManager.getInstance().getAllResources())); 
			out.print(gson.toJson(getResourcesFromAmazon())); 
		} finally {			
			out.close();
		}
	}
	
	List<Resource> getResourcesFromAmazon() {
		List<Resource> resourceList = new ArrayList<Resource>();
		String DefaultS3BucketName = "cloudimageprocessing";
        AmazonS3 s3 = null;
        try {
			s3 = new AmazonS3Client(new PropertiesCredentials(
					this.getClass().getClassLoader().getResourceAsStream("AwsCredentials.properties")));
		} catch (IOException ex) {
			//Logger.getLogger(UploadPictureTask.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		ObjectListing objectListing = s3.listObjects(DefaultS3BucketName);
		List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
		
		for(S3ObjectSummary summary : objectSummaries) {
			Resource resource = new VideoResource();
			resource.setID(summary.getETag());
			resource.setSize(summary.getSize());
			resource.setTitle(summary.getKey());
			resource.setDuration(60);
			resource.setLocation(summary.getKey());
			
			resourceList.add(resource);
		}
		
		return resourceList;
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
