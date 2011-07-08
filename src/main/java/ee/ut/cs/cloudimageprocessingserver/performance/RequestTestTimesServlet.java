/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.cloudimageprocessingserver.performance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
@WebServlet(name = "TestTimesServlet", urlPatterns = {"/TestTimesServlet"})
public class RequestTestTimesServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String testType = request.getParameter("testType");
        String testID = request.getParameter("testID");
        String timesJson = request.getParameter("clientTimes");
        String responseJson = null;
        
        Gson gson = new GsonBuilder().create();
        if (testType.equals("SyncTests")) {
            System.out.println("Getting sync test times from client");
            SyncTestTimes clientTimes = gson.fromJson(timesJson, SyncTestTimes.class);
            SyncTestTimes serverTimes = SyncTestTimesManager.sharedManager().getTimesForTestID(testID);
            serverTimes.updateWith(clientTimes);
            responseJson = gson.toJson(serverTimes);
            
        } else if (testType.equals("AsyncTests")) {
            //AsyncTestTimes times = gson.fromJson(timesJson, AsyncTestTimes.class);
			System.out.println("clientTimesJson : " + timesJson);
            AsyncTestTimes clientTimes = gson.fromJson(timesJson, AsyncTestTimes.class);
			AsyncTestTimes serverTimes = TestTimesManager.sharedManager().asyncTimesForTestID(testID);
			if(serverTimes != null) {
				serverTimes.updateWith(clientTimes);
			}
			
			responseJson = gson.toJson(serverTimes);
        } else {
//            StringBuilder builder = new StringBuilder();
//			for (Object time : TestTimesManager.sharedManager().getAllTimes()) {
//				builder.append(gson.toJson(time));
//				builder.append("\n<BR>");				
//			}
//            responseJson = builder.toString();
//			
			StringBuilder builder = new StringBuilder();
			double difference = -119.993868;
			double beginning = 0;
			String columnsTitlesRow = "ClientInitialRequest, ServerReceiveInitialRequest, ServerSendImmediateResponse, ClientReceiveImmediateResponse, ServerRequestToCloud, ServerResponseFromCloud, ServerSendPushNotification, ClientReceivePushNotification\n";
			builder.append(columnsTitlesRow);
			for (AsyncTestTimes asyncTime : TestTimesManager.sharedManager().allAsyncTimes()) {
				beginning = asyncTime.getClientInitialRequest();
				builder.append(asyncTime.getClientInitialRequest()-beginning);
				builder.append(", ");
				builder.append(asyncTime.getServerReceiveInitialRequest()-beginning+difference);
				builder.append(", ");
				builder.append(asyncTime.getServerSendImmediateResponse()-beginning+difference);
				builder.append(", ");
				builder.append(asyncTime.getClientReceiveImmediateResponse()-beginning);
				builder.append(", ");
				builder.append(asyncTime.getServerRequestToCloud()-beginning+difference);
				builder.append(", ");
				builder.append(asyncTime.getServerResponseFromCloud()-beginning+difference);
				builder.append(", ");
				builder.append(asyncTime.getServerSendPushNotification()-beginning+difference);
				builder.append(", ");
				builder.append(asyncTime.getClientReceivePushNotification()-beginning);
				builder.append("\n");
			}
            responseJson = builder.toString();
        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.write(responseJson);
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
