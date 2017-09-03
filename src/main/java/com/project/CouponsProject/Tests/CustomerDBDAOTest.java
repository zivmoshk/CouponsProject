package com.project.CouponsProject.Tests;

import java.sql.SQLException;

import com.project.CouponsProject.Beans.Customer;
import com.project.CouponsProject.Dao.Impl.CustomerDBDAO;

public class CustomerDBDAOTest {

	public static void main(String[] args) throws Exception {
		 create();
		// remove();
		//update();

	}

	public static void create() throws Exception {
		//Customer cust1 = new Customer(123456L, "Ziv", "12345QAZ");
		//Customer cust2 = new Customer(98745L, "Dvir", "QAZWSX12");
		CustomerDBDAO custDao = new CustomerDBDAO();
		String cust_name="ZZiv";
		String password="123";
		custDao.login(cust_name,password);

	}

	public static void remove() throws SQLException {
		Customer cust1 = new Customer( "Ziv", "12345QAZ");
		Customer cust2 = new Customer( "Dvir", "QAZWSX12");
		CustomerDBDAO custDao = new CustomerDBDAO();
		custDao.removeCustomer(cust2);
	}
	
	public static void update() throws SQLException {
		Customer cust1 = new Customer( "Aya", "12345QAZ");
		Customer cust2 = new Customer( "Aya", "QAZWSX12");
		CustomerDBDAO custDao = new CustomerDBDAO();
		custDao.updateCustomer(cust1);

	}
}
