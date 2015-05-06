package com.zzti.bean;

import java.util.Date;

public class Page {
	private Date stratDate;
	private Date endDate;
	private int pageIndex;
	private int pageSize;
	private int totalCount;
	
	
	public Page() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Page(Date stratDate, Date endDate, int pageIndex, int pageSize) {
		super();
		this.stratDate = stratDate;
		this.endDate = endDate;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}
	public Date getStratDate() {
		return stratDate;
	}
	public void setStratDate(Date stratDate) {
		this.stratDate = stratDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
}
