package com.project.CouponsProject.Facade;

import com.project.CouponsProject.Dao.CompanyDAO;
import com.project.CouponsProject.Dao.ConnectionPool;
import com.project.CouponsProject.Dao.CouponDAO;
import com.project.CouponsProject.Dao.CustomerDAO;


public class CouponSystemSingelton  {

	private CouponDAO couponDAO;
	private CustomerDAO customerDAO;
	private CompanyDAO companyDAO;
	private ConnectionPool pool;
	DailyCouponExpirationTask task=new DailyCouponExpirationTask();
	private static CouponSystemSingelton instance = null;
	
	public CouponSystemSingelton(CouponDAO couponDAO,CustomerDAO customerDAO, CompanyDAO companyDAO){
		this.couponDAO= couponDAO;
		this.customerDAO=customerDAO;
		this.companyDAO=companyDAO;
		
	}
	
	public static CouponSystemSingelton getInstance() {
		if (instance == null) {
			instance = new CouponSystemSingelton();
		}
		return instance;
	}
	public CouponSystemSingelton(){
		
		pool = ConnectionPool.getInstance();
		task.run();
		
	}
	
	
	public CouponClientFacade login(String name,String Password,ClientType clientType){
		
		CouponClientFacade facade;
		
		// applying to the right facade by the client TYPE
		if (clientType.name().equals("ADMIN")){
			facade = new AdminFacade();		
		}		
		else if (clientType.name().equals("COMPANY")){
			facade = new CompanyFacade();
		}
		
		else if (clientType.name().equals("CUSTOMER")){
			facade = new CustomerFacade();
		}
		else {
			facade = null;
			System.out.println("The CLIENT TYPE must be: ADMIN or COMPANY or CUSTOMER only!");
		}
		
		return facade;	
	}
	
	
	

	public void shutdown(){
		
		DailyCouponExpirationTask.stopTask();
		pool.closeAllConections();
		
	}
	
}
