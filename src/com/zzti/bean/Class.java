package com.zzti.bean;

import java.io.Serializable;

public class Class implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3945634170427268146L;

	public Class() {
	}

	public Class(String name, String vocational) {
		this.name = name;
		this.vocational = vocational;
	}

	public Class(int id, String name, String vocational) {
		this(name, vocational);
		this.id = id;
	}

	private int id;
	private String name;
	private String vocational;

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

	public String getVocational() {
		return vocational;
	}

	public void setVocational(String vocational) {
		this.vocational = vocational;
	}

}
