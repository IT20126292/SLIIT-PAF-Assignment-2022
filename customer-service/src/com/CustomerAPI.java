package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Customer;

/**
 * Servlet implementation class CustomerAPI
 */
@WebServlet("/CustomerAPI")
public class CustomerAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Customer cusObj = new Customer();
	
    public CustomerAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("done insert");
		String output = cusObj.createNewCustomer(
			request.getParameter("cus_Name"), 
			request.getParameter("cus_Nic"),
			request.getParameter("cus_addr"),
			request.getParameter("cus_pnumber"),
			request.getParameter("cus_username"),
			request.getParameter("cus_pwd")
		);
		response.getWriter().write(output);
	}

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("got here");
		Map paras = getParasMap(request); 
		 String output = cusObj.updateCustomer(
			paras.get("hidCustomerIDSave").toString(), 
			paras.get("cus_Name").toString(), 
			paras.get("cus_Nic").toString(), 
			paras.get("cus_addr").toString(), 
			paras.get("cus_pnumber").toString(), 
			paras.get("cus_username").toString(),
			paras.get("cus_pwd").toString()
		); 
		response.getWriter().write(output); 
	}
	
	private Map<String, String> getParasMap(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>(); 
			try { 
				 Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); 
				 String queryString = scanner.hasNext() ? 
				 scanner.useDelimiter("\\A").next() : ""; 
				 scanner.close(); 
				 String[] params = queryString.split("&"); 
				 for (String param : params) { 
					 String[] p = param.split("=");
					 map.put(p[0], p[1]); 
				 } 
			} catch (Exception e) { 
				
			} 
			return map; 
	}

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map paras = getParasMap(request);
		String output = cusObj.deleteCustomer(
				paras.get("cID").toString()
		);
		
		response.getWriter().write(output);
	}

}
