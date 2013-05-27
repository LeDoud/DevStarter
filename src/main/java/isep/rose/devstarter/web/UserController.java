package isep.rose.devstarter.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;

import isep.rose.devstarter.service.Email;
import isep.rose.devstarter.domain.User;
import isep.rose.devstarter.domain.Enumeration;

@RequestMapping("/user/**")
@Controller
public class UserController {

	@RequestMapping(method = RequestMethod.POST, value = "{id}")
	public void post(@PathVariable Long id, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
	}

	@RequestMapping
	public String index() {
		return "user/index";
	}

	@RequestMapping(value = "/signupForm", produces = "text/html")
	public ModelAndView signupForm() {
		return new ModelAndView("user/signupForm");
	}

	/*----------ACCES A LA PAGE ACCOUNT-------------*/
	@RequestMapping(value = "/account", produces = "text/html")
	public String account(HttpServletRequest request, ModelMap model) {

		if (request.getSession().getAttribute("idUser") != null) {
			User user = new User().findUser((Integer) (request.getSession().getAttribute("idUser")));
			//model.addAttribute("firstName", user.getFirstname());
			//model.addAttribute("lastName", user.getName());
			//model.addAttribute("idUser", user.getIdUser());
			model.addAttribute("user",user);
			
	    	List<Enumeration> job= Enumeration.findEnumerationsByType("job");
	    	model.addAttribute("jobs",job);
			return "user/account";
		}
		return "resourceNotFound";
	}
	
	/*----------UPDATE ACCOUNT-------------*/
	@RequestMapping(value = "/update", produces = "text/html", method = RequestMethod.POST)
	public String updateAccount(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("email") String email,
			@RequestParam("passwordOld") String passwordOld,
			@RequestParam("passwordNew") String passwordNew,
			@RequestParam("job") int job,
			@RequestParam("experience") String experience,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		
		
		return"";
	}
	/*------TRAITEMENT DU FORM DINSCRIPTION PAR EMAIL----------*/
	@RequestMapping(value = "/signupEmail", produces = "text/html", method = RequestMethod.POST)
	public String signupEmail(@RequestParam("firstname") String firstName,
			@RequestParam("lastname") String lastName,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			RedirectAttributes redirectAttributes) {

		PasswordEncoder encoder = new Md5PasswordEncoder();
		String hashedPass = encoder.encodePassword(password, "DevStarter");
		User user = new User();

		user.setFirstname(firstName);
		user.setName(lastName);
		user.setEmail(email);
		user.setPassword(hashedPass);
		user.setJobEnumId(Enumeration.findEnumerationByNameAndType("No job",
				"job"));
		user.setCompteEnumId(Enumeration.findEnumerationByNameAndType("normal",
				"account"));
		user.setWallet(0);
		user.setActive(1);

		/* enregistrement en bdd */
		user.persist();

		/*
		 * envoi d'un mail pour confirmer et lien de redirection pour valider
		 * compte
		 */
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"springEmail.xml");
		Email mm = (Email) context.getBean("email");
		mm.sendMail("from@no-spam.com", email, "[DevStarter] Création de votre compte",
				"Votre compte a bien été crée.");

		/* message de confirmation lors du retour sur l'accueil */
		String accountCreated = "<div class=\"alert alert-success\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Your account has been created. You can sign in now !</strong> However, we have sent you an email so you can validate your account for good.</div>";
		redirectAttributes.addFlashAttribute("message", accountCreated);
		return "redirect:/home/index";
	}

	/*------VERIFICATION DE LUNICITE DU MAIL POUR INSCRIPTION--------------*/
	@RequestMapping(value = "/checkEmailUnicity", produces = "text/html", method = RequestMethod.POST)
	@ResponseBody
	public String checkEmailUnicity(@RequestParam("subEmail") String subEmail) {
		User user = new User().findUserByEmail(subEmail);
		if (user != null) {
			return user.getEmail().toString();
		}
		return "";
	}

	/*---------INSCRIPT ET LOGIN AVEC SERVICE EXTERNE----------*/
	@RequestMapping(value = "/signinProvider", produces = "text/html", method = RequestMethod.POST)
	@ResponseBody
	public String signinProvider(@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName,@RequestParam("email") String email,@RequestParam("password") String password,@RequestParam("provider") String provider,HttpServletRequest request) {
		String action="";

		if((request.getSession().getAttribute("account") == null) || !(String.valueOf(request.getSession().getAttribute("account")).contains(provider))){
			User userTest = new User().findUserByEmail(email);
			PasswordEncoder encoder = new Md5PasswordEncoder();
			String hashedPass = encoder.encodePassword(password, "DevStarter");
			if (userTest == null) {	
				User userInsert = new User();
				userInsert.setFirstname(firstName);
				userInsert.setName(lastName);
				userInsert.setEmail(email);
				userInsert.setPassword(hashedPass);
				userInsert.setJobEnumId(Enumeration.findEnumerationByNameAndType("No job",
						"job"));
				userInsert.setCompteEnumId(Enumeration.findEnumerationByNameAndType(provider,
						"account"));
				userInsert.setWallet(0);
				userInsert.setActive(2);
				userInsert.persist();

			}
			User user = new User().getUserAfterAuthentification(email, hashedPass);
			if (user != null) {
				request.getSession().invalidate();
				request.getSession().setAttribute("logged", "true");
				request.getSession().setAttribute("idUser", user.getIdUser());
				request.getSession().setAttribute("firstName", user.getFirstname());
				request.getSession().setAttribute("lastName", user.getName());
				request.getSession().setAttribute("active", user.getActive());
				request.getSession().setAttribute("wallet", user.getWallet());
				request.getSession().setAttribute("account", user.getCompteEnumId().getName());	
				action="refresh";
			}else{
				action = "<div class=\"alert alert-error\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Cannot sign in ! The email address is already associated with a "+userTest.getCompteEnumId().getName()+" account.</strong></div>";
			}
		}
		return action;
	}
	
	/*----------LOGIN AVEC MAIL-----------------*/
	@RequestMapping(value = "/signin", produces = "text/html", method = RequestMethod.POST)
	public String signin(@RequestParam("email") String email,
			@RequestParam("password") String password,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {

		PasswordEncoder encoder = new Md5PasswordEncoder();
		String hashedPass = encoder.encodePassword(password, "DevStarter");

		User user = new User().getUserAfterAuthentification(email, hashedPass);
		if (user != null) {
			request.getSession().invalidate();
			request.getSession().setAttribute("logged", "true");
			request.getSession().setAttribute("idUser", user.getIdUser());
			request.getSession().setAttribute("firstName", user.getFirstname());
			request.getSession().setAttribute("lastName", user.getName());
			request.getSession().setAttribute("active", user.getActive());
			request.getSession().setAttribute("wallet", user.getWallet());
			request.getSession().setAttribute("account", user.getCompteEnumId().getName());

			if (user.getActive() == 1) {
				String infoMessage = "<div class=\"alert\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Warning !</strong> You didn't validate your account yet. Please follow the instructions sent by mail. <a href=\"\">Re-send email.</a></div>";
				redirectAttributes.addFlashAttribute("message", infoMessage);
			}
			return "redirect:/home/index";
		} else {

			String errorMessage = "<div class=\"alert alert-error\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Cannot sign in !</strong> Wrong email or password. Forgot your password ? <a href=\"/DevStarter/user/forgotPassword\" data-target=\"#myModal2\" data-backdrop=\"true\" data-toggle=\"modal\">Click here.</a></div>";
			redirectAttributes.addFlashAttribute("message", errorMessage);

			return "redirect:/home/index";
		}
	}

	/*----------FORGOT PASSWORD-----------------*/
	@RequestMapping(value = "/forgotPassword", produces = "text/html")
	public String forgotPassword(HttpServletRequest request, ModelMap model) {
		return "user/forgotPassword";
	}

	/*----------RESET PASSWORD-----------------*/
	@RequestMapping(value = "/resetPassword", produces = "text/html", method = RequestMethod.POST)
	public String resetPassword(@RequestParam("email") String email,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {

		/* Create random string */
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String pass = "";
		for (int x = 0; x < 6; x++) {
			int i = (int) Math.floor(Math.random() * 62);
			pass += chars.charAt(i);
		}

		PasswordEncoder encoder = new Md5PasswordEncoder();
		String hashedPass = encoder.encodePassword(pass, "DevStarter");
		User user = new User().findUserByEmail(email);
		if (user != null) {
			user.setPassword(hashedPass);
			user.persist();
		}

		/*
		 * mail pour envoyer le nouveau mot de passe
		 */
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"springEmail.xml");
		Email mm = (Email) context.getBean("email");
		mm.sendMail("from@no-spam.com", email, "[DevStarter] New Password",
				"Your new password is : " + pass);

		/* message de confirmation lors du retour sur l'accueil */
		String newPassword = "<div class=\"alert alert-success\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Your new password has been sent to your email account</div>";
		redirectAttributes.addFlashAttribute("message", newPassword);
		return "redirect:/home/index";
	}

	/*----------LOGOUT---------------*/
	@RequestMapping(value = "/logout", produces = "text/html" )
	public String logout(HttpServletRequest request) {

		request.getSession().invalidate();
		return "redirect:/home/index";
	}
	

}
