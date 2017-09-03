package com.project.CouponsProject.Dao.Impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.project.CouponsProject.Beans.Coupon;
import com.project.CouponsProject.Beans.Customer;
import com.project.CouponsProject.Dao.CouponDAO;
import com.project.CouponsProject.Dao.CustomerDAO;

public class DataVallidation {
	CustomerDAO customerDao;
	CouponDAO couponDao;
	Customer customer;

	
	public DataVallidation() throws Exception, Throwable{
		couponDao= CouponDBDAO.getInstance();
		customerDao=CustomerDBDAO.getInstance();
		customer=  new Customer();
			
	}
	
	public Boolean customerHasOne(Coupon coupon) throws Exception, Throwable {
		Collection<Coupon> customersCoupons= new ArrayList<Coupon>();
		customersCoupons=customerDao.getCouponsByCustomer(customer);
		Long id = coupon.getId();
		while (customersCoupons.iterator().hasNext()){
			if( customersCoupons.iterator().next().getId() == id) {
				System.out.println("You already purchased this coupon");
				return true;
			}	
		}
		return false;

	}
	
	public Boolean couponDateIsVallid(Coupon coupon) throws Exception, Exception {
		Long id = coupon.getId();
		Coupon chechkCoup=couponDao.getCoupon(id);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd ");
		Date date = new Date();
		Date currentDate=dateFormat.parse(dateFormat.format(date));
		if(chechkCoup.getEnd_date().before(currentDate)){
			return true;
		}
		else{
		System.out.println("Coupon has expired ");
			return false;
		}
	}
		
	public Boolean couponInStock(Coupon coupon) throws Exception, Exception {
			Long id = coupon.getId();
			Coupon chechkCoupStock=couponDao.getCoupon(id);
			if(chechkCoupStock.getId()!=null && chechkCoupStock.getAmount()>=1){
				return true ;
			}
			else{
				System.out.println("Coupon out of stock");
				return false;
			}
		}
			
	}

