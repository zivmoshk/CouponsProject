package com.project.CouponsProject.Dao.Impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import com.project.CouponsProject.Beans.Coupon;
import com.project.CouponsProject.Beans.CouponType;
import com.project.CouponsProject.Beans.Customer;
import com.project.CouponsProject.Dao.ConnectionPool;
import com.project.CouponsProject.Dao.CustomerDAO;

public class CustomerDBDAO implements CustomerDAO {
	private ConnectionPool pool;

	public CustomerDBDAO() {
		pool = ConnectionPool.getInstance();
	}
	
	//singleton
	private static CustomerDBDAO instance= null;
	public static CustomerDBDAO getInstance(){
		
		if (instance == null) {
			instance = new CustomerDBDAO();
		}
		
		return instance;
	}


	@Override
	public void createCustomer(Customer customer) throws SQLException {
		Connection con = pool.getConnection();

		try {
			String query = "INSERT INTO Customer (Cust_name, Password)" + "VALUES ('" + "','"
					+ customer.getCust_name() + "','" + customer.getPassword() + "');";

			Statement st;
			st = con.createStatement();
			st.executeUpdate(query);
			System.out.println("Customer has been created");
		} catch (SQLException e) {
			throw new SQLException("Cannot create customer data into BD");
		}

		pool.returnConnection(con);

	}

	@Override
	public void removeCustomer(Customer customer) throws SQLException {
		Connection con = pool.getConnection();
		try {
			String query = "DELETE FROM Customer WHERE id=" + customer.getId() + ";";
			Statement st;
			st = con.createStatement();
			st.executeUpdate(query);
			System.out.println("customer has been deleted");
		} catch (SQLException e) {
			throw new SQLException("Cannot delete customer data into BD");
		}

		pool.returnConnection(con);

	}

	@Override
	public void updateCustomer(Customer customer) throws SQLException {
		Connection con = pool.getConnection();

		try {

			String query = "UPDATE Customer SET comp_name=" + customer.getCust_name() + ", Password="
					+ customer.getPassword() + " WHERE id=" + customer.getId() + ";";
			Statement st;
			st = con.createStatement();
			st.executeUpdate(query);
			System.out.println("customer has been updated");
		} catch (SQLException e) {
			throw new SQLException("Cannot update customer data into BD");
		}

		pool.returnConnection(con);

	}

	@Override
	public Customer getCustomer(long id) throws SQLException {
		Connection con = pool.getConnection();
		Customer customer;
		try {
			customer=new Customer();
			String query = "SELECT * FROM Customer WHERE id=" + id + ";";
			Statement st;
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				customer.setCust_name("cust_name");
				customer.setPassword("password");
				//String custName = rs.getString("cust_name");
				//String custPass = rs.getString("Password");
				//System.out.print(id + "| " + custName + " |" + custPass);
			}

		} catch (SQLException e) {
			throw new SQLException("Cannot select customer data from BD");
		}

		pool.returnConnection(con);
		return customer;

	}
	@Override
	public Customer getCustomerByName(String cust_name) throws Exception {
		Connection con = pool.getConnection();
		Customer customer=new Customer();
		try {
			String query = "SELECT * FROM Customer WHERE cust_name='" + cust_name + "';";
			//for debug
			//System.out.println(query);
			Statement st;
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("id");
				String custPass = rs.getString("Password");
				customer.setId(Long.parseLong(id));
				customer.setPassword(custPass);
			}

		} catch (SQLException e) {
			throw new SQLException("Cannot select customer data from BD");
		}
		pool.returnConnection(con);
		return customer;
	}

	@Override
	public Collection<Customer> getAllCustomers() throws SQLException {
		Connection con = pool.getConnection();
		ArrayList<Customer> allCustomers = new ArrayList<Customer>();
		try {
			String query = "SELECT * FROM Customer";
			Statement st;
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				Customer customer = new Customer();
				String custId = rs.getString("id");
				String custName = rs.getString("cust_name");
				String custPass = rs.getString("Password");
				customer.setId(Long.parseLong(custId));
				customer.setCust_name(custName);
				customer.setPassword(custPass);
				allCustomers.add(customer);
			}

		} catch (SQLException e) {
			throw new SQLException("Cannot get all customers data from BD");
		}
		pool.returnConnection(con);
		return allCustomers;

	}

	@Override
	public Collection<Coupon> getCoupons() throws SQLException, Exception {
		Connection con = pool.getConnection();
		ArrayList<Coupon> Coupons = new ArrayList<Coupon>();
		try {
			String query = "SELECT * FROM Coupon";
			Statement st;
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next())
				try {
					{
						Coupon coupon = new Coupon();
						String coupon_id = rs.getString("id");
						String coupon_title = rs.getString("title");
						String coupon_Start_date = rs.getString("Start_date");
						String coupon_End_date = rs.getString("End_date");
						String coupon_Amount = rs.getString("Amount");
						String coupon_type = rs.getString("type");
						String coupon_message = rs.getString("message");
						String coupon_price = rs.getString("price");
						String coupon_image = rs.getString("image");

						coupon.setId(Long.parseLong(coupon_id));
						coupon.setTitle(coupon_title);
						DateFormat format = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH);
						Date Start_date = format.parse(coupon_Start_date);
						coupon.setStart_date(Start_date);
						Date End_date = format.parse(coupon_End_date);
						coupon.setEnd_date(End_date);
						coupon.setAmount(Integer.parseInt(coupon_Amount));
						coupon.setType(CouponType.valueOf(coupon_type));
						coupon.setMessage(coupon_message);
						coupon.setPrice(Double.parseDouble(coupon_price));
						coupon.setImage(coupon_image);

						Coupons.add(coupon);
					}
				} catch (Exception e) {
					throw new Exception("Cannot select Coupons data from BD");
				}

		} catch (SQLException e) {
			throw new SQLException("Cannot select Coupons data from BD");
		}
		pool.returnConnection(con);
		return Coupons;

	}
	// the function returns collection of all the  id coupon the customer own
	public Collection<Coupon> getCouponsByCustomer(Customer costomer) throws SQLException, Exception {
		Connection con = pool.getConnection();
		ArrayList<Coupon> customerCouponsID = new ArrayList<Coupon>();
		try {
			String query = "SELECT * FROM customer_coupon WHERE cust_id=" + costomer.getId() + ";";
			Statement st;
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next())
				try {
						Coupon coupon = new Coupon();
						String coupon_id = rs.getString("id");
						coupon.setId(Long.parseLong(coupon_id));
						customerCouponsID.add(coupon);
							
				} catch (Exception e) {
					throw new Exception("Cannot select Coupons data from BD");
				}

		} catch (SQLException e) {
			throw new SQLException("Cannot select Coupons data from BD");
		}
		pool.returnConnection(con);
		return customerCouponsID;
	}


	// updates customer_coupon table  
	public void UpdateCustomer_CouponTable(Customer customer, Coupon coupon) throws SQLException {
		Connection con = pool.getConnection();
		try {
			String query = "INSERT INTO Customer_Coupon (Cust_ID, Coupon_ID)" + "VALUES('" + customer.getId() + "','"
					+ coupon.getId() + "');";
			Statement st;
			st = con.createStatement();
			st.executeUpdate(query);
		

			System.out.println("Customer_Coupon table has been updated");
		} catch (SQLException e) {
			throw new SQLException("Cannot update customer_coupon data into DB");
			
		}

		pool.returnConnection(con);

	}

	public void RemoveCustomer_CouponTable(Coupon coupon) throws SQLException {
		Connection con = pool.getConnection();
		try {
			String query = "REMOVE *FROM Customer_Coupon WHERE id=" + coupon.getId() + ";";
			Statement st;
			st = con.createStatement();
			st.executeUpdate(query);
			System.out.println("Customer_Coupon toble has been updated");
		} catch (SQLException e) {
			throw new SQLException("Cannot update customer_coupon data into BD");
		}

		pool.returnConnection(con);

	}

	@Override
	public boolean login(String custName, String password) throws Exception {
		Connection con = pool.getConnection();
		boolean passwordMatchcustName=true;
		try {
			String query = "SELECT* FROM Customer WHERE cust_name='" + custName + "';";
			Statement st;
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				String customer_Password = rs.getString("Password");
				if (customer_Password.equals(password)) {
					passwordMatchcustName = true;
				} else {
					passwordMatchcustName = false;
				}
			}
		} catch (SQLException e) {
			throw new Exception("Cannot login custName to BD");
		}

		pool.returnConnection(con);
		return passwordMatchcustName;
	}


	

}
