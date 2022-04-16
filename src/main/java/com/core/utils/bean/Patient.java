package com.core.utils.bean;

public class Patient {

	private String name;
	private String email;
	private long id;

	public Patient() {
		super();
	}

	public Patient(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}

	public Patient(String name, String email, long id) {
		super();
		this.name = name;
		this.email = email;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
