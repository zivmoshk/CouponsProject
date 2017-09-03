package com.project.CouponsProject.Dao.Impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.project.CouponsProject.Beans.Company;
import com.project.CouponsProject.Beans.Coupon;
import com.project.CouponsProject.Beans.CouponType;
import com.project.CouponsProject.Dao.ConnectionPool;
import com.project.CouponsProject.Dao.CouponDAO;

public class CouponDBDAO implements CouponDAO {

	private ConnectionPool pool;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
	

	public CouponDBDAO() {

		pool = ConnectionPool.getInstance();
	}
	//singleton
	private static CouponDBDAO instance= null;
	public static CouponDBDAO getInstance(){
		
		if (instance == null) {
			instance = new CouponDBDAO();
		}
		
		return instance;
	}
	@Override
	public void createCoupon(Coupon coupon) throws SQLException, Exception {
		Connection con = pool.getConnection();
		String couponTitle = coupon.getTitle();
		if(!isTitleExsist(couponTitle)) {
		try {
			String query = "INSERT INTO Coupon (title, start_date, end_date, amount, type, message, price, image)"
					+ "VALUES ('" + "','" + coupon.getTitle() + "','"
					+ format.format(coupon.getStart_date()) + "','" + format.format(coupon.getEnd_date()) + "','"
					+ coupon.getAmount() + "','" + coupon.getType().name() + "','" + coupon.getMessage() + "','"
					+ coupon.getPrice() + "','" + coupon.getImage() + "');";

			Statement st;
			st = con.createStatement();
			st.executeUpdate(query);
			System.out.println("Coupon has been created");
		} catch (SQLException e) {
			throw new SQLException("Cannot insert coupon data into DB");
		}

		pool.returnConnection(con);
		}
	}
	@Override
	public void removeCoupon(Coupon coupon) throws SQLException {

		Connection con = pool.getConnection();
		try {
			// deleting the coupon from the COMPANY_COUPON JOIN table
			String deleteFromCompany_Coupon = "DELETE FROM company_coupon WHERE coupon_id=" + coupon.getId() + ";";
			// deleting the coupon from the CCUSTOMER_COUPON JOIN table
			String deleteFromCustomer_Coupon = "DELETE FROM customer_coupon WHERE coupon_id=" + coupon.getId() + ";";
			// deleting the coupon from the COUPON table
			String deleteFromCoupons = "DELETE FROM coupon WHERE id=" + coupon.getId() + ";";
			Statement st;
			st = con.createStatement();
			st.executeUpdate(deleteFromCompany_Coupon);
			st.executeUpdate(deleteFromCustomer_Coupon);
			st.executeUpdate(deleteFromCoupons);
			System.out.println("Coupon deleted from system");
		} catch (SQLException e) {
			throw new SQLException("Cannot delete coupon from BD");
		}

		pool.returnConnection(con);
	}
	@Override
	public void updateCoupon(Coupon coupon) throws SQLException {
		Connection con = pool.getConnection();

		try {

			String query = "UPDATE Coupon SET title='" + coupon.getTitle() + "', start_Date='"
					+ format.format(coupon.getStart_date()) + "', end_Date='" + format.format(coupon.getEnd_date())
					+ "', amount='" + coupon.getAmount() + "', type='" + coupon.getType().name() + "', message='"
					+ coupon.getMessage() + "', price='" + coupon.getPrice() + "', image='" + coupon.getImage()
					+ "' WHERE id='" + coupon.getId() + "';";
			Statement st;
			st = con.createStatement();
			st.executeUpdate(query);
			System.out.println("Coupon has been updated");
		} catch (SQLException e) {
			throw new SQLException("Cannot update coupon data into BD");
		}

		pool.returnConnection(con);

	}
	@Override
	public Coupon getCoupon(long id) throws SQLException, Exception {
		Connection con = pool.getConnection();
		Coupon coupon = new Coupon();
		try {
			String query = "SELECT * FROM coupon WHERE id=" + id + ";";
			Statement st;
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
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
				//DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
				Date Start_date = format.parse(coupon_Start_date);
				coupon.setStart_date(Start_date);
				Date End_date = format.parse(coupon_End_date);
				coupon.setEnd_date(End_date);
				coupon.setAmount(Integer.parseInt(coupon_Amount));
				coupon.setType(CouponType.valueOf(coupon_type));
				coupon.setMessage(coupon_message);
				coupon.setPrice(Double.parseDouble(coupon_price));
				coupon.setImage(coupon_image);
			}

		} catch (SQLException e) {
			throw new SQLException("Cannot select coupon data from BD");
		}

		pool.returnConnection(con);
		// For debug
		// System.out.println(coupon.toString());
		return coupon;

	}
	public void createCouponCompany(Coupon coupon, Company company) throws SQLException {
		Connection con = pool.getConnection();

		try {

			String query = "INSERT INTO COMPANY_COUPON (COMPANY_ID,COUPON_ID)" + "VALUES ('" + company.getId() + "','"
					+ coupon.getId() + "');";

			Statement st;
			st = con.createStatement();
			st.executeUpdate(query);
			System.out.println("CompanyCoupon has been created");
		} catch (SQLException e) {
			throw new SQLException("Cannot insert couponcompany data into DB");
		}

		pool.returnConnection(con);

	}
	@Override
	public Collection<Coupon> getAllCompanyCoupons(Long comp_id) throws Exception {
		Connection con = pool.getConnection();
		ArrayList<Coupon> companyCoupons = new ArrayList<Coupon>();
		String query = "SELECT * FROM Company_Coupon Join Coupon ON Company_Coupon.COUPON_ID = COUPON.ID WHERE Company_Coupon.COMPANY_ID = " + comp_id + "';";

		Statement st;
		st = con.createStatement();
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			// creating a new Coupon object to catch the rs results.
			Coupon coupon = new Coupon();

			// setting the object's fields
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
			//DateFormat format = new SimpleDateFormat("YYYY/MM/DD");
			Date Start_date = format.parse(coupon_Start_date);
			coupon.setStart_date(Start_date);
			Date End_date = format.parse(coupon_End_date);
			coupon.setEnd_date(End_date);
			coupon.setAmount(Integer.parseInt(coupon_Amount));
			coupon.setType(CouponType.valueOf(coupon_type));
			coupon.setMessage(coupon_message);
			coupon.setPrice(Double.parseDouble(coupon_price));
			coupon.setImage(coupon_image);

			// adding the object to the Coupons Collection
			companyCoupons.add(coupon);
		}
		return companyCoupons;


	}
	public Collection<Coupon> getCompanyCouponsByPrice(double price, Long id) throws SQLException, ParseException {
		Connection con = pool.getConnection();
		ArrayList<Coupon> couponsByPrice = new ArrayList<Coupon>();
		String query = "SELECT * FROM Company_Coupon Join Coupon ON Company_Coupon.COUPON_ID = COUPON.ID WHERE Company_Coupon.COMPANY_ID = " + id + " and COUPON.PRICE <= '"+ price +"';";

		Statement st;
		st = con.createStatement();
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			// creating a new Coupon object to catch the rs results.
			Coupon coupon = new Coupon();

			// setting the object's fields
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
			//DateFormat format = new SimpleDateFormat("YYYY/MM/DD");
			Date Start_date = format.parse(coupon_Start_date);
			coupon.setStart_date(Start_date);
			Date End_date = format.parse(coupon_End_date);
			coupon.setEnd_date(End_date);
			coupon.setAmount(Integer.parseInt(coupon_Amount));
			coupon.setType(CouponType.valueOf(coupon_type));
			coupon.setMessage(coupon_message);
			coupon.setPrice(Double.parseDouble(coupon_price));
			coupon.setImage(coupon_image);

			// adding the object to the Coupons Collection
			couponsByPrice.add(coupon);
		}
		return couponsByPrice;

	}
	public Collection<Coupon> getCompanyCouponsByDate(Date date, Long id) throws SQLException, ParseException {
			Connection con = pool.getConnection();
			ArrayList<Coupon> couponsByDate = new ArrayList<Coupon>();
			String query = "SELECT * FROM Company_Coupon Join Coupon ON Company_Coupon.COUPON_ID = COUPON.ID WHERE Company_Coupon.COMPANY_ID = " + id + " and COUPON.END_DATE <= '"+ date +"';";

			Statement st;
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				// creating a new Coupon object to catch the rs results.
				Coupon coupon = new Coupon();

				// setting the object's fields
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
				//DateFormat format = new SimpleDateFormat("YYYY/MM/DD");
				Date Start_date = format.parse(coupon_Start_date);
				coupon.setStart_date(Start_date);
				Date End_date = format.parse(coupon_End_date);
				coupon.setEnd_date(End_date);
				coupon.setAmount(Integer.parseInt(coupon_Amount));
				coupon.setType(CouponType.valueOf(coupon_type));
				coupon.setMessage(coupon_message);
				coupon.setPrice(Double.parseDouble(coupon_price));
				coupon.setImage(coupon_image);

				// adding the object to the Coupons Collection
				couponsByDate.add(coupon);
			}
			return couponsByDate;		
	}
	
	public Company checkcompany(String comp_name) throws SQLException {
		Connection con = pool.getConnection();
		Company comp = new Company();
		try {
			String query = "SELECT * FROM Company WHERE COMP_NAME = '" + comp_name + "';";
			Statement st;
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("id");
				comp.setId(Long.parseLong(id));
			}

		} catch (SQLException e) {
			throw new SQLException("Company doesnt exist");
		}

		pool.returnConnection(con);
		return comp;

	}
	@Override
	public Collection<Coupon> getAllCoupons() throws Exception {
		Connection con = pool.getConnection();
		ArrayList<Coupon> Coupons = new ArrayList<Coupon>();
		try {
			String query = "SELECT * FROM Coupon";
			Statement st;
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next())
				try {
						Coupon coupon = new Coupon();
						String coupon_id = rs.getString("id");
						String coupon_title = rs.getString("title");
						String coupon_Start_date = rs.getString("START_DATE");
						String coupon_End_date = rs.getString("END_DATE");
						String coupon_Amount = rs.getString("Amount");
						String coupon_type = rs.getString("type");
						String coupon_message = rs.getString("message");
						String coupon_price = rs.getString("price");
						String coupon_image = rs.getString("image");

						coupon.setId(Long.parseLong(coupon_id));
						coupon.setTitle(coupon_title);
						//DateFormat format = new SimpleDateFormat("yyyy/mm/dd");
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
					
				} catch (Exception e) {
					throw new Exception("Cannot select Coupons data from BD");
				}

		} catch (SQLException e) {
			throw new SQLException("Cannot select Coupons data from BD");
		}
		pool.returnConnection(con);
		return Coupons;
	}
	@Override
	public Collection<Coupon> getCouponsByType(CouponType type) throws SQLException, Exception {
		Connection con = pool.getConnection();
		System.out.println("connected");
		ArrayList<Coupon> couponsByType = new ArrayList<Coupon>();
		String query = "SELECT * FROM coupon WHERE `type` = '" + type + "'";
		Statement st;
		st = con.createStatement();
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			// creating a new Coupon object to catch the rs results.
			Coupon coupon = new Coupon();

			// setting the object's fields
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
			//DateFormat format = new SimpleDateFormat("YYYY/MM/DD", Locale.ENGLISH);
			Date Start_date = format.parse(coupon_Start_date);
			coupon.setStart_date(Start_date);
			Date End_date = format.parse(coupon_End_date);
			coupon.setEnd_date(End_date);
			coupon.setAmount(Integer.parseInt(coupon_Amount));
			coupon.setType(CouponType.valueOf(coupon_type));
			coupon.setMessage(coupon_message);
			coupon.setPrice(Double.parseDouble(coupon_price));
			coupon.setImage(coupon_image);

			// adding the object to the Coupons Collection
			couponsByType.add(coupon);

		}
		return couponsByType;

	}
	public Collection<Coupon> getCouponsByPrice(double price) throws SQLException, ParseException {
		


		Connection con = pool.getConnection();
		System.out.println("connected");
		ArrayList<Coupon> couponsByPrice = new ArrayList<Coupon>();
		String query = "SELECT * FROM coupon WHERE `Price` <= '" + price + "'";
		Statement st;
		st = con.createStatement();
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			// creating a new Coupon object to catch the rs results.
			Coupon coupon = new Coupon();

			// setting the object's fields
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
			//DateFormat format = new SimpleDateFormat("YYYY/MM/DD", Locale.ENGLISH);
			Date Start_date = format.parse(coupon_Start_date);
			coupon.setStart_date(Start_date);
			Date End_date = format.parse(coupon_End_date);
			coupon.setEnd_date(End_date);
			coupon.setAmount(Integer.parseInt(coupon_Amount));
			coupon.setType(CouponType.valueOf(coupon_type));
			coupon.setMessage(coupon_message);
			coupon.setPrice(Double.parseDouble(coupon_price));
			coupon.setImage(coupon_image);

			// adding the object to the Coupons Collection
			couponsByPrice.add(coupon);

		}
		return couponsByPrice;

	}
	public Collection<Coupon> getCompanyCouponsByType(CouponType type, Long compId) throws SQLException, Exception {
		
		Collection<Coupon> Coupons = new ArrayList<Coupon>();
		Connection con = pool.getConnection();
		try {
			
			String query = "SELECT * FROM Company_Coupon Join Coupon ON Company_Coupon.COUPON_ID = COUPON.ID WHERE Company_Coupon.COMPANY_ID = " + compId + " and COUPON.TYPE = '"+ type +"';";
			Statement st;
			st = con.createStatement();
			
			
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				Coupon coupon = new Coupon();
				
				String coup_id = rs.getString("COUPON_ID");
				coupon.setId(Long.parseLong(coup_id));
				Coupons.add(coupon);
			}
			
		} catch (Exception e) {
			throw new Exception("Cannot select Coupons data from BD");
		}
		
		pool.returnConnection(con);
		return Coupons;
	}
	
	public boolean isTitleExsist (String title) throws Exception {
		Connection con = pool.getConnection();
		
		try {
			String query = "SELECT * FROM coupon WHERE `title` = '" + title + "'";

			Statement st;
			st = con.createStatement();
			st.executeUpdate(query);
			ResultSet rs = st.executeQuery(query);
			return (rs.next());
		
		} catch (Exception e) {
			throw new Exception("cannot select Coupon title from DB");
		}
		
		finally {
	 	pool.returnConnection(con);
		}
	}

		
	}
	
