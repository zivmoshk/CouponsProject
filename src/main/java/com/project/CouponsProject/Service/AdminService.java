package com.project.CouponsProject.Service;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.project.CouponsProject.Beans.Company;
import com.project.CouponsProject.Beans.Customer;
import com.project.CouponsProject.Facade.AdminFacade;
import com.project.CouponsProject.Facade.ClientType;

@Path("admin")
public class AdminService {
	
	@Context    
	private HttpServletRequest request;
	
	@POST
	@Path("login")
	public String login (@QueryParam("username") String userName,
						@QueryParam("password") String password,
						@QueryParam("type") ClientType type) {
		return null;
		
	}
	
	@POST
	@Path("createCoumpany")
	public void createCoumpany (@QueryParam("companyName") String companyName,
									@QueryParam("companyPassword") String companyPassword,
									@QueryParam("companyEmail") String companyEmail) throws Exception {
		
		HttpSession session = request.getSession(false);
		AdminFacade adminFacade = (AdminFacade) session.getAttribute("facade");
		
		Company company=new Company(companyName, companyPassword, companyEmail);
		try {
			adminFacade.createCompany(company);
		} catch (Exception e) {
			throw new Exception("FAILED ADD A NEW COMPANY: "+companyName);
		}
		
	}
	
	@DELETE
	@Path("removeCompany")
	public String removeCompany(@QueryParam("companyId") long companyId) throws Exception {
		HttpSession session = request.getSession(false);									
		AdminFacade admin = (AdminFacade)session.getAttribute("facade");
		
		try {
			Company company = admin.getCompany(companyId);
			if(company!=null) {
				admin.removeComany(company);
				return "SUCCEED TO REMOVE COMPANY :"+company.getComp_name();
			}
			else{
				return "FAILED TO REMOVE COMPANY: there is no such id: " + companyId + " - please enter another customer id";
			}
		} catch (Exception e) {
			throw new Exception("FAILED TO REMOVE COMPANY");
		}
	}
	
	@PUT
	@Path("updateCompany")
	public String updateCompany(@QueryParam("companyId") long companyId,
								@QueryParam("companyPsaaword") String companyPassword,
								@QueryParam("companyEmail") String companyEmail) throws Exception {
		HttpSession session = request.getSession(false);									
		AdminFacade admin = (AdminFacade)session.getAttribute("facade");
				
		try {

			Company company = admin.getCompany(companyId);
			
			if (company != null){
				company.setPassword(companyPassword);
				company.setEmail(companyEmail);

				admin.updateCompany(company);
				return "SUCCEED TO UPDATE COMPANY:";
						}
			
			else {
				return "FAILED TO UPDATE COMPANY: there is no such id; " + companyId + " - please enter another company id";
			}	
		} catch (Exception e) {
			 throw new Exception("FAILD TO UPDATE COMPANY");
		}
	}
	
	@GET
	@Path("getCompanyById")
	@Produces(MediaType.APPLICATION_JSON)
	public Company getConpantById(@QueryParam("companyId") long companyId) throws Exception {
		HttpSession session = request.getSession(false);									
		AdminFacade admin = (AdminFacade)session.getAttribute("facade");
		Company company=null;
		
		try {
		company=admin.getCompany(companyId);
		
		} catch (Exception e) {
			throw new Exception("FAILED GET COMPANY");
		}
		return company;
	}
	
	@GET
	@Path("getAllCompanies")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Company> getAllCompanies() throws Exception{
		HttpSession session = request.getSession(false);									
		AdminFacade admin = (AdminFacade)session.getAttribute("facade");
		Collection<Company> companies = null;
		try {
			companies=admin.getAllCompany();
		} catch (Exception e) {
			throw new Exception("FAILED GET ALL COMPANIES");
		}
		return companies;
	}
	
	@POST
	@Path("createCustomer")
	public void createCustomer(@QueryParam("customerName") String customerName,
								@QueryParam("customerPasswors") String customerPassword) throws Exception {
		HttpSession session = request.getSession(false);									
		AdminFacade admin = (AdminFacade)session.getAttribute("facade");
		
		Customer customer = new Customer(customerName,customerPassword);
		try {
			admin.createCustomer(customer);
			
		} catch (Exception e) {
			throw new Exception("FAILED TO ADD A NEW CUSTOMER");
		}
		
		
	}
	
	@DELETE
	@Path("removeCustomer")
	public String removeCustomer(@QueryParam("customerId") long customerId) throws Exception {
		HttpSession session = request.getSession(false);									
		AdminFacade admin = (AdminFacade)session.getAttribute("facade");
		
		try {
			Customer customer = admin.getCustomer(customerId);
			if(customer!=null) {
				admin.removeCustomer(customer);
				return "SUCCEED TO REMOVE CUSTOMER:  " + customer.getCust_name();
			}
			else {
				return "FAILED TO REMOVE CUSTOMER: there is no such id: " + customerId + " - please enter another customer id";
			}
		} catch (Exception e) {
			throw new Exception("FAILED TO REMOVE CUSTOMER");	
		}
	}
	
	@PUT
	@Path("updateCustomer")
	public String updateCustomer(@QueryParam("customerId") long customerId,
								@QueryParam("customerPassword") String customerPassword) throws Exception  {
								
		HttpSession session = request.getSession(false);									
		AdminFacade admin = (AdminFacade)session.getAttribute("facade");
		
		try {
			Customer customer = admin.getCustomer(customerId);
			if(customer!=null) {
				customer.setPassword(customerPassword);
				admin.updateCustomer(customer);
				return "SUCCEED TO UPDATE A CUSTOMER WITH ID :"+customerId;
			}
			else {
				return "FAILED TO UPDATE CUSTOMER: there is no such id: " + customerId + " - please enter another customer id";
			}
		} catch (Exception e) {
			throw new Exception("FAILED TO UPDATE CUSTOMER");
					
		}
		
	}
	
	@GET
	@Path("getCustomer")
	public Customer getCustomer(@QueryParam("customerId") long customerId) throws Exception {
		HttpSession session = request.getSession(false);									
		AdminFacade admin = (AdminFacade)session.getAttribute("facade");
		
		Customer customer;
		try {
		   customer=admin.getCustomer(customerId);
		} catch (Exception e) {
			throw new Exception("FAILED GET CUSTOMER");
		}
		
		return customer;
	}
	
	@GET
	@Path("getAllCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getAllCustomers() throws Exception{
		HttpSession session = request.getSession(false);									
		AdminFacade admin = (AdminFacade)session.getAttribute("facade");
		
		Collection<Customer> customers = null;
		try {
		 customers=admin.getAllCustomer();
		 
		} catch (Exception e) {
			throw new Exception("FAILED GET ALL CUSTOMERS");
		}
		return customers;
	}
}
