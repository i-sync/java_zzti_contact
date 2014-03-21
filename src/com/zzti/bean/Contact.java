package com.zzti.bean;

import java.io.Serializable;

public class Contact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5686236415539469634L;

	public Contact() {
	}

	public Contact(String name, int cid, String cname, String phone,
			String email, String living, String company, String remark) {
		super();
		this.name = name;
		this.cid = cid;
		this.cname = cname;
		this.phone = phone;
		this.email = email;
		this.living = living;
		this.company = company;
		this.remark = remark;
	}

	public Contact(int id, String name, int cid, String cname, String phone,
			String email, String living, String company, String remark) {
		this(name, cid, cname, phone, email, living, company, remark);
		this.id = id;
	}

	private int id;
	private String name;
	private int cid;
	private String cname;
	private String phone;
	private String email;
	private String living;
	private String company;
	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLiving() {
		return living;
	}

	public void setLiving(String living) {
		this.living = living;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
