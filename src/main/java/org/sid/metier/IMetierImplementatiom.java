package org.sid.metier;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.sid.DAO.CheckerRepository;
import org.sid.DAO.PersonneRepository;
//import org.sid.DAO.QuestionsRepository;
import org.sid.DAO.ResultatRepository;
import org.sid.DAO.TestRepository;
//import org.sid.DAO.UsersRepository;
import org.sid.entities.Candidat;
import org.sid.entities.Checker;
import org.sid.entities.Formateur;
import org.sid.entities.Personne;
//import org.sid.entities.Question;
import org.sid.entities.Resultat;
import org.sid.entities.Test;
//import org.sid.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Contact;
import com.mailjet.client.resource.Email;
import com.mailjet.client.resource.Emailv31;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


@Service
@Transactional
public class IMetierImplementatiom implements IMetier{
	@Autowired
	TestRepository testrepository;
	@Autowired
	PersonneRepository personnerepository;
//	@Autowired
//	QuestionsRepository questionrepository;
	@Autowired
	ResultatRepository resultatrepository;
	@Autowired
	CheckerRepository checkerRepository;
//	@Autowired
//	UsersRepository usersrepository;
	@Autowired
	private DataSource dataSource;

	@Override
	public Test consulterTest(String code) {
		Test t = testrepository.findById(code).get();
		return t;
	}

	@Override
	public void ajouterTest(Test test) {
		testrepository.save(test);
	}

	@Override
	public void supprimerTest(String codeTest) {
		testrepository.deleteById(codeTest);		
	}
	
	@Override
	public void supprimerResultat(Long codeResultat) {
		resultatrepository.deleteById(codeResultat);
		
	}

	@Override
	public void supprimerCoach(Long codeCoach) {
		personnerepository.deleteById(codeCoach);
	}
	
	@Override
	public void supprimerCandidat(Long codeCandidat) {
		personnerepository.deleteById(codeCandidat);
	}



	@Override
	public void genererTest(String sujet, int nbrQuestion, boolean timed, double duree) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ajouterCandidat(Candidat candidat) {
		personnerepository.save(candidat);
	}

	@Override
	public void ajouterFormateur(Formateur formateur) {
		personnerepository.save(formateur);
	}
	
	@Override
	public void ajouterResultat(Resultat resultat) {
		resultatrepository.save(resultat);
	}

	@Override
	public Page<Resultat> listResultat(int page, int size) {
		
		return resultatrepository.listResultat(new PageRequest(page, size));
		
	}

	@Override
	public Page<Test> listTest(int page, int size) {
		
		return testrepository.listTest(new PageRequest(page, size));
		
	}

//	@Override
//	public Page<Question> listQuestion(int page, int size) {
//
//		return questionrepository.listQuestion(new PageRequest(page, size));
//	}

	@Override
	public List<String> importdata(String filepath) {
			List<String> questions = new ArrayList<String>();
		try {
			FileInputStream input = new FileInputStream(filepath);
			POIFSFileSystem fs = new POIFSFileSystem(input);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            Row row;
            
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            	
            	 row = sheet.getRow(i);
            	 String text = row.getCell(0).getStringCellValue();
            	 questions.add(text);
             System.out.println("       :   "+ text );
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return questions;
	}
	
	@Override
	public List<String> importfile(MultipartFile file) {
		
		return null;
	}

	@Override
	public Page<Candidat> listCandidat(int page, int size) {
		return personnerepository.listCandidat(new PageRequest(page, size));
	}
	
//	@Override
//	public Page<Users> listUsers(int page, int size) {
//		return usersrepository.findAll(new PageRequest(page, size));
//	}
	
	@Override
	public Page<Formateur> listFormateur(int page, int size) {
		return personnerepository.listFormateur(new PageRequest(page, size));
	}


	@Override
	public Candidat getCandidat(Long code) {
		return personnerepository.findCandidat(code);
	}

	@Override
	public Resultat getResultat(Long code) {
		return resultatrepository.findById(code).get();
	}

	@Override
	public void sendMail(String nom,String to,String code,String url) throws Exception {
			MailjetClient client;
		    	MailjetRequest email;
		    	MailjetResponse response;
		    	JSONArray recipient;

		    	client = new MailjetClient("0bb42c90b5efb0929f4d8e77c347f94b","e656a4b49b26ff8930ddc71e8caea9af");

		    	recipient = new JSONArray()
                .put(new JSONObject().put(Contact.EMAIL, to));
		    
		    	email = new MailjetRequest(Email.resource)
                    .property(Email.FROMNAME, "Rahma Consulting")
                    .property(Email.FROMEMAIL, "ch.nour2nd@gmail.com")
                    .property(Email.SUBJECT, "Invitation passage de test")
                    .property(Email.MJTEMPLATEID, 509631)
                    .property(Email.MJTEMPLATELANGUAGE, true)
                    .property(Email.VARS, new JSONObject().put("nom", nom).put("code", code).put("lien", url))
                    .property(Email.RECIPIENTS, recipient)
                    .property(Email.MJCUSTOMID, "JAVA-Email");

    	response = client.post(email);
      System.out.println(response.getStatus());
      System.out.println(response.getData());
    }
	

	@Override
	public Long addChecker(String email) {
		Checker ch = checkerRepository.save(new Checker(email));
		return Long.parseLong(ch.getCode());
	}

	@Override
	public boolean checkTest(String email, String code) {
		try {
		Checker checker = checkerRepository.getByCode(code);
		if (checker != null)
		{
			return true;
		}
		}catch(Exception e)
		{
			return false;
		}
		return false;
	}

	@Override
	public void deleteCheker(String email, String code) {
		checkerRepository.deleteById(checkerRepository.getByCode(code).getId());
	}

	@Override
	public void affecterRole(String email, String role) {
		try {
			dataSource.getConnection().createStatement().executeUpdate("insert into users_roles values ('"+email+"','"+role+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Formateur getFormateur(Long code) {
		return personnerepository.findFormateur(code);
	}

	@Override
	public void activate(boolean state, Long id) {
		personnerepository.activate(state, id);
	}

	@Override
	public void modpass(String pwd, Long id) {
		System.out.println("updating password in imetier");
		personnerepository.modpass(id, BCrypt.hashpw(pwd, BCrypt.gensalt()));
	}

	@Override
	public void resetMail(String nom, String to, String url) throws Exception {

		MailjetClient client;
	    	MailjetRequest email;
	    	MailjetResponse response;
	    	JSONArray recipient;

	    	client = new MailjetClient("0bb42c90b5efb0929f4d8e77c347f94b","e656a4b49b26ff8930ddc71e8caea9af");
	
	    	recipient = new JSONArray()
	        .put(new JSONObject().put(Contact.EMAIL, to));
	    
	    	email = new MailjetRequest(Email.resource)
	            .property(Email.FROMNAME, "Rahma Consulting")
	            .property(Email.FROMEMAIL, "ch.nour2nd@gmail.com")
	            .property(Email.SUBJECT, "Réinitialisation de mot de passe")
	            .property(Email.MJTEMPLATEID, 547910)
	            .property(Email.MJTEMPLATELANGUAGE, true)
	            .property(Email.VARS, new JSONObject().put("nom", nom).put("lien", url))
	            .property(Email.RECIPIENTS, recipient)
	            .property(Email.MJCUSTOMID, "JAVA-Email");
	
				response = client.post(email);
				System.out.println(response.getStatus());
				System.out.println(response.getData());
				System.out.println("iedhwouchscjsoidjciosjd");
	}

	@Override
	public boolean ifexist(String email) {

		return personnerepository.ifexist(email);
	}

	@Override
	public Personne findFormateurByEmail(String email) {
		
		return personnerepository.findFormateurByMail(email);
	}

	







	
	
	
	
	



		

}
