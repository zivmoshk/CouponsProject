package com.project.CouponsProject.Beans;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Company {
	public Long id;
	public String comp_name;
	public String Password;
	public String email;
	public Collection<Coupon>coupons;
	
	//constructor
	public Company(){
	}
	public Company( String comp_name,String Password, String email){
		this.comp_name=comp_name;
		this.Password= Password;
		this.email=email;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getComp_name() {
		return comp_name;
	}
	public void setComp_name(String comp_name) {
		this.comp_name = comp_name;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString(){
		return "Company [id="+ id +",comp_name="+ comp_name +"]";
		
	}
	public Collection<Coupon> getCoupons() {
		return coupons;
	}
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}
	
}
