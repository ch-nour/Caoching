package org.sid.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("c")
public class Candidat extends Personne {
	
	public Candidat(String nom, String prenom, String tel, String email, String adresse) {
		super(nom, prenom, tel, email, adresse);
	}

	public Candidat() {
		super();
	}




	
	
}
