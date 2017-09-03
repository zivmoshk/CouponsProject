package com.project.CouponsProject.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

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
import com.project.CouponsProject.Beans.Coupon;
import com.project.CouponsProject.Beans.CouponType;
import com.project.CouponsProject.Beans.Customer;
import com.project.CouponsProject.Facade.CompanyFacade;
import com.project.CouponsProject.Facade.CustomerFacade;



@Path("company")
public class CompanyService {
	
	@Context    
	private HttpServletRequest request;
	
	 private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@POST
	@Path("createCoupon")
	public String createCoupon(@QueryParam("title") String title,
								@QueryParam("startDate") String startDate,
								@QueryParam("endDate") String endDate,
								@QueryParam("amount") Integer amount,
								@QueryParam("type") String type,
								@QueryParam("message") String message,
								@QueryParam("price") double price,
					            @QueryParam("image") String image) throws ParseException{
		
		 HttpSession session = request.getSession(false);									
		 CompanyFacade companyFacade = (CompanyFacade)session.getAttribute("facade");
		 Company company = companyFacade.getCompanyName();
		 session.setAttribute("company", company);
		 
		//Change from String to Date.				
		Date sDate = sdf.parse(startDate);
		Date eDate=sdf.parse(endDate);
		//Change from String to CuponType.
		CouponType couponType = null;
		for (CouponType ct : CouponType.values()){
			if (ct.name().equals(type)){
				couponType = ct;
			}
		}
		
		Coupon coupon=new Coupon(title,sDate,eDate,amount,couponType,message,price,image);
		
		try {
			companyFacade.createCoupon(coupon);				
		} catch (Exception e) {
			e.printStackTrace();
		}
			return " FAILED TO ADD A NEW COUPON  ";
	}
	
	
	@DELETE
	@Path("removeCoupon")
	public String removeCoupon(@QueryParam("couponId") long id) {
		 HttpSession session = request.getSession(false);									
		 CompanyFacade companyFacade = (CompanyFacade)session.getAttribute("facade");
		 Company company = companyFacade.getCompanyName();
		 session.setAttribute("company", company);
		 
		 try {
			 Coupon coupon = companyFacade.getCoupon(id);
				companyFacade.removeCoupon(coupon);				
			} catch (Exception e) {
				e.printStackTrace();
			}
				return " FAILED TO DELETE COUPON  ";
	}
	
	
	@PUT
	@Path("updateCoupon")
	public String updateCoupon(@QueryParam("couponId") Long id,
								@QueryParam("endDate") String endDate,							
								@QueryParam("price") Double price){
	
		 HttpSession session = request.getSession(false);									
		 CompanyFacade companyFacade = (CompanyFacade)session.getAttribute("facade");
		 Company company = companyFacade.getCompanyName();
		 session.setAttribute("company", company);
		 
		 try {
			Coupon coupon=companyFacade.getCoupon(id);
			companyFacade.updateCoupon(coupon);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return " FAILED TO UPDATE COUPON ";
	}
	
	@GET
	@Path("getCouponById")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon getCouponById(@QueryParam("couponId") long id) throws Exception {
		HttpSession session = request.getSession(false);
		CompanyFacade companyFacade = (CompanyFacade) session.getAttribute("facade");
		Company company = companyFacade.getCompanyName();
		session.setAttribute("company", company);
		 
		Coupon coupon=null;
		try {
			coupon=companyFacade.getCoupon(id);
		} catch (Exception e) {
			throw new Exception("FAILED TO GET COUPON BY ID: "+id);
		}
	return coupon;
	}
	
	
	@GET
	@Path("getAllCouponsCompany")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllCouponsCompany(@QueryParam("companyId") long id) throws Exception{
		HttpSession session = request.getSession(false);
		CompanyFacade companyFacade = (CompanyFacade) session.getAttribute("facade");
		Company company = companyFacade.getCompanyName();
		session.setAttribute("company", company);
		
		Collection<Coupon> coupons=null;
		try {
			coupons=companyFacade.getAllCompanyCoupons(id);
			
		} catch (Exception e) {
			throw new Exception("FAILED TO GET COUPONS_COMPANY BY COMPANY: "+company.getComp_name());
		}
		
		return coupons;
		
	}
	
	@GET
	@Path("getCouponsByType")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponsByType(@QueryParam("couponType") String couponType) throws Exception{
		 HttpSession session = request.getSession(false);									
		 CompanyFacade companyFacade = (CompanyFacade)session.getAttribute("facade");
		 Company company = companyFacade.getCompanyName();
		 session.setAttribute("company", company);
		
		Collection<Coupon> coupons = null;
		
		try {
			CouponType type = CouponType.valueOf(couponType); // convert String to ENUM
			coupons = companyFacade.getAllCouponByType(type, company.getId());
			
		} catch (Exception e) {
			throw new Exception("FAILED TO GET COUPON BY TYPE: "+couponType);
		}
		return coupons;
		
	}
	
	@GET
	@Path("exit")
	public String exit() {
		HttpSession session = request.getSession(false);
		CompanyFacade companyFacade = (CompanyFacade) session.getAttribute("facade");
		Company company = companyFacade.getCompanyName();
		session.setAttribute("company", company);

		session.invalidate(); //End the session.
		return "BYE BYE "+company.getComp_name();
	}
	
	
	
}
