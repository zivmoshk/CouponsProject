package com.project.CouponsProject.Dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;

import com.project.CouponsProject.Beans.Company;
import com.project.CouponsProject.Beans.Coupon;
import com.project.CouponsProject.Beans.CouponType;

public interface CouponDAO {

	void createCoupon(Coupon coupon) throws SQLException, ParseException, Exception;

	void removeCoupon(Coupon coupon) throws SQLException;

	void updateCoupon(Coupon coupon) throws SQLException;

	Coupon getCoupon(long id) throws SQLException, Exception;

	Collection<Coupon> getAllCoupons() throws SQLException, Exception;

	 Collection<Coupon> getCouponsByPrice(double price) throws SQLException, ParseException;

	Collection<Coupon> getAllCompanyCoupons(Long comp_id) throws Exception;

	void createCouponCompany(Coupon coupon, Company company) throws SQLException;

	Collection<Coupon> getCompanyCouponsByPrice(double price, Long id) throws SQLException, ParseException;

	Collection<Coupon> getCouponsByType(CouponType type) throws SQLException, Exception;

	Collection<Coupon> getCompanyCouponsByType(CouponType type, Long id) throws SQLException, Exception;

	boolean isTitleExsist (String title) throws Exception;
	
}

