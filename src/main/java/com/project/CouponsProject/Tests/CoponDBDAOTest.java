package com.project.CouponsProject.Tests;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.project.CouponsProject.Beans.Coupon;
import com.project.CouponsProject.Beans.CouponType;
import com.project.CouponsProject.Dao.Impl.CouponDBDAO;

public class CoponDBDAOTest {

	
	public static void main(String[] args) throws Exception {

	//createCoupon();
	//getCoupon();
	
	//getAllCoupons();
		getCouponByType();
	}

	private static void getCouponByType() throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Coupon coup=new Coupon("aaaa", simpleDateFormat.parse("13/05/2017"), simpleDateFormat.parse("18/05/2017"), 10, CouponType.FOOD ,"sssss", 3.25, "http://");
		CouponDBDAO coupDao = new CouponDBDAO();
		
		coupDao.getCouponsByType(CouponType.FOOD);
		
	}

	private static void getAllCoupons() throws Exception {
		
		CouponDBDAO coupDao = new CouponDBDAO();
		coupDao.getAllCoupons();
		
		
	}

	private static void getCoupon() throws Exception {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/mm/dd");
		CouponDBDAO coupDao = new CouponDBDAO();
		
		System.out.println(coupDao.getCoupon(122).toString());
		
	}

	private static void createCoupon() throws SQLException, Exception {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Coupon coup=new Coupon("aaaa", simpleDateFormat.parse("13/05/2017"), simpleDateFormat.parse("18/05/2017"), 10, CouponType.FOOD ,"sssss", 3.25, "http;//");
		CouponDBDAO coupDao = new CouponDBDAO();
		
		coupDao.createCoupon(coup);
		}

}
