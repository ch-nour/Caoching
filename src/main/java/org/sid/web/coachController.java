package org.sid.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.sid.entities.Candidat;
import org.sid.entities.Formateur;
import org.sid.entities.Personne;
import org.sid.entities.Resultat;
import org.sid.entities.Test;
import org.sid.metier.IMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.SynthesizedAnnotation;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class coachController {

	@Autowired
	private IMetier iMetier;

	@RequestMapping("/")
	private String index() {
		return "blank";
	}

	@RequestMapping("/thanks")
	private String thanks() {
		return "thanks";
	}

	@RequestMapping("/generate")
	private String generate(Model model) {
		Page<Test> pageTest = iMetier.listTest(0, 100);
		model.addAttribute("listeTest", pageTest.getContent());
		return "generate";
	}

	@RequestMapping("/import")
	private String importer() {
		return "import";
	}

	@RequestMapping("/invite")
	private String invite(Model model, @RequestParam(value = "test") String code,
			@RequestParam(value = "codecandidat") Long codecandidat) {
		Long checkcode = iMetier.addChecker(iMetier.getCandidat(codecandidat).getEmail());
		try {
			Candidat candidat = iMetier.getCandidat(codecandidat);
			iMetier.sendMail(candidat.getNom(), candidat.getEmail(), checkcode + "",
					"http://a88c3e0c.ngrok.io/check?test=" + code + "&codecandidat=" + codecandidat);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return "blank";
	}

	@RequestMapping("/check")
	private String check(Model model, @RequestParam(value = "test") String code,
			@RequestParam(value = "codecandidat") Long codecandidat) {
		model.addAttribute("code", code);
		model.addAttribute("codeCandidat", codecandidat);
		return "check";
	}

	@RequestMapping("/activate")
	public String activate(@RequestParam(value = "codeFormateur") Long code, @RequestParam(value = "state") int state) {
		if (state == 1) {
			iMetier.activate(true, code);
			System.out.println("activating coach");
		} else {
			iMetier.activate(false, code);
			System.out.println("desactiviting coach");
		}

		return "redirect:/managecoach";
	}

	@RequestMapping("/adduser")
	private String adduser(@RequestParam(value = "Nom") String nom, @RequestParam(value = "Prenom") String prenom,
			@RequestParam(value = "Email") String email, @RequestParam(value = "test") String test) {

		if (nom != null && prenom != null && email != null) {
			iMetier.ajouterCandidat(new Candidat(nom, prenom, "", email, ""));
		}
		return "redirect:/selectuser?test=" + test;
	}

	@RequestMapping("/selectuser")
	private String selectUser(Model model, @RequestParam(value = "test") String codeTest) {

		Page<Candidat> page = iMetier.listCandidat(0, 100);
		model.addAttribute("codeTest", codeTest);
		model.addAttribute("listeCandidat", page);

		return "selectuser";
	}

	@RequestMapping("/results")
	private String results(Model model) {

		Page<Resultat> pageResult = iMetier.listResultat(0, 100);
		model.addAttribute("listeResults", pageResult.getContent());

		return "results";
	}

	@RequestMapping("/verify")
	private String verify(@RequestParam(value = "email") String email, @RequestParam(value = "password") String code,
			@RequestParam(value = "codeCandidat") String codeCandidat, @RequestParam(value = "code") String codetest) {

		if (iMetier.checkTest(email, code)) {
			return "forward://generated%20questions?codecandidat=" + codeCandidat + "&test=" + codetest
					+ "&codechecker=" + code;
		} else {
			return "alert('you cant acess');";
		}

	}

	@RequestMapping("/generated questions")
	private String qcm(Model model, @RequestParam(value = "test") String code,
			@RequestParam(value = "codecandidat") String codecandidat,
			@RequestParam(value = "codechecker") String codechecker) {
		List<String> questions = iMetier.consulterTest(code).getQuestion();
		model.addAttribute("listeQuestions", questions);
		model.addAttribute("codechecker", codechecker);
		model.addAttribute("titre", iMetier.consulterTest(code).getTitre());
		model.addAttribute("code", iMetier.consulterTest(code).getId());
		model.addAttribute("codecandidat", codecandidat);
		model.addAttribute("type", iMetier.consulterTest(code).getType());

		return "generated questions";
	}

	@RequestMapping("/manage")
	private String manage(Model model, @RequestParam(value = "delete", required = false) String codeTest) {
		Page<Test> pageTest = iMetier.listTest(0, 100);
		model.addAttribute("listeTest", pageTest.getContent());

		if (codeTest != null) {
			try {
				iMetier.supprimerTest(codeTest);
				System.out.println("deleting .......");
				manage(model, null);
			} catch (Exception e) {
				System.out.println("exception");
				model.addAttribute("exception", "Vous ne pouvez pas supprimer ce test, il a des resultat affecté");
			}
		}

		return "manage";
	}

	@RequestMapping("/managecoach")
	private String manageCoach(Model model) {
		Page<Formateur> pageFormateur = iMetier.listFormateur(0, 100);
		model.addAttribute("pageformateur", pageFormateur.getContent());

		return "managecoach";
	}

	@RequestMapping("/download")
	private String download(@RequestParam(value = "download", required = false) String codeTest) {

		return "redirect:/manage";
	}

	@RequestMapping("/saveanswers")
	private String getAnswers(@RequestParam(value = "answers") String answers,
			@RequestParam(value = "code") String code, @RequestParam(value = "type") String type,
			@RequestParam(value = "codecandidat") String codecandidat,
			@RequestParam(value = "codechecker") String codechecker) {

		List<String> reponses = new ArrayList<String>(Arrays.asList(answers.split(",")));

		iMetier.ajouterResultat(new Resultat("", type, new Date(), iMetier.consulterTest(code),
		iMetier.getCandidat(Long.parseLong(codecandidat)), reponses));
		iMetier.deleteCheker(iMetier.getCandidat(Long.parseLong(codecandidat)).getEmail(), codechecker);
		for (String string : reponses) {
			System.out.println(string);
		}

		return "redirect:/thanks";
	}

	@RequestMapping("/showresult")
	private String showresult(Model model, @RequestParam(name = "result") Long code) {
		Resultat resultat = iMetier.getResultat(code);
		List<String> list = resultat.getReponses();
		model.addAttribute("type", resultat.getType());
		model.addAttribute("titre", resultat.getTest().getTitre());
		System.out.println(resultat.getTest().getTitre());

		if (resultat.getType().equals("5")) {
			int col1 = 0;
			int col2 = 0;
			int col3 = 0;
			int col4 = 0;
			int col5 = 0;

			for (int i = 1; i < list.size(); i = i + 5) {
				col1 += Integer.parseInt(list.get(i));
			}

			for (int i = 2; i < list.size(); i = i + 5) {
				col2 += Integer.parseInt(list.get(i));
			}

			for (int i = 3; i < list.size(); i = i + 5) {
				col3 += Integer.parseInt(list.get(i));
			}

			for (int i = 4; i < list.size(); i = i + 5) {
				col4 += Integer.parseInt(list.get(i));
			}

			for (int i = 5; i < list.size(); i = i + 5) {
				col5 += Integer.parseInt(list.get(i));
			}

			int p1 = (int) col1 * 100 / 50;
			int p2 = (int) col2 * 100 / 50;
			int p3 = (int) col3 * 100 / 50;
			int p4 = (int) col4 * 100 / 50;
			int p5 = (int) col5 * 100 / 50;

			model.addAttribute("p1", p1);
			model.addAttribute("p2", p2);
			model.addAttribute("p3", p3);
			model.addAttribute("p4", p4);
			model.addAttribute("p5", p5);

			model.addAttribute("col1", col1);
			model.addAttribute("col2", col2);
			model.addAttribute("col3", col3);
			model.addAttribute("col4", col4);
			model.addAttribute("col5", col5);

			model.addAttribute("reponses", list);

			return "showresult";
		} else if (resultat.getType().equals("2")) {
			int colA = 0;
			int colB = 0;
			int colC = 0;
			int colD = 0;

			int[] A = new int[] { 1, 7, 15, 16, 17, 25, 26, 35, 36, 37, 50, 51, 52, 59, 60 };
			int[] B = new int[] { 4, 6, 10, 11, 20, 21, 28, 29, 30, 39, 40, 48, 49, 55, 56 };
			int[] C = new int[] { 3, 5, 9, 12, 13, 19, 22, 31, 32, 41, 42, 46, 47, 54, 57 };
			int[] D = new int[] { 2, 8, 14, 18, 23, 24, 27, 33, 34, 38, 43, 44, 45, 53, 58 };

			for (int i : A) {
				if (Integer.parseInt(list.get(i - 1)) == 1)
					colA += 1;
			}

			for (int i : B) {
				if (Integer.parseInt(list.get(i - 1)) == 1)
					colB += 1;
			}

			for (int i : C) {
				if (Integer.parseInt(list.get(i - 1)) == 1)
					colC += 1;
			}

			for (int i : D) {
				if (Integer.parseInt(list.get(i - 1)) == 1)
					colD += 1;
			}

			int p1 = (int) colA * 100 / 15;
			int p2 = (int) colB * 100 / 15;
			int p3 = (int) colC * 100 / 15;
			int p4 = (int) colD * 100 / 15;

			model.addAttribute("p1", p1);
			model.addAttribute("p2", p2);
			model.addAttribute("p3", p3);
			model.addAttribute("p4", p4);

			model.addAttribute("colA", colA);
			model.addAttribute("colB", colB);
			model.addAttribute("colC", colC);
			model.addAttribute("colD", colD);

			return "showresult";
		} else if (resultat.getType().equals("3")) {
			int colA = 0;
			int colB = 0;
			int colC = 0;
			int colD = 0;

			int[] A = new int[] { 1, 7, 15, 16, 17, 25, 26, 35, 36, 37, 50, 51, 52, 59, 60 };
			int[] B = new int[] { 4, 6, 10, 11, 20, 21, 28, 29, 30, 39, 40, 48, 49, 55, 56 };
			int[] C = new int[] { 3, 5, 9, 12, 13, 19, 22, 31, 32, 41, 42, 46, 47, 54, 57 };
			int[] D = new int[] { 2, 8, 14, 18, 23, 24, 27, 33, 34, 38, 43, 44, 45, 53, 58 };

			for (int i : A) {
				if (Integer.parseInt(list.get(i - 1)) == 1)
					colA += 1;
			}

			for (int i : B) {
				if (Integer.parseInt(list.get(i - 1)) == 1)
					colB += 1;
			}

			for (int i : C) {
				if (Integer.parseInt(list.get(i - 1)) == 1)
					colC += 1;
			}

			for (int i : D) {
				if (Integer.parseInt(list.get(i - 1)) == 1)
					colD += 1;
			}

			int p1 = (int) colA * 100 / 15;
			int p2 = (int) colB * 100 / 15;
			int p3 = (int) colC * 100 / 15;
			int p4 = (int) colD * 100 / 15;

			model.addAttribute("p1", p1);
			model.addAttribute("p2", p2);
			model.addAttribute("p3", p3);
			model.addAttribute("p4", p4);

			model.addAttribute("colA", colA);
			model.addAttribute("colB", colB);
			model.addAttribute("colC", colC);
			model.addAttribute("colD", colD);

			return "showresult";
		} else
			return "error";
	}

	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public String submit(@RequestParam("file") MultipartFile file, @RequestParam("titre") String titre,
			@RequestParam("sujet") String sujet) {

		if (file != null && titre != null && sujet != null) {
			// iMetier.ajouterTest(new Test("code11", titre, sujet, new Date(), new
			// Formateur(), ));
		}
		return "manage";
	}

	@RequestMapping("/deletecoach")
	public String deleteCoach(@RequestParam("codecoach") Long codeCoach, Model model) {
		try {
			iMetier.supprimerCoach(codeCoach);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "forward:/managecoach";
	}

	@RequestMapping("/deleteresult")
	public String deleteResult(@RequestParam("coderesult") Long codeResultat, Model model) {
		try {
			iMetier.supprimerResultat(codeResultat);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "redirect:/results";
	}

	@RequestMapping("/deleteCandidat")
	public String deleteCandidat(@RequestParam("codecandidat") Long codeCandidat,
			@RequestParam("codetest") String codeTest, Model model) {
		try {
			iMetier.supprimerCandidat(codeCandidat);
		} catch (Exception e) {
			System.out.println("raising exception");
			model.addAttribute("error", e.getMessage());
		}
		return "forward:/selectuser?test=" + codeTest;
	}

	@RequestMapping("/addcoach")
	private String addcoach(@RequestParam(value = "Nom") String nom, @RequestParam(value = "Prenom") String prenom,
			@RequestParam(value = "Email") String email, @RequestParam(value = "tel") String tel,
			@RequestParam(value = "password") String password, @RequestParam(value = "role") String role) {

		if (nom != null && prenom != null && email != null && tel != null && password != null) {
			iMetier.ajouterFormateur(new Formateur(nom, prenom, tel, email, "", "", null,
					BCrypt.hashpw(password, BCrypt.gensalt()), true));
			iMetier.affecterRole(email, role);
		}

		return "redirect:/managecoach";
	}

	@RequestMapping("/changepwd")
	public String changepassword(Model model, @RequestParam(value = "id") Long id) {

		if (iMetier.getFormateur(id).isActive())
			model.addAttribute("id", id);
		model.addAttribute("id", id);
		return "changepwd";
	}

	@RequestMapping("/modifypass")
	public String modifypwd(@RequestParam("pass") String pwd, @RequestParam("id") Long id) {

		iMetier.modpass(pwd, id);
		System.out.println("modifying password .........");
		iMetier.activate(true, id);
		return "redirect:/login";
	}

	@RequestMapping("/resetpass")
	public String resetpass(@RequestParam("email") String email) throws Exception {

		System.out.println(iMetier.ifexist(email));
		if (iMetier.ifexist(email)) {
			System.out.println("sdnsdskdnsdnksdnsk");
			Personne f = iMetier.findFormateurByEmail(email);
			Formateur ff = iMetier.getFormateur(f.getId());
			ff.setActive(false);
			iMetier.resetMail(f.getNom(), email, "http://66acdd69.ngrok.io/changepwd?id=" + f.getId());
		}
		return "redirect:/login";
	}

}
