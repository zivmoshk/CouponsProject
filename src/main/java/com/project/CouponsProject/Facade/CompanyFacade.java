package com.project.CouponsProject.Facade;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

import com.project.CouponsProject.Beans.Company;
import com.project.CouponsProject.Beans.Coupon;
import com.project.CouponsProject.Beans.CouponType;
import com.project.CouponsProject.Dao.Impl.CompanyDBDAO;
import com.project.CouponsProject.Dao.Impl.CouponDBDAO;

public class CompanyFacade implements CouponClientFacade {

	public CompanyDBDAO compDao;
	public CouponDBDAO coupDao;
	public Company company;

	public CompanyFacade() {
		this.coupDao = new CouponDBDAO();
		this.compDao = new CompanyDBDAO();
		company = new Company();

	}

	public void createCoupon(Coupon coupon) throws SQLException, Exception {
		this.company = this.coupDao.checkcompany(company.getComp_name());
		this.coupDao.createCoupon(coupon);
		this.coupDao.createCouponCompany(coupon, company);
	}

	public void removeCoupon(Coupon coupon) throws SQLException {
		this.company = this.coupDao.checkcompany(company.getComp_name());
		this.coupDao.removeCoupon(coupon);
	}

	public void updateCoupon(Coupon coupon) throws SQLException {
		this.company = this.coupDao.checkcompany(company.getComp_name());
		this.coupDao.updateCoupon(coupon);
	}

	public void getCompany(Long id) throws Exception {
		this.company = this.coupDao.checkcompany(company.getComp_name());
		this.compDao.getCompany((company.getId()));
	}

	public void getAllCoupon() throws Exception {
		this.company = this.coupDao.checkcompany(company.getComp_name());
		this.coupDao.getAllCoupons();
	}

	public Collection<Coupon> getAllCouponByType(CouponType couponType, long compId) throws Exception {
		this.company = this.coupDao.checkcompany(company.getComp_name());
		return this.coupDao.getCompanyCouponsByType(couponType, compId);
	}

	public Collection<Coupon> getAllCompanyCoupons(long id) throws Exception {
		this.company = this.coupDao.checkcompany(company.getComp_name());
		return this.coupDao.getAllCompanyCoupons(id);
	}

	public void getCompanyCouponsByPrice(int price) throws SQLException, ParseException {
		this.company = this.coupDao.checkcompany(company.getComp_name());
		this.coupDao.getCompanyCouponsByPrice(price, this.company.getId());
	}

	public void getCompanyCouponsByDate(Date date) throws SQLException, ParseException {
		this.company = this.coupDao.checkcompany(company.getComp_name());
		this.coupDao.getCompanyCouponsByDate(date, this.company.getId());
	}

	public CouponClientFacade login(String name, String password, ClientType clientType)
			throws Exception, SQLException {

		if (clientType.equals(ClientType.COMPANY)) {
			if (compDao.login(name, password)) {
				System.out.println("login complete");
				company.setComp_name(name);
				return this;
			}
		}

		throw new Exception("cannot login, please try again");
	}
	
	public Company getCompanyName() {
		return company;
	}

	public Coupon getCoupon(long id) throws SQLException, Exception  {
		// checking if the given coupon belongs to the logged in Company
		Company company = getCompanyName();
		Coupon coupon = null;
		
		if (compDao.isBelongToCompany(company.getId(), id)){
			
			coupon = coupDao.getCoupon(id);
		}
		
		else {
			System.err.println("YOU DON'T ALLOWED TO SEE THIS COUPON DETAILS, id = " + id);
		}
		
		return coupon;
	}
}
