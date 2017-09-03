package com.project.CouponsProject.Facade;


public interface CouponClientFacade {

	// Abstract method login
	// successful login should return ClientFacade object
	public CouponClientFacade login(String custName, String password, ClientType clientType) throws Exception;
	

}
