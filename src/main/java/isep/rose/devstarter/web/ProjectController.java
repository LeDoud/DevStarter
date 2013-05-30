package isep.rose.devstarter.web;

import java.io.File;
import java.io.IOException;
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
import isep.rose.devstarter.domain.UploadedFile;
import isep.rose.devstarter.domain.ManageUserProject;
import isep.rose.devstarter.domain.Project;
import isep.rose.devstarter.domain.TechnologyProjectEnumeration;
import isep.rose.devstarter.domain.User;
import isep.rose.devstarter.service.MultipartFiles;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/project/**")
@Controller
public class ProjectController {
	
	private static final String UPLOAD_DIRECTORY = "/DevstarterProjectsFiles/";
	

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
    
    
    @RequestMapping(value = "/create", produces = "text/html")
    public String create(ModelMap model) {
    	
    	List<Enumeration> project_types= Enumeration.findEnumerationsByType("project_type");
    	model.addAttribute("project_types",project_types);
        return "project/create";
    }
    
    @RequestMapping(value = "/persistProject", produces = "text/html")
    public String persistProject(org.springframework.web.context.request.WebRequest webRequest, @ModelAttribute("uploadForm") MultipartFiles multipartFiles ,HttpServletRequest httpServletRequest) {
    	
    	/*sauvegarde des fichiers*/
    	List<MultipartFile> files = multipartFiles.getFiles();
    	
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
    	if(files.get(0) != null){
    		project.setPictureUrl(files.get(0).getOriginalFilename());
    		project.persist();
    		try {
				this.saveMultipartToDisk(files.get(0), project);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	
    	ManageUserProject manageUserProject = new ManageUserProject();
    	manageUserProject.setProjectId(Project.findProject(project.getIdProject()));
    	manageUserProject.setUserId(User.findUser(Integer.parseInt(webRequest.getParameter("user_id"))));
    	manageUserProject.persist();
    	
    	if(webRequest.getParameter("hidden-languages") != null){
    		String languages=webRequest.getParameter("hidden-languages").toString();
    		if(!languages.equals("")){
        		String languagesArray[]=languages.split(",");
        		for(String languageString:languagesArray){
        			if(languageString != ""){
	            		TechnologyProjectEnumeration language = new TechnologyProjectEnumeration();
	            		language.setProjectId(Project.findProject(project.getIdProject()));
	            		language.setTechnoEnumId(Enumeration.findEnumerationByNameAndType(languageString, "language"));
	            		language.persist();
        			}
            	}
        	}
    	}
    	if(webRequest.getParameter("hidden-frameworks") != null){
    		String frameworks=webRequest.getParameter("hidden-frameworks").toString(); 
    		if(!frameworks.equals("")){
    	    	String frameworksArray[]=frameworks.split(",");
    	    	for(String frameworkString:frameworksArray){
    	    		if(frameworkString != ""){
	    	    		TechnologyProjectEnumeration framework = new TechnologyProjectEnumeration();
	    	    		framework.setProjectId(Project.findProject(project.getIdProject()));
	    	    		framework.setTechnoEnumId(Enumeration.findEnumerationByNameAndType(frameworkString, "framework"));
	    	    		framework.persist();
    	    		}
    	    	}
        	}
    	}
    	
    	
    	
    	for(int i=1;i<=5;i++){
	    	if((webRequest.getParameter("doc"+i+"_title") != null) && (files.get(i) != null)){
	    		try {
					this.saveMultipartToDisk(files.get(i), project);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		UploadedFile file = new UploadedFile();
	    		file.setProjectId(Project.findProject(project.getIdProject()));
	    		file.setTitle(webRequest.getParameter("doc"+i+"_title"));
	    		file.setUrl(files.get(i).getOriginalFilename());
	    		file.persist();
	    	}
    	}
        return "redirect:/project/show/"+project.getIdProject().toString();
    }
    
    @RequestMapping(value = "/show/{id}", produces = "text/html",method=RequestMethod.GET)
    public String show(@PathVariable String id,ModelMap model) {
    	Integer idProject = Integer.parseInt(id);
    	Project project = Project.findProject(idProject);
    	model.addAttribute("project",project);
        return "project/show";
    }
    
    
    /*----------------AUTOCOMPLETION LANGUAGES---------------*/
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
    /*----------------AUTOCOMPLETION FRAMEWORKS---------------*/
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
    
    
    private String calculateDestinationDirectory(Project project) {
        String result = UPLOAD_DIRECTORY;
        result += project.getIdProject();
        return result;
    }
 
    private String calculateDestinationPath(MultipartFile file, Project project) {
        String result = this.calculateDestinationDirectory(project);
        result += "/";
        result += file.getOriginalFilename();
        return result;
    }
 
    private void saveMultipartToDisk(MultipartFile file, Project project) throws Exception {
        File dir = new File(this.calculateDestinationDirectory(project));
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File multipartFile = new File(this.calculateDestinationPath(file, project));
        file.transferTo(multipartFile);
    }
}
