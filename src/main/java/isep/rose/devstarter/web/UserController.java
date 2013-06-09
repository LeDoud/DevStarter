package isep.rose.devstarter.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import isep.rose.devstarter.domain.FollowUserProject;
import isep.rose.devstarter.domain.User;
import isep.rose.devstarter.domain.Project;
import isep.rose.devstarter.domain.Enumeration;
import isep.rose.devstarter.domain.DonationUserProject;

@RequestMapping("/user/**")
@Controller
public class UserController {

	@RequestMapping(method = RequestMethod.POST, value = "{id}")
	public void post(@PathVariable Long id, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
	}

	@RequestMapping(value = "/signupForm", produces = "text/html")
	public ModelAndView signupForm() {
		return new ModelAndView("user/signupForm");
	}

	/*----------ACCES A LA PAGE ACCOUNT-------------*/
	@RequestMapping(value = "/account", produces = "text/html")
	public String account(HttpServletRequest request, ModelMap model) {

		if (request.getSession().getAttribute("idUser") != null) {
			User user = new User().findUser((Integer) (request.getSession()
					.getAttribute("idUser")));
			// model.addAttribute("firstName", user.getFirstname());
			// model.addAttribute("lastName", user.getName());
			// model.addAttribute("idUser", user.getIdUser());
			model.addAttribute("user", user);
			model.addAttribute("compte", user.getCompteEnumId().getName());

			List<Enumeration> job = Enumeration.findEnumerationsByType("job");
			model.addAttribute("jobs", job);
			return "user/account";
		}
		return "resourceNotFound";
	}

	/*----------ACCES A LA PAGE PROFILE-------------*/
	@RequestMapping(value = "/profile/{idUser}", produces = "text/html", method = RequestMethod.GET)
	public String profile(@PathVariable Integer idUser,
			HttpServletRequest request, ModelMap model) {

		User user = new User().findUser((Integer) (idUser));
		model.addAttribute("user", user);

		return "user/profile";

	}
	
	/*----------ACCES A LA PAGE MY PROJECTS-------------*/
	@RequestMapping(value = "/myprojects", produces = "text/html", method = RequestMethod.GET)
	public String myprojects(HttpServletRequest request, ModelMap model) {
		if (request.getSession().getAttribute("idUser") != null) {
		User user = new User().findUser((Integer) (request.getSession().getAttribute("idUser")));
		model.addAttribute("user", user);
		List<Project> myprojects = Project.findUserProjects((Integer)(request.getSession().getAttribute("idUser")));
		model.addAttribute("myprojects",myprojects);

		return "user/managedProjects";
		}
		return "resourceNotFound";
	}
	
	/*----------UPDATE ACCOUNT-------------*/
	@RequestMapping(value = "/update", produces = "text/html", method = RequestMethod.POST)
	public String update(@RequestParam("firstname") String firstName,
			@RequestParam("name") String lastName,
			@RequestParam("email") String email,
			@RequestParam("passwordOld") String passwordOld,
			@RequestParam("passwordNew") String passwordNew,
			@RequestParam("job") int job,
			@RequestParam("profil") String experience,
			@RequestParam("userId") int userId,
			RedirectAttributes redirectAttributes) {
		String accountUpdated = "";
		if (userId != 0) {

			User user = new User().findUser((Integer) (userId));
			if (user != null) {

				if (!user.getCompteEnumId().getName().equals("facebook")
						|| !user.getCompteEnumId().getName().equals("google")) {
					if (!passwordOld.equals("") && !passwordNew.equals("")) {
						String pass = user.getPassword();
						PasswordEncoder encoder = new Md5PasswordEncoder();
						String hashedPass = encoder.encodePassword(passwordOld,
								"DevStarter");
						if (hashedPass.equals(pass)) {
							user.setPassword(encoder.encodePassword(
									passwordNew, "DevStarter"));
							accountUpdated = "<div class=\"alert alert-success\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Your profile and password have been modified !</strong></div>";

						} else {
							accountUpdated = "<div class=\"alert alert-warning\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Your old password was not correct, only your profile has been modified !</strong></div>";
						}

					} else {
						accountUpdated = "<div class=\"alert alert-success\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Your profile has been modified !</strong></div>";
					}
				}

				user.setEmail(email);
				user.setFirstname(firstName);
				user.setName(lastName);
				user.setJobEnumId(Enumeration.findEnumeration(job));
				user.setProfil(experience);

				user.persist();
			}
			/* message de confirmation des modifs */
			redirectAttributes.addFlashAttribute("message", accountUpdated);

			return "redirect:/user/account";
		} else {
			return "redirect:/";
		}
	}

	/*----------ACCES A LA PAGE WALLET-------------*/
	@RequestMapping(value = "/wallet", produces = "text/html")
	public String wallet(HttpServletRequest request, ModelMap model) {
		if (request.getSession().getAttribute("idUser") != null) {
			User user = new User().findUser((Integer) (request.getSession()
					.getAttribute("idUser")));
			model.addAttribute("user", user);
			model.addAttribute("compte", user.getCompteEnumId().getName());

			List<DonationUserProject> transaction = DonationUserProject
					.findDonationUserProjectByUserId(user);
			model.addAttribute("donations", transaction);
			return "user/wallet";
		}
		return "resourceNotFound";
	}

	/*----------USER DONATE POP UP-----------------*/
	@RequestMapping(value = "/donate/{idProject}", produces = "text/html")
	public String donate(@PathVariable Integer idProject,
			HttpServletRequest request, ModelMap model) {
		model.addAttribute("idProject", idProject);
		return "user/donate";
	}

	/*----------USER DONATE PROJECT-----------------*/
	@RequestMapping(value = "/donateProject", produces = "text/html", method = RequestMethod.POST)
	public String donateProject(@RequestParam("projectId") int projectId,
			@RequestParam("amount") int amount,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String donationDone = "";
		if (request.getSession().getAttribute("idUser") != null) {

			User user = new User().findUser((Integer) (request.getSession()
					.getAttribute("idUser")));
			int wallet = user.getWallet() - amount;
			if (wallet >= 0) {

				request.getSession().setAttribute("wallet", wallet); // pour la
																		// session
				user.setWallet(wallet); // pour la bdd
				user.persist();

				Project project = new Project().findProject(projectId);

				DonationUserProject history = new DonationUserProject();
				history.setAmount(amount);
				history.setType("less");
				history.setUserId(user);
				history.setProjectId(project);
				history.setTransactionDetail("Donation to project : "
						+ project.getName());
				history.setDate(Calendar.getInstance());

				history.persist();
				donationDone = "<div class=\"alert alert-success\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Your donation is most welcomed !</div>";
			} else {
				donationDone = "<div class=\"alert alert-danger\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Your donation has not been completed because you don't have the funds !</div>";

			}
		}

		/* message de confirmation lors du retour sur l'accueil */
		redirectAttributes.addFlashAttribute("message", donationDone);
		return "redirect:/project/show/"+projectId;
	}

	/*----------USER ADD MONEY POP UP -----------------*/
	@RequestMapping(value = "/addMoney", produces = "text/html")
	public String addMoney(HttpServletRequest request, ModelMap model) {

		return "user/addMoney";
	}

	/*----------USER ADD MONEY  -----------------*/
	@RequestMapping(value = "/userAddMoney", produces = "text/html", method = RequestMethod.POST)
	public String userAddMoney(@RequestParam("money") int money,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (request.getSession().getAttribute("idUser") != null) {
			User user = new User().findUser((Integer) (request.getSession()
					.getAttribute("idUser")));
			int wallet = user.getWallet() + money;

			request.getSession().setAttribute("wallet", wallet); // pour la
																	// session
			user.setWallet(wallet); // pour la bdd
			user.persist();

			DonationUserProject history = new DonationUserProject();
			history.setAmount(money);
			history.setType("add");
			history.setUserId(user);
			history.setTransactionDetail("Money added");
			history.setDate(Calendar.getInstance());

			history.persist();

		}
		/* message de confirmation lors du retour sur le wallet */
		String moneyAdded = "<div class=\"alert alert-success\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Your wallet is now quite heavy !</div>";
		redirectAttributes.addFlashAttribute("message", moneyAdded);
		return "redirect:/user/wallet";
	}
	
	/*----------USER FOLLOW PROJECT  -----------------*/
	@RequestMapping(value = "/follow/{idProject}", produces = "text/html")
	public String follow(@PathVariable Integer idProject,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String followDone="";
		if (request.getSession().getAttribute("idUser") != null) {
			
			User user = new User().findUser((Integer) (request.getSession()
					.getAttribute("idUser")));
			Project project = new Project().findProject(idProject);
			FollowUserProject followed = new FollowUserProject();
			if(followed.findFollowUserProjectByUserId( user, project)==false){
				
				FollowUserProject follow = new FollowUserProject();
				follow.setProjectId(project);
				follow.setUserId(user);
				follow.persist();
				followDone="<div class=\"alert alert-success\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>You are now following this project!</div>";
					
			}else{
				followDone="<div class=\"alert alert-success\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>You are already following this project!</div>";
				
			}

		}else{
			followDone="<div class=\"alert alert-warning\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>You must be logged to follow a project!</div>";			
		}
		
		/* message de confirmation lors du retour sur le projet */
		redirectAttributes.addFlashAttribute("message", followDone);
		return "redirect:/project/show/"+ idProject;
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
		user.setCreatedDate(Calendar.getInstance());

		/* enregistrement en bdd */
		user.persist();

		/*
		 * envoi d'un mail pour confirmer et lien de redirection pour valider
		 * compte
		 */
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"springEmail.xml");
		Email mm = (Email) context.getBean("email");
		mm.sendMail("from@no-spam.com", email,
				"[DevStarter] Creation of your account",
				"Your account has been successfully created!");

		/* message de confirmation lors du retour sur l'accueil */
		String accountCreated = "<div class=\"alert alert-success\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Your account has been created. You can sign in now !</strong> However, we have sent you an email so you can validate your account for good.</div>";
		redirectAttributes.addFlashAttribute("message", accountCreated);
		return "redirect:/";
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
	public String signinProvider(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("provider") String provider,
			HttpServletRequest request) {
		String action = "";

		if ((request.getSession().getAttribute("account") == null)
				|| !(String.valueOf(request.getSession()
						.getAttribute("account")).contains(provider))) {
			User userTest = new User().findUserByEmail(email);
			PasswordEncoder encoder = new Md5PasswordEncoder();
			String hashedPass = encoder.encodePassword(password, "DevStarter");
			if (userTest == null) {
				User userInsert = new User();
				userInsert.setFirstname(firstName);
				userInsert.setName(lastName);
				userInsert.setEmail(email);
				userInsert.setPassword(hashedPass);
				userInsert.setJobEnumId(Enumeration
						.findEnumerationByNameAndType("No job", "job"));
				userInsert.setCompteEnumId(Enumeration
						.findEnumerationByNameAndType(provider, "account"));
				userInsert.setWallet(0);
				userInsert.setActive(2);
				userInsert.setCreatedDate(Calendar.getInstance());
				userInsert.persist();

			}
			User user = new User().getUserAfterAuthentification(email,
					hashedPass);
			if (user != null) {
				request.getSession().invalidate();
				request.getSession().setAttribute("logged", "true");
				request.getSession().setAttribute("idUser", user.getIdUser());
				request.getSession().setAttribute("firstName",
						user.getFirstname());
				request.getSession().setAttribute("lastName", user.getName());
				request.getSession().setAttribute("active", user.getActive());
				request.getSession().setAttribute("wallet", user.getWallet());
				request.getSession().setAttribute("account",
						user.getCompteEnumId().getName());
				action = "refresh";
			} else {
				action = "<div class=\"alert alert-error\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Cannot sign in ! The email address is already associated with a "
						+ userTest.getCompteEnumId().getName()
						+ " account.</strong></div>";
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
			request.getSession().setAttribute("account",
					user.getCompteEnumId().getName());

			if (user.getActive() == 1) {
				String infoMessage = "<div class=\"alert\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Warning !</strong> You didn't validate your account yet. Please follow the instructions sent by mail. <a href=\"\">Re-send email.</a></div>";
				redirectAttributes.addFlashAttribute("message", infoMessage);
			}
			return "redirect:/";
		} else {

			String errorMessage = "<div class=\"alert alert-error\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Cannot sign in !</strong> Wrong email or password. Forgot your password ? <a href=\"/DevStarter/user/forgotPassword\" data-target=\"#myModal2\" data-backdrop=\"true\" data-toggle=\"modal\">Click here.</a></div>";
			redirectAttributes.addFlashAttribute("message", errorMessage);

			return "redirect:/";
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

		String newPassword = "";
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

			/*
			 * mail pour envoyer le nouveau mot de passe
			 */
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"springEmail.xml");
			Email mm = (Email) context.getBean("email");
			mm.sendMail("from@no-spam.com", email, "[DevStarter] New Password",
					"Your new password is : " + pass);

			/* message de confirmation lors du retour sur l'accueil */
			newPassword = "<div class=\"alert alert-success\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Your new password has been sent to your email account</div>";

		} else {
			newPassword = "<div class=\"alert alert-danger\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>This email is not linked to an account!</div>";

		}

		/* message de confirmation lors du retour sur l'accueil */
		redirectAttributes.addFlashAttribute("message", newPassword);
		return "redirect:/";
	}

	/*----------LOGOUT---------------*/
	@RequestMapping(value = "/logout", produces = "text/html")
	public String logout(HttpServletRequest request) {

		request.getSession().invalidate();
		return "redirect:/";
	}

}
