package com.project.CouponsProject.Service;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.project.CouponsProject.Beans.Coupon;
import com.project.CouponsProject.Beans.CouponType;
import com.project.CouponsProject.Beans.Customer;
import com.project.CouponsProject.Facade.CustomerFacade;


@Path("customer")
public class CustomerService {

	@Context
	private HttpServletRequest request;

	@POST
	@Path("login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login() {
		HttpSession session = request.getSession(false);
		CustomerFacade customerFacade = (CustomerFacade) session.getAttribute("facade");
		
		return null;

	}

	@GET
	@Path("purchaseCoupon")
	@Produces(MediaType.APPLICATION_JSON)
	public String purchaseCoupon(@QueryParam("couponId") long couponId) throws Throwable {
		HttpSession session = request.getSession(false);
		CustomerFacade customerFacade = (CustomerFacade) session.getAttribute("facade");
		Customer customer = customerFacade.getCustomerName();
		session.setAttribute("customer", customer);
		try {
			Coupon coupon = customerFacade.getCoupon(couponId);
			if (coupon != null) {
				customerFacade.purchasceCoupon(coupon);
				return "SUCCEED TO PURCHASE A COUPON: " + coupon.getTitle() + ", id : " + coupon.getId() + " by: "
						+ customer.getCust_name();
			} else {
				return "FAILED: there is no such id!" + couponId + " - please try another coupon id";
			}
		} catch (Exception e) {
			return "FAILED PURCHASE COUPON ID: " + couponId;
		}
	}

	@GET
	@Path("getAllPurchasedCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllPurchasedCoupons() {
		HttpSession session = request.getSession(false);
		CustomerFacade customerFacade = (CustomerFacade) session.getAttribute("facade");
		Customer customer = customerFacade.getCustomerName();
		session.setAttribute("customer", customer);
		Collection<Coupon> coupons = null;
		try {
			coupons = customerFacade.getAllCoupons();
		} catch (Exception e) {
			System.out.println("FAILD TO GET ALL COUPONS");
			e.printStackTrace();
		}
		return coupons;

	}

	@GET
	@Path("getAllPurchasedCouponsByType")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllPurchasedCouponsByType(@QueryParam("couponType") String couponType) throws Throwable {
		HttpSession session = request.getSession(false);
		CustomerFacade customerFacade = (CustomerFacade) session.getAttribute("facade");
		Customer customer = customerFacade.getCustomerName();
		session.setAttribute("customer", customer); 
		
		Collection<Coupon> coupons = null;
		try {
			CouponType type = CouponType.valueOf(couponType); 
			coupons = customerFacade.getAllPurchasedCouponbyType(type);
		} catch (Exception e) {
			System.out.println("FAILD TO GET ALL COUPONS BY TYPE");
			e.printStackTrace();
		}
		return coupons;

	}

	@GET
	@Path("getAllPurchasedCouponsByPrice")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllPurchasedCouponsByPrice(@QueryParam("price") double price) {
		HttpSession session = request.getSession(false);
		CustomerFacade customerFacade = (CustomerFacade) session.getAttribute("facade");
		Customer customer = customerFacade.getCustomerName();
		session.setAttribute("customer", customer);
		
		Collection<Coupon> coupons = null;
		try {
			coupons = customerFacade.getAllPurchasedCouponbyPrice(price);
		} catch (Exception e) {
			System.out.println("FAILD TO GET ALL COUPONS BY PRICE");
			e.printStackTrace();
		}
		return coupons;
	}
	
	@GET
	@Path("exit")
	public String exit() {
		HttpSession session = request.getSession(false);
		CustomerFacade customerFacade = (CustomerFacade) session.getAttribute("facade");
		Customer customer = customerFacade.getCustomerName();
		session.setAttribute("customer", customer);

		session.invalidate(); //End the session.
		return "BYE BYE "+customer.getCust_name();
	}
	
	
}
