package isep.rose.devstarter.web;

import java.util.ArrayList;
import java.util.List;

import isep.rose.devstarter.domain.Enumeration;
import isep.rose.devstarter.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String create(ModelMap model) {
    	
    	List<Enumeration> project_types= Enumeration.findEnumerationsByType("project_type");
    	model.addAttribute("project_types",project_types);
        return "project/create";
    }
    
    @RequestMapping(value = "/persistProject", produces = "text/html")
    public String persistProject(org.springframework.web.context.request.WebRequest webRequest) {
    	
    	
  
        return "project/create";
    }
    
    @RequestMapping(value = "/languageAutocomplete", method = RequestMethod.POST)
    @ResponseBody
    public String languageAutocomplete(@RequestParam("typeahead") String name) {
    	List<Enumeration> enums = Enumeration.searchEnumerationsByNameAndType(name,"language");
    	String languages="";
    	if(enums != null){
	    	int i=0;
	    	for(Enumeration enumf : enums){
	    		if(i != 0){
	    			languages +=";";
	    		}
	    		languages+=enumf.getName();
	    		i++;
	    	}
    	}
        return languages;
    }
    
    @RequestMapping(value = "/frameworkAutocomplete", method = RequestMethod.POST)
    @ResponseBody
    public String frameworkAutocomplete(@RequestParam("typeahead") String name) {
    	List<Enumeration> enums = Enumeration.searchEnumerationsByNameAndType(name,"framework");
    	String frameworks="";
    	if(enums != null){
	    	int i=0;
	    	for(Enumeration enumf : enums){
	    		if(i != 0){
	    			frameworks +=";";
	    		}
	    		frameworks+=enumf.getName();
	    		i++;
	    	}
    	}
        return frameworks;
    }
    
}
