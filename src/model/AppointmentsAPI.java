package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppointmentsAPI extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
    Appointment appobj = new Appointment();
 /**
  * @see HttpServlet#HttpServlet()
  */
 public AppointmentsAPI() {
     super();
     // TODO Auto-generated constructor stub
 }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("discription"));
		
		String output = appobj.insertAppointment(request.getParameter("discription"),
				 request.getParameter("patient_id"),
				request.getParameter("doctor_id"),
				request.getParameter("hospital_id"),
				request.getParameter("datetime"),
				request.getParameter("charge")); 
		
		
		response.getWriter().write(output); 
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

		Map paras = getParasMap(request);
		
		String output = appobj.updateAppointment(paras.get("hidAppointmentIDSave").toString(), paras.get("discription").toString(),
				paras.get("patient_id").toString(), paras.get("doctor_id").toString(), paras.get("hospital_id").toString(),
				paras.get("datetime").toString(), paras.get("charge").toString());
		response.getWriter().write(output);
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		 String output = appobj.deleteAppointment(paras.get("apt_ID").toString());
		  	 
		response.getWriter().write(output); 

		// TODO Auto-generated method stub
	}

	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			
			String[] params = queryString.split("&");
			
			for (String param : params)
				{

				String[] p = param.split("=");
				map.put(p[0], p[1]);
				}
			} 
		catch (Exception e) {
	}
		return map;
	}


}
