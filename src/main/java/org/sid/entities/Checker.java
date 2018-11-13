package org.sid.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Checker {
	
	@Id @GeneratedValue
	private long id;
	private String code;
	private String email;
	public Checker(String email) {
		super();
		this.code = Integer.toString((int)(Math.random()*9000)+1000);
		this.email = email;
	}
	public Checker() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
