package org.sid.entities;

import java.util.Collection;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Entity
@DiscriminatorValue("f")
public class Formateur extends Personne{
	
	private String profession;
	@OneToMany(mappedBy="id") 
	private Collection<Test> tests;
	private String password;
	private boolean active;
	
	public Formateur() {
		super();
	}
	
	
	
	public Formateur(String nom, String prenom, String tel, String email, String adresse, String profession,
			Collection<Test> tests, String password, boolean active) {
		super(nom, prenom, tel, email, adresse);
		this.profession = profession;
		this.tests = tests;
		this.password = password;
		this.active = active;
	}



	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public Collection<Test> getTests() {
		return tests;
	}
	public void setTests(Collection<Test> tests) {
		this.tests = tests;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
	

}
