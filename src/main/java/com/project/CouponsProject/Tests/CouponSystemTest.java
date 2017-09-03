package com.project.CouponsProject.Tests;

import com.project.CouponsProject.Beans.Customer;
import com.project.CouponsProject.Facade.ClientType;
import com.project.CouponsProject.Facade.CouponSystemSingelton;
import com.project.CouponsProject.Facade.CustomerFacade;

public class CouponSystemTest {

	private static CustomerFacade cf = new CustomerFacade();
	
	public static void main(String[] args) throws Exception {
		CouponSystemSingelton css= new CouponSystemSingelton();
		Customer dvir= new Customer();
		css.login("Dvir", "QAZWSX12", ClientType.CUSTOMER);
		cf.getAllPurchasedCoupon();
		
		// TODO Auto-generated method stub

	}

}
