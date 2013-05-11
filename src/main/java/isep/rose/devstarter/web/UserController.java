package isep.rose.devstarter.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
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
import isep.rose.devstarter.domain.User;
import isep.rose.devstarter.domain.Enumeration;
@RequestMapping("/user/**")
@Controller
public class UserController {

    @RequestMapping(method = RequestMethod.POST, value = "{id}")
    public void post(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }

    @RequestMapping
    public String index() {
        return "user/index";
    }
    
    @RequestMapping(value = "/account", produces = "text/html")
    public String account(HttpServletRequest request,ModelMap model) {
    	
    	if(request.getSession().getAttribute("idUser")!=null){
    		User user = new User().findUser((Integer)(request.getSession().getAttribute("idUser")));
    		model.addAttribute("firstName",user.getFirstname());
    		return "user/account";
    	}
        return "resourceNotFound";
    }
    
    @RequestMapping(value = "/signup", produces = "text/html")
    public ModelAndView signup() {
        return new ModelAndView("user/signup");
    }
    
    
    
    /*------TRAITEMENT DU FORM DINSCRIPTION PAR EMAIL*/
    @RequestMapping(value = "/signupEmail", produces = "text/html",method = RequestMethod.POST)
    public String signupEmail(@RequestParam("firstname") String firstName,@RequestParam("lastname") 
    String lastName,@RequestParam("email") String email,@RequestParam("password") String password,RedirectAttributes redirectAttributes) {
   
    	PasswordEncoder encoder = new Md5PasswordEncoder();
    	String hashedPass = encoder.encodePassword(password, "DevStarter");
    	User user =new User();
    	
    	user.setFirstname(firstName);
    	user.setName(lastName);
    	user.setEmail(email);
    	user.setPassword(hashedPass);
    	user.setJobEnumId(Enumeration.findEnumeration(8));
    	user.setCompteEnumId(Enumeration.findEnumeration(6));
    	user.setWallet(0);
    	user.setActive(1);
    	
    	user.persist();
    	
		String accountCreated="<div class=\"alert alert-success\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Your account has been created. You can sign in now !</strong> However, we have sent you an email so you can validate your account for good.</div>";

    	redirectAttributes.addFlashAttribute("message", accountCreated);
    	return "redirect:/home/index";
    }
    
    
    /*------VERIFICATION DE LUNICITE DU MAIL POUR INSCRIPTION--------------*/
    @RequestMapping(value = "/checkEmailUnicity", produces = "text/html",method = RequestMethod.POST)
    @ResponseBody
    public String checkEmailUnicity(@RequestParam("subEmail") String subEmail) {
    	User user =new User().findUserByEmail(subEmail);
    	if(user!=null){
    		return user.getEmail().toString();
    	}
        return "";
    }

    
    /*----------LOGIN-----------------*/
    @RequestMapping(value = "/signin", produces = "text/html",method = RequestMethod.POST)
    public String signin(@RequestParam("email") String email, @RequestParam("password") String password, RedirectAttributes redirectAttributes,HttpServletRequest request) {
    	
    	PasswordEncoder encoder = new Md5PasswordEncoder();
    	String hashedPass = encoder.encodePassword(password, "DevStarter");
    	User user =new User().getUserAfterAuthentification(email,hashedPass);
    	if(user!=null){
    		request.getSession().invalidate();
    		request.getSession().setAttribute("logged", "true");
    		request.getSession().setAttribute("idUser", user.getIdUser());
    		request.getSession().setAttribute("firstName", user.getFirstname());
    		request.getSession().setAttribute("lastName", user.getName());
    		request.getSession().setAttribute("active", user.getActive());
    		request.getSession().setAttribute("wallet", user.getWallet());
   
    		return "redirect:/home/index";
    	}else{
    		
    		String errorMessage="<div class=\"alert alert-error\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Cannot sign in !</strong> Wrong email or password.</div>";
    		redirectAttributes.addFlashAttribute("message", errorMessage);

    		return "redirect:/home/index";
    	}
    }
    
    /*----------LOGOUT---------------*/
    @RequestMapping(value = "/logout", produces = "text/html")
    public String logout(HttpServletRequest request) {
    	
    		request.getSession().invalidate();
    		return "redirect:/home/index";
    }
}
