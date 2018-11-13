package org.sid.entities;

import java.io.Serializable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE_USER",discriminatorType=DiscriminatorType.STRING,length=1)
public abstract class Personne implements Serializable{
	@Id @GeneratedValue
	private Long id;
	private String nom, prenom, tel, email ,adresse;
	
	
	public Personne() {
		super();
	}


	public Personne(String nom, String prenom, String tel, String email, String adresse) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.tel = tel;
		this.email = email;
		this.adresse = adresse;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAdresse() {
		return adresse;
	}


	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	
	
}
