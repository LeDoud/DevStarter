package isep.rose.devstarter.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
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
    public String account() {
        return "user/account";
    }
    
    @RequestMapping(value = "/signup", produces = "text/html")
    public ModelAndView signup() {
        return new ModelAndView("user/signup");
    }
    
    /*------TRAITEMENT DU FORM DINSCRIPTION PAR EMAIL*/
    @RequestMapping(value = "/signupEmail", produces = "text/html",method = RequestMethod.POST)
    public String signupEmail(@RequestParam("firstname") String firstName,@RequestParam("lastname") 
    String lastName,@RequestParam("email") String email,@RequestParam("password") String password ) {
    	User user =new User();
    	
    	user.setFirstname(firstName);
    	user.setName(lastName);
    	user.setEmail(email);
    	user.setPassword(password);
    	user.setJobEnumId(Enumeration.findEnumeration(8));
    	user.setCompteEnumId(Enumeration.findEnumeration(6));
    	user.setActive(1);
    	
    	user.persist();
        return "redirect:/user/account";
    }
    
    
    
    /*------VERIFICATION DE LUNICITE DU MAIL POUR INSCRIPTION--------------*/
    @RequestMapping(value = "/checkEmailUnicity", produces = "text/html",method = RequestMethod.POST)
    @ResponseBody
    public String checkEmailUnicity(@RequestParam("subEmail") String subEmail) {
    	User user =new User().getUserByEmail(subEmail);
    	if(user!=null){
    		return user.getEmail().toString();
    	}
        return "";
    }

}
