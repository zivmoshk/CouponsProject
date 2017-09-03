package com.project.CouponsProject.Facade;

import java.util.Collection;

import com.project.CouponsProject.Beans.Coupon;
import com.project.CouponsProject.Dao.CompanyDAO;
import com.project.CouponsProject.Dao.CouponDAO;
import com.project.CouponsProject.Dao.CustomerDAO;
import com.project.CouponsProject.Dao.Impl.CompanyDBDAO;
import com.project.CouponsProject.Dao.Impl.CouponDBDAO;
import com.project.CouponsProject.Dao.Impl.CustomerDBDAO;

public class DailyCouponExpirationTask implements Runnable {

	private CouponDAO couponDAO;
	private CustomerDAO customerDAO;
	private CompanyDAO companyDAO;
	private com.project.CouponsProject.Dao.Impl.DataVallidation dataVall;
	 static boolean quit;

	public DailyCouponExpirationTask() {
		couponDAO= CouponDBDAO.getInstance();
		customerDAO=CustomerDBDAO.getInstance();
		companyDAO= CompanyDBDAO.getInstance();
	}

	@Override
	public void run() {
		try {
			Collection<Coupon> Coupons = couponDAO.getAllCoupons();
			while (Coupons.iterator().hasNext()) {
				if (dataVall.couponDateIsVallid(Coupons.iterator().next()) == false) {
					couponDAO.removeCoupon(Coupons.iterator().next());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void stopTask() {
		quit = true;
		System.out.println("STOPING THE DAILY TASK");

	}

}
