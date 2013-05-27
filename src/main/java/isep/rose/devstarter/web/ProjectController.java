package isep.rose.devstarter.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import isep.rose.devstarter.domain.Enumeration;
import isep.rose.devstarter.domain.ManageUserProject;
import isep.rose.devstarter.domain.Project;
import isep.rose.devstarter.domain.TechnologyProjectEnumeration;
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
    	
    	/*dates*/
    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
    	String startDateString = webRequest.getParameter("start_date");
        Date startDate = new Date();
        try {
            startDate = df.parse(startDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String minDateString = webRequest.getParameter("min_date");
        Date minDate = new Date();
        try {
            minDate = df.parse(minDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String maxDateString = webRequest.getParameter("max_date");
        Date maxDate = new Date();
        try {
            maxDate = df.parse(maxDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
    	/*creation du projet*/
    	Project project = new Project();
    	project.setName(webRequest.getParameter("title"));
    	project.setDescription(webRequest.getParameter("description"));
    	project.setTypeId(Enumeration.findEnumeration(Integer.parseInt(webRequest.getParameter("project_type"))));
    	project.setStartDate(startDate);
    	project.setMinEndDate(minDate);
    	project.setMaxEndDate(maxDate);
    	project.setEffectiveEndDate(maxDate);
    	project.setFund(Integer.parseInt(webRequest.getParameter("amount_fund")));
    	project.setDateCreated(Calendar.getInstance());
    	project.setActive(1);
    	project.persist();
    	
    	
    	ManageUserProject manageUserProject = new ManageUserProject();
    	manageUserProject.setProjectId(Project.findProject(project.getIdProject()));
    	manageUserProject.setUserId(User.findUser(Integer.parseInt(webRequest.getParameter("user_id"))));
    	manageUserProject.persist();
    	
    	
    	String languages=webRequest.getParameter("hidden-languages");
    	String frameworks=webRequest.getParameter("hidden-frameworks"); 	
    	if(languages != ""){
    		String languagesArray[]=languages.split(",");
    		for(String languageString:languagesArray){
        		TechnologyProjectEnumeration language = new TechnologyProjectEnumeration();
        		language.setProjectId(Project.findProject(project.getIdProject()));
        		language.setTechnoEnumId(Enumeration.findEnumerationByNameAndType(languageString, "language"));
        		language.persist();
        	}
    	}
    	if(languages != ""){
    	String frameworksArray[]=frameworks.split(",");
    	for(String frameworkString:frameworksArray){
    		TechnologyProjectEnumeration framework = new TechnologyProjectEnumeration();
    		framework.setProjectId(Project.findProject(project.getIdProject()));
    		framework.setTechnoEnumId(Enumeration.findEnumerationByNameAndType(frameworkString, "framework"));
    		framework.persist();
    	}
    	}
    	

    	if(webRequest.getParameter("description") != null){
    		
    	}
        return "home/index";
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
