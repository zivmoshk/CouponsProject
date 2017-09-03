package com.project.CouponsProject.Dao;

import java.sql.SQLException;
import java.util.Collection;

import com.project.CouponsProject.Beans.Coupon;
import com.project.CouponsProject.Beans.Customer;

public interface CustomerDAO {

	void createCustomer(Customer customer) throws SQLException;

	void removeCustomer(Customer customer) throws SQLException;

	void updateCustomer(Customer customer) throws SQLException;

	Customer getCustomer(long id) throws SQLException;

	Collection<Customer> getAllCustomers() throws SQLException;

	public void UpdateCustomer_CouponTable(Customer customer, Coupon coupon) throws SQLException;

	Collection<Coupon> getCoupons() throws SQLException, Exception;

	boolean login(String custName, String password) throws SQLException, Exception;

	void RemoveCustomer_CouponTable(Coupon coupon) throws SQLException;

	Collection<Coupon> getCouponsByCustomer(Customer customer) throws SQLException, Exception;

	Customer getCustomerByName(String cust_name) throws Exception;
}
