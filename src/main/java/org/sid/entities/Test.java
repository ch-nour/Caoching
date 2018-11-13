package org.sid.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Test implements Serializable{
	
	@Id 
	private String id;
	private String titre;
	private String sujet;
	private String type;
	private Date date;
	@ManyToOne
	@JoinColumn(name="CODE_FORM")
	private Formateur formateur;
	@ElementCollection
	private List<String> question;


	public Test(String id, String titre, String sujet, String type, Date date, Formateur formateur,
			List<String> question) {
		super();
		this.id = id;
		this.titre = titre;
		this.sujet = sujet;
		this.type = type;
		this.date = date;
		this.formateur = formateur;
		this.question = question;
	}




	public String getSujet() {
		return sujet;
	}




	public void setSujet(String sujet) {
		this.sujet = sujet;
	}




	public Test() {
		super();
	}




	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTitre() {
		return titre;
	}


	public void setTitre(String titre) {
		this.titre = titre;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}



	public Formateur getFormateur() {
		return formateur;
	}


	public void setFormateur(Formateur formateur) {
		this.formateur = formateur;
	}



	
	public List<String> getQuestion() {
		return question;
	}




	public void setQuestion(List<String> question) {
		this.question = question;
	}




	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}
	
	
	
	

}
