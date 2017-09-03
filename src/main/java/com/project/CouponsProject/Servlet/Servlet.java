package com.project.CouponsProject.Servlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.CouponsProject.Facade.ClientType;
import com.project.CouponsProject.Facade.CouponClientFacade;
import com.project.CouponsProject.Facade.CouponSystemSingelton;

/**
 * Servlet implementation class Servlet
 */
public class Servlet extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
	private CouponSystemSingelton system;
    
	
	public Servlet() {
        super();
    }

 //  start the Coupon System
	public void init() throws ServletException {
		system = CouponSystemSingelton.getInstance();
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		
		if (session != null){	
			session.invalidate();
		}
		
		session = request.getSession(true);						
		session.setAttribute("system", system);
		
		// getting the data from the Login HTML form
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String clientType = request.getParameter("type");
		ClientType type = ClientType.valueOf(clientType);		// convert String to ENUM
		
		try	{
			
			CouponClientFacade facade = system.login(username, password, type);
			
			if (facade != null){
				// updating the session with the login facade
				session.setAttribute("facade", facade);			
				// Check Client Type and transfer to the right Page 
				switch (type){
					case ADMIN:
						request.getRequestDispatcher("admin.html").forward(request, response);		
						break;
						
					case COMPANY:
						request.getRequestDispatcher("company.html").forward(request, response);	
						break;
						
					case CUSTOMER:
						request.getRequestDispatcher("customer.html").forward(request, response);
						break;
						
					default:
						break;
				}
			}
			
			else {
				// return to the Login HTML form if the user name or password are incorrect
				response.sendRedirect("login.html");
			}
			
		}
		
		catch (Exception e) {
			System.err.println("FAILED TO ENTER the system");
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
	}
	

}
