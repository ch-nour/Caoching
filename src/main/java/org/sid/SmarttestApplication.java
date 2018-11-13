package org.sid;

import java.util.ArrayList;
import java.util.Date;

import org.sid.DAO.PersonneRepository;
//import org.sid.DAO.QuestionsRepository;
import org.sid.DAO.ResultatRepository;
import org.sid.DAO.TestRepository;
import org.sid.entities.Candidat;
import org.sid.entities.Formateur;
import org.sid.entities.Personne;
//import org.sid.entities.Question;
import org.sid.entities.Resultat;
import org.sid.entities.Test;
import org.sid.metier.IMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmarttestApplication implements CommandLineRunner{

	@Autowired
	private PersonneRepository personneRepository; 
	@Autowired
	private TestRepository testRepository;
	@Autowired
	private ResultatRepository resultatRepository;
//	@Autowired
//	private QuestionsRepository questionsRepository;
	@Autowired
	private IMetier iMetier;
	
	public static void main(String[] args) {
	
		SpringApplication.run(SmarttestApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		//Formateur f1 = personneRepository.save(new Formateur("Taqafi", "issam", "0655", "issam@rahmaconsulting.ma", "adresse", "Coach"));
		
		//Test t1 = testRepository.save(new Test("code1","Driver","Driver francais", "5" ,new Date(), f1,iMetier.importdata("/Users/apple/Desktop/Driver_francais.xls")));
		//Test t2 = testRepository.save(new Test("code2","Test de gordon","Test de gordon", "2" ,new Date(), f1,iMetier.importdata("/Users/apple/Desktop/test_gordon.xls")));
		//Test t3 = testRepository.save(new Test("code3","Driver Arabe","Driver Arabe", "2" ,new Date(), f1,iMetier.importdata("/Users/apple/Desktop/Driver_arabe.xls")));
		
	}
}
