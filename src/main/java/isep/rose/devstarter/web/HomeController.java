package isep.rose.devstarter.web;

import java.util.List;

import isep.rose.devstarter.domain.Project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping("/")
@Controller
public class HomeController {

    @RequestMapping(method = RequestMethod.POST, value = "{id}")
    public void post(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index(ModelMap model) {
    	List<Project> topProjects = Project.findTopProjects();
    	List<Project> newProjects = Project.findNewProjects();
    	List<Project> startingSoonProjects = Project.findStartingSoonProjects();
    	model.addAttribute("topProjects", topProjects);
    	model.addAttribute("newProjects", newProjects);
    	model.addAttribute("startingSoonProjects", startingSoonProjects);
        return "home/index";
    }
}
