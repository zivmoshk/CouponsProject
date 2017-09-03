package com.project.CouponsProject.Tests;

import java.text.SimpleDateFormat;

import com.project.CouponsProject.Beans.Coupon;
import com.project.CouponsProject.Beans.CouponType;
import com.project.CouponsProject.Facade.ClientType;
import com.project.CouponsProject.Facade.CustomerFacade;


public class CustomerFacadTest {


	public static void main(String[] args) throws Throwable {
		purchasCoup();
	}
		
	public static void purchasCoup() throws Throwable {
		CustomerFacade custFac = new CustomerFacade();
		//Customer cust1 = new Customer(123456L, "Ziv", "12345QAZ");
		custFac.login("Ziv", "12345QAZ",ClientType.CUSTOMER);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Coupon coup=new Coupon("aaaa", simpleDateFormat.parse("13/05/2017"), simpleDateFormat.parse("18/05/2017"), 10, CouponType.FOOD ,"sssss", 3.25, "http;//");
		//custFac.login("Ziv", "12345QAZ");
		custFac.purchasceCoupon(coup);

	}

}
