package com.project.CouponsProject.Dao.Impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.project.CouponsProject.Beans.Company;
import com.project.CouponsProject.Beans.Coupon;
import com.project.CouponsProject.Beans.Customer;
import com.project.CouponsProject.Dao.CompanyDAO;
import com.project.CouponsProject.Dao.ConnectionPool;

public class CompanyDBDAO implements CompanyDAO {
	private ConnectionPool pool;
	
	
	public CompanyDBDAO(){	
		pool = ConnectionPool.getInstance();
	}
	//singleton
	private static CompanyDBDAO instance= null;
	public static CompanyDBDAO getInstance(){
		
		if (instance == null) {
			instance = new CompanyDBDAO();
		}
		
		return instance;
	}
	
	
	@Override
	public void createCompany(Company company) throws SQLException {
		Connection con = pool.getConnection();

		try {
			String query = "INSERT INTO Company (id, Comp_name, Password, email)" + "VALUES ('" + company.getId()
					+ "','" + company.getComp_name() + "','" + company.getPassword() + "','" + company.getEmail()
					+ "');";

			Statement st;
			st = con.createStatement();
			st.executeUpdate(query);
			System.out.println("Company has been created");
		} catch (SQLException e) {
			throw new SQLException("Cannot insert company data into BD");
		}

		pool.returnConnection(con);

	}

	@Override
	public void removeCompany(Company company) throws SQLException {
		Connection con = pool.getConnection();
		try {
			String query = "DELETE FROM Company WHERE id=" + company.getId() + ";";
			Statement st;
			st = con.createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			throw new SQLException("Cannot delete company data from  BD");
		}

		pool.returnConnection(con);

	}

	@Override
	public void updateCompany(Company company) throws SQLException {
		Connection con = pool.getConnection();

		try {

			String query = "UPDATE Company SET comp_name=" + company.getComp_name() + ", Password="
					+ company.getPassword() + ", email=" + company.getEmail() + " WHERE id=" + company.getId() + ";";
			Statement st;
			st = con.createStatement();
			st.executeUpdate(query);
			System.out.println("Company has been updated");
		} catch (SQLException e) {
			throw new SQLException("Cannot update company data");
		}

		pool.returnConnection(con);

	}

	@Override
	public Company getCompany(long id) throws SQLException {
		Connection con = pool.getConnection();
		Company company = new Company();
		try {
			String query = "SELECT * FROM Company WHERE id=" + id + ";";
			Statement st;
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {

				String compName = rs.getString("comp_name");
				String compPass = rs.getString("Password");
				String compEmail = rs.getString("email");
				company.comp_name = rs.getString(compName);
				company.Password = rs.getString(compPass);
				company.email = rs.getString(compEmail);

			}

		} catch (SQLException e) {
			throw new SQLException("Cannot select company data from BD");
		}
		pool.returnConnection(con);
		return company;

	}

	@Override
	public Collection<Company> getAllCompanies() throws SQLException {
		Connection con = pool.getConnection();
		ArrayList<Company> allCompanies = new ArrayList<Company>();
		try {
			String query = "SELECT * FROM Company";
			Statement st;
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				Company company= new Company();
				String compId = rs.getString("id");
				String compName = rs.getString("comp_name");
				String compPass = rs.getString("Password");
				String compEmail = rs.getString("email");
				
				company.setId(Long.parseLong(compId));
				company.setComp_name(compName);
				company.setPassword(compPass);
				company.setEmail(compEmail);
				
				allCompanies.add(company);
				
			}

		} catch (SQLException e) {
			throw new SQLException("Cannot get companies data from BD");
		}

		pool.returnConnection(con);
		return allCompanies;

	}

	
	public Collection<Coupon> getCoupons() throws Exception {
		CouponDBDAO couponDBDao= new CouponDBDAO();
		 return couponDBDao.getAllCoupons();
	}

	@Override
	public boolean login(String compName, String password) throws Exception {

		Connection con = pool.getConnection();
		boolean passwordMatchcompName=true;
		try {
			String query = "SELECT* FROM Company WHERE  comp_name='compName';";
			Statement st;
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			Customer customer = new Customer();
			while (rs.next()){
				String customer_Password = rs.getString("Password");
				customer.setPassword(customer_Password);
			}
			passwordMatchcompName= false;
			if(customer.getPassword()!=password){
				passwordMatchcompName= false;
				
			}
			else{
				passwordMatchcompName= true;
			}
		} catch (SQLException e) {
			throw new Exception("Cannot login compName to BD");
		}
		
		pool.returnConnection(con);
		return passwordMatchcompName;
	}

	public boolean isBelongToCompany (long compId, long couponId) throws Exception {
		Connection con = pool.getConnection();
		
		try {
			String query = "SELECT company_id FROM company_coupon WHERE `company_id` = '" + compId + "&& 'coupon_id="+ couponId +"'";
			
			Statement st;
			st = con.createStatement();
			st.executeUpdate(query);
			ResultSet rs = st.executeQuery(query);
			return (rs.next());
			
			
		} catch (Exception e) {
			throw new Exception("cannot select Coupon from DB");
		}
		
		finally {
	 	pool.returnConnection(con);
		}
	
	}
	
}
