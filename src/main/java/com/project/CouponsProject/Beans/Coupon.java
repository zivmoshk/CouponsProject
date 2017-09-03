package com.project.CouponsProject.Beans;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Coupon {
	private Long id;
	private String title;
	private Date start_date;
	private Date end_date;
	private Integer amount;
	private CouponType type;
	private String message;
	private Double price;
	private String image;
	
	//constructor
	 public Coupon(){
	}
	
	public Coupon( String title, Date start_date, Date end_date, int amount,CouponType type,String message,double price,String image){
		this.title=title;
		this.start_date=start_date;
		this.end_date =end_date;
		this.amount=amount;
		this.type=type;
		this.message= message;
		this.price=price;
		this.image=image;
			
	}
	
	
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public CouponType getType() {
		return type;
	}
	public void setType(CouponType type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	public String toString(){
		return "Coupon [id="+ id +",title="+ title +",start_date="+ start_date +",end_date="+ end_date +",amount="+ amount +","
				+ "type="+ type +",message="+ message +",price="+ price +",image="+ image +"]";
		
	}	
	
}
