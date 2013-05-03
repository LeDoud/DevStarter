package isep.rose.devstarter.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    
    @RequestMapping(value = "/create", produces = "text/html")
    public String create() {
        return "user/create";
    }
}
