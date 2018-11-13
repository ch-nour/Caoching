package org.sid.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Resultat implements Serializable{
	
	@Id @GeneratedValue
	private Long id;
	private String observation;
	private String type;
	private Date date;
	@ManyToOne
	@JoinColumn(name="CODE_TEST")
	private Test test;
	@ManyToOne
	@JoinColumn(name="CODE_CANDIDAT")
	private Candidat candidat;
	@ElementCollection
	private List<String> reponses;
	
	
	public Resultat(String observation, String type,Date date, Test test, Candidat candidat, List<String> reponses) {
		super();
		this.observation = observation;
		this.type = type;
		this.date = date;
		this.test = test;
		this.candidat = candidat;
		this.reponses = reponses;
	}
	
	public Resultat() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public Test getTest() {
		return test;
	}
	public void setTest(Test test) {
		this.test = test;
	}
	public Candidat getCandidat() {
		return candidat;
	}
	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public List<String> getReponses() {
		return reponses;
	}

	public void setReponses(List<String> reponses) {
		this.reponses = reponses;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	

}
