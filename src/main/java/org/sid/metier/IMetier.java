package org.sid.metier;

import java.util.List;

import org.json.JSONException;
//import org.json.JSONException;
import org.sid.entities.Candidat;
import org.sid.entities.Formateur;
import org.sid.entities.Personne;
//import org.sid.entities.Question;
import org.sid.entities.Resultat;
import org.sid.entities.Test;
//import org.sid.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;

//import com.mailjet.client.errors.MailjetException;
//import com.mailjet.client.errors.MailjetSocketTimeoutException;

public interface IMetier {
	
	public Test consulterTest(String code);
	public void ajouterTest(Test test);
	public void supprimerTest(String codeTest);
	public void supprimerResultat(Long codeResultat);
	public void supprimerCoach(Long codeCoach);
	public void supprimerCandidat(Long codeCandidat);

	public void genererTest(String sujet,int nbrQuestion,boolean timed,double duree);
	public void ajouterCandidat(Candidat candidat);
	public void ajouterFormateur(Formateur formateur);
	public void ajouterResultat(Resultat resultat);
	public Page<Resultat> listResultat(int page,int size);
	public Page<Test> listTest(int page,int size);
	//public Page<Question> listQuestion(int page,int size);
	public Page<Candidat> listCandidat(int page,int size);
	public Page<Formateur> listFormateur(int page,int size);
	
	//public Page<Users> listUsers(int page,int size);
	
	public void affecterRole(String email,String role);

	
	public Candidat getCandidat(Long code);
	
	public Formateur getFormateur(Long code);
	
	public void activate(boolean state, Long id);
	
	public List<String> importdata (String filepath);
	
	public List<String> importfile (MultipartFile file);

	
	public Resultat getResultat(Long code);
	
	public void sendMail(String nom,String to,String code,String url) throws Exception;
	public void resetMail(String nom,String to, String url) throws Exception;
	
	public Long addChecker(String email);
	public boolean checkTest(String email,String code);
	public void deleteCheker(String email,String code);
	
	public void modpass(String pwd, Long id);
	
	public boolean ifexist(String email);
	
	public Personne findFormateurByEmail(String email);
	
	


}
