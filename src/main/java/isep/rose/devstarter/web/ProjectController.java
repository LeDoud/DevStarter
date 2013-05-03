package isep.rose.devstarter.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/project/**")
@Controller
public class ProjectController {

    @RequestMapping(method = RequestMethod.POST, value = "{id}")
    public void post(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }

    @RequestMapping
    public String index() {
        return "project/index";
    }
    
    @RequestMapping(value = "/search", produces = "text/html")
    public String search() {
        return "project/search";
    }
    
    @RequestMapping(value = "/show", produces = "text/html")
    public String show() {
        return "project/show";
    }
    
    @RequestMapping(value = "/create", produces = "text/html")
    public String create() {
        return "project/create";
    }
}
