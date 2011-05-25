/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.cloudimageprocessingserver.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ee.ut.cs.cloudimageprocessingserver.model.VideoResource;
import ee.ut.cs.cloudimageprocessingserver.notifications.NotificationCentre;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author madis
 */
@WebServlet(name = "ProcessResource", urlPatterns = {"/ProcessResource"})
public class ProcessResource extends HttpServlet {

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
		Gson gson = new GsonBuilder().create();
		String resourceJson = request.getParameter("resource");
		VideoResource requestedResource = gson.fromJson(resourceJson, VideoResource.class);
		
		// TODO: process the requestedResource. When processing is done, notify device.
		// Currently deviceToken for our iphone is 5324075b4c07afa9e2dbe15b74dbda2227a74d5537f0d4af24cc0aecda697f1c
		// Example is given here: (this code should be called when processing is COMPLETE
		NotificationCentre.getProviderForDevice("iphone")
				.withDeviceData("5324075b4c07afa9e2dbe15b74dbda2227a74d5537f0d4af24cc0aecda697f1c")
				.withMessage("Processing complete")
				.withKeyValuePairs("resource", resourceJson)
				.send();
		
		PrintWriter out = response.getWriter();
		try {
			/* TODO output your page here
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet ProcessResource</title>");  
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet ProcessResource at " + request.getContextPath () + "</h1>");
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
