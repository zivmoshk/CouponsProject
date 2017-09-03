package com.project.CouponsProject.Dao;
import java.sql.SQLException;
import java.util.Collection;

import com.project.CouponsProject.Beans.Company;
import com.project.CouponsProject.Beans.Coupon;
	
	//The interface is designed to bound the implements class to apply the following method.
	public interface CompanyDAO {
		
		public void createCompany(Company company) throws SQLException ;
		public void removeCompany(Company company) throws SQLException;
		public void updateCompany(Company company) throws SQLException;
		public Company getCompany(long id) throws SQLException;
		public Collection<Company> getAllCompanies() throws SQLException;
		public Collection<Coupon> getCoupons() throws SQLException, Exception;
		boolean login(String compName,String password) throws SQLException, Exception;
		public boolean isBelongToCompany (long compId, long couponId) throws Exception;
	}



