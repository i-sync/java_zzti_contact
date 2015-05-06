package com.zzti.bean;

import java.io.Serializable; 

public class Contact implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8632781672251495809L;

	public Contact() {
	}

	public Contact(int id ,String name, int cid, String cname, String phone,
			String email, String living, String company, String remark ) {
		this(name, cid, cname, phone, email, living, company, remark, null,
				null, "::1");
	}
	public Contact(String name, int cid, String cname, String phone,
			String email, String living, String company, String remark,
			String addDate, String updateDate, String ip) {
		super();
		this.name = name;
		this.cid = cid;
		this.cname = cname;
		this.phone = phone;
		this.email = email;
		this.living = living;
		this.company = company;
		this.remark = remark;
		this.addDate = addDate;
		this.updateDate = updateDate;
		this.ip = ip;
	}

	public Contact(int id, String name, int cid, String cname, String phone,
			String email, String living, String company, String remark,
			String addDate, String updateDate, String ip) {
		this(name, cid, cname, phone, email, living, company, remark, addDate,
				updateDate, ip);
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
	private String addDate;
	private String updateDate;
	private String ip;

	private Page page;

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

	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}
