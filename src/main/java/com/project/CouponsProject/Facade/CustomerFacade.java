package com.project.CouponsProject.Facade;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;

import com.project.CouponsProject.Beans.Coupon;
import com.project.CouponsProject.Beans.CouponType;
import com.project.CouponsProject.Beans.Customer;
import com.project.CouponsProject.Dao.CouponDAO;
import com.project.CouponsProject.Dao.CustomerDAO;
import com.project.CouponsProject.Dao.Impl.CouponDBDAO;
import com.project.CouponsProject.Dao.Impl.CustomerDBDAO;
import com.project.CouponsProject.Dao.Impl.DataVallidation;


public class CustomerFacade implements CouponClientFacade {
	CustomerDAO customerDao;
	CouponDAO couponDao;
	Customer customer;

	public CustomerFacade() {
		couponDao = CouponDBDAO.getInstance();
		customerDao = CustomerDBDAO.getInstance();
		customer = new Customer();

	}

	public Customer getCustomerName() {
		return customer;
	}

	public void purchasceCoupon(Coupon coupon) throws Throwable {

		com.project.CouponsProject.Dao.Impl.DataVallidation vallData = new DataVallidation();
		if (vallData.couponDateIsVallid(coupon) == true && vallData.couponInStock(coupon) == true
				&& vallData.customerHasOne(coupon) == false) {
			coupon = couponDao.getCoupon(coupon.getId());
			// UpdateCustomer_CouponTableAndCouponsColl updates customer_coupon table
			customerDao.UpdateCustomer_CouponTable(customer, coupon);
			coupon.setAmount(coupon.getAmount() - 1);
			couponDao.updateCoupon(coupon);
			System.out.println("coupon purchased");

		} else {
			throw new SQLException("Purchasing process has failed");
		}

	}

	public void getAllPurchasedCoupon() throws Exception {
		try {
			couponDao.getAllCoupons();
		} catch (SQLException e) {
			throw new SQLException("Cannot show all coupons purchased");
		}

	}

	public Collection<Coupon> getAllPurchasedCouponbyType(CouponType type) throws SQLException, Exception {
		return couponDao.getCouponsByType(type);

	}

	public Collection<Coupon> getAllPurchasedCouponbyPrice(double price) throws SQLException, ParseException  {
		return	couponDao.getCouponsByPrice(price);

	}

	public Collection<Coupon> getAllCoupons() throws SQLException, Exception {

		return couponDao.getAllCoupons();
	}

	@Override
	public CouponClientFacade login(String custName, String password, ClientType clientType) throws Exception {
		if (customerDao.login(custName, password) == true) {
			System.out.println("successfully logged in");
			customer = customerDao.getCustomerByName(custName);
			return this;
		} else {
			throw new Exception("Failed to login. customer name does not match password");
		}

	}

	public Coupon getCoupon(long id) throws SQLException, Exception {

		return couponDao.getCoupon(id);
	}

}
