package com.project.CouponsProject.Tests;
import java.sql.SQLException;

import com.project.CouponsProject.Beans.Company;
import com.project.CouponsProject.Beans.Coupon;
import com.project.CouponsProject.Dao.Impl.CompanyDBDAO;
import com.project.CouponsProject.Dao.Impl.CouponDBDAO;
import com.project.CouponsProject.Facade.AdminFacade;
import com.project.CouponsProject.Facade.ClientType;

public class Test {
	
	public static void main(String[] args) throws Exception {
//		remove();
   //	create();
//		update();
		getcomp();
	//getAllComp();
		
	//removeCompanyFacade();
	//	loginAdminFacade();
	//createCompanyFacade();
	
		
		
		
	}
		private static void createCompanyFacade() throws SQLException {

			AdminFacade af=new AdminFacade();
			Company company= new Company("dvir1","123456789","a@a.com");
			af.createCompany(company);
			
	}
		private static void loginAdminFacade() {
			AdminFacade af=new AdminFacade();
			af.login("admin", "1234", ClientType.ADMIN);
		
	}
		private static void removeCompanyFacade() throws SQLException {

			AdminFacade af=new AdminFacade();
			Company company=new Company("aaaa","ssss","aaaa");
		
			af.removeComany(company);
			
			
			
	}
		private static void getAllComp() throws SQLException {
			CompanyDBDAO compDao = new CompanyDBDAO();
			compDao.getAllCompanies();
			
			
			
		
	}
		private static void getcomp() throws SQLException {
			Company comp2 = new Company("2232","36464","4");
			CompanyDBDAO compDao = new CompanyDBDAO();
			compDao.getCompany(comp2.getId());
			
	}
		public static void update() throws SQLException {
			//Company comp1 = new Company(123456L,"2","3","4");
			Company comp2 = new Company("2232","36464","4");
			CompanyDBDAO compDao = new CompanyDBDAO();
			compDao.updateCompany(comp2);
		
	}
		public static void create() throws SQLException {
		//	Company comp1 = new Company(0L,"abc212d","dddd","a@a.com");
			Company comp2 = new Company("2","3","4");
			CompanyDBDAO compDao = new CompanyDBDAO();
			compDao.createCompany(comp2);

		
	}
		public static void remove() throws SQLException{
		//Company comp1 = new Company(123456L,"2","3","4");
		Company comp2 = new Company("2","3","4");
		CompanyDBDAO compDao = new CompanyDBDAO();
		compDao.removeCompany(comp2);
		}
	
	}


