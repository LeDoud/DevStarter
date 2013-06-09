package isep.rose.devstarter.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import isep.rose.devstarter.domain.CommentUserProject;
import isep.rose.devstarter.domain.Enumeration;
import isep.rose.devstarter.domain.FollowUserProject;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/project/**")
@Controller
public class ProjectController {

	private static final String UPLOAD_DIRECTORY = "/projectsFiles/";

	@Autowired
	private ServletContext context;

	@RequestMapping(method = RequestMethod.POST, value = "{id}")
	public void post(@PathVariable Long id, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
	}

	@RequestMapping(method = RequestMethod.GET, value = "/index")
	public String index() {
		return "project/index";
	}

	@RequestMapping(value = "/search", produces = "text/html")
	public String search(ModelMap model) {
		List<Enumeration> project_types = Enumeration
				.findEnumerationsByType("project_type");
		model.addAttribute("project_types", project_types);
		return "project/search";
	}

	@RequestMapping(value = "/create", produces = "text/html")
	public String create(ModelMap model) {

		List<Enumeration> project_types = Enumeration
				.findEnumerationsByType("project_type");
		model.addAttribute("project_types", project_types);
		return "project/create";
	}
	
	/*----------COMMENT PROJECT----------------*/
	@RequestMapping(value = "/comment", produces = "text/html", method = RequestMethod.POST)
	public String comment(@RequestParam("comment") String comment, @RequestParam("project") int idProject,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String messageDone="";
		if (request.getSession().getAttribute("idUser") != null) {
			
			User user = new User().findUser((Integer) (request.getSession()
					.getAttribute("idUser")));
			Project project = new Project().findProject(idProject);
			//FollowUserProject followed = new FollowUserProject();
			CommentUserProject newComment = new CommentUserProject();

				newComment.setProjectId(project);
				newComment.setUserId(user);
				newComment.setMessage(comment);
				newComment.setTitle("Comment");
				newComment.setCreatedDate(Calendar.getInstance());
				newComment.persist();
				messageDone="<div class=\"alert alert-success\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Your comment has been posted!</div>";


		}else{
			messageDone="<div class=\"alert alert-warning\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>You must be logged to leave a comment to this project!</div>";			
		}
		
		/* message de confirmation lors du retour sur le projet */
		redirectAttributes.addFlashAttribute("message", messageDone);
		return "redirect:/project/show/"+ idProject;
	}

	/*----------RECHERCHE----------------*/
	@RequestMapping(value = "/find", produces = "text/html")
	public String find(
			org.springframework.web.context.request.WebRequest webRequest,
			HttpServletRequest httpServletRequest,ModelMap model) {

		/* dates */
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date startDate = null;
		String startDateString="";
		String endDateString="";
		if(webRequest.getParameter("start_date") != null){
			if(!webRequest.getParameter("start_date").equals("")){
				startDateString = webRequest.getParameter("start_date");
				startDate = new Date();
				try {
					startDate = df.parse(startDateString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		Date endDate = null;
		if(webRequest.getParameter("end_date") != null){
			if(!webRequest.getParameter("end_date").equals("")){
				endDateString = webRequest.getParameter("end_date");
				endDate = new Date();
				try {
					endDate = df.parse(endDateString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		
		String title="";
		if(webRequest.getParameter("title") != null){
		if(!webRequest.getParameter("title").equals("")){
			title=webRequest.getParameter("title");
		}
		}
		String languagesArray[] = null;
		if (webRequest.getParameter("hidden-languages") != null) {
			String languages = webRequest.getParameter("hidden-languages").toString();
			if (!languages.equals("")) {
				languagesArray = languages.split(",");
				}
			}
		
		String frameworksArray[] =null;
		if (webRequest.getParameter("hidden-frameworks") != null) {
			String frameworks = webRequest.getParameter("hidden-frameworks")
					.toString();
			if (!frameworks.equals("")) {
				frameworksArray = frameworks.split(",");
			}
		}
		
		Integer application_type=-1;
		if(webRequest.getParameter("application_type") != null){
			application_type=Integer.parseInt(webRequest.getParameter("application_type"));
		}
		List<Project> projects=Project.searchProjects(title,startDate,endDate,application_type,languagesArray,frameworksArray);
		
		boolean firstCall=true;
		model.addAttribute("projects", projects);
		model.addAttribute("title", title);
		model.addAttribute("startDate", startDateString);
		model.addAttribute("endDate", endDateString);
		model.addAttribute("application_type", application_type);
		model.addAttribute("firstCall", firstCall);
		List<Enumeration> project_types = Enumeration.findEnumerationsByType("project_type");
		model.addAttribute("project_types", project_types);
		return "project/search";
	}

	/*----------CREATION PROJET----------------*/
	@RequestMapping(value = "/persistProject", produces = "text/html")
	public String persistProject(
			org.springframework.web.context.request.WebRequest webRequest,
			@ModelAttribute("uploadForm") MultipartFiles multipartFiles,
			HttpServletRequest httpServletRequest) {

		List<MultipartFile> files = multipartFiles.getFiles();

		/* dates */
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

		/* creation du projet */
		Project project = new Project();
		project.setName(webRequest.getParameter("title"));
		project.setDescription(webRequest.getParameter("description"));
		project.setTypeId(Enumeration.findEnumeration(Integer
				.parseInt(webRequest.getParameter("project_type"))));
		project.setStartDate(startDate);
		project.setMinEndDate(minDate);
		project.setMaxEndDate(maxDate);
		project.setEffectiveEndDate(maxDate);
		project.setFund(Integer.parseInt(webRequest.getParameter("amount_fund")));
		project.setDateCreated(Calendar.getInstance());
		project.setActive(1);
		project.setRank(0);
		project.persist();
		if (files.get(0) != null) {
			project.setPictureUrl(files.get(0).getOriginalFilename());
			try {
				project.setPictureBytes(files.get(0).getBytes());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			project.persist();
			/*
			 * try { this.saveMultipartToDisk(files.get(0), project); } catch
			 * (Exception e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
		}

		ManageUserProject manageUserProject = new ManageUserProject();
		manageUserProject.setProjectId(Project.findProject(project
				.getIdProject()));
		manageUserProject.setUserId(User.findUser(Integer.parseInt(webRequest
				.getParameter("user_id"))));
		manageUserProject.persist();

		if (webRequest.getParameter("hidden-languages") != null) {
			String languages = webRequest.getParameter("hidden-languages")
					.toString();
			if (!languages.equals("")) {
				String languagesArray[] = languages.split(",");
				for (String languageString : languagesArray) {
					if (languageString != "") {
						TechnologyProjectEnumeration language = new TechnologyProjectEnumeration();
						language.setProjectId(Project.findProject(project
								.getIdProject()));
						language.setTechnoEnumId(Enumeration
								.findEnumerationByNameAndType(languageString,
										"language"));
						language.persist();
					}
				}
			}
		}
		if (webRequest.getParameter("hidden-frameworks") != null) {
			String frameworks = webRequest.getParameter("hidden-frameworks")
					.toString();
			if (!frameworks.equals("")) {
				String frameworksArray[] = frameworks.split(",");
				for (String frameworkString : frameworksArray) {
					if (frameworkString != "") {
						TechnologyProjectEnumeration framework = new TechnologyProjectEnumeration();
						framework.setProjectId(Project.findProject(project
								.getIdProject()));
						framework.setTechnoEnumId(Enumeration
								.findEnumerationByNameAndType(frameworkString,
										"framework"));
						framework.persist();
					}
				}
			}
		}

		/*-------------FICHIERS---------*/
		for (int i = 1; i <= 5; i++) {
			if ((webRequest.getParameter("doc" + String.valueOf(i) + "_title") != null)
					&& (files.get(i) != null)) {
				UploadedFile file = new UploadedFile();
				try {
					file.setBytes(files.get(i).getBytes());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				file.setProjectId(Project.findProject(project.getIdProject()));
				file.setTitle(webRequest.getParameter("doc" + i + "_title"));
				file.setUrl(files.get(i).getOriginalFilename());
				file.persist();
			}
		}
		return "redirect:/project/show/" + project.getIdProject().toString();
	}

	@RequestMapping(value = "/show/{id}", produces = "text/html", method = RequestMethod.GET)
	public String show(@PathVariable String id, ModelMap model) {
		Integer idProject = Integer.parseInt(id);
		Project project = Project.findProject(idProject);
		project.setRank(project.getRank() + 1);
		project.persist();
		List<CommentUserProject> comments = CommentUserProject.findCommentUserProjectByProjectId(project);
		model.addAttribute("project", project);
		model.addAttribute("comments", comments);
		return "project/show";
	}

	@RequestMapping(value = "/downloadImage/{idProject}", produces = "text/html", method = RequestMethod.GET)
	public void downloadImage(@PathVariable int idProject, ModelMap model,
			HttpServletResponse response) throws IOException {
		byte[] pictureBytes = Project.findProject(idProject).getPictureBytes();

		OutputStream o = response.getOutputStream();
		o.write(pictureBytes);
		o.flush();
		o.close();
	}

	@RequestMapping(value = "/downloadFile/{idFile}/{filename}", produces = "text/html", method = RequestMethod.GET)
	public void downloadFile(@PathVariable int idFile,
			@PathVariable String filename, ModelMap model,
			HttpServletResponse response) throws IOException {
		byte[] fileBytes = UploadedFile.findUploadedFile(idFile).getBytes();
		OutputStream o = response.getOutputStream();
		o.write(fileBytes);
		o.flush();
		o.close();
	}

	@RequestMapping(value = "/list/{criteria}", produces = "text/html", method = RequestMethod.GET)
	public String list(@PathVariable String criteria, ModelMap model,
			HttpServletRequest request) {
		List<Project> listProjects = new ArrayList<Project>();
		String title = "";
		if (criteria.equals("top")) {
			listProjects = Project.findTopProjects();
			title = "Top projects";
		} else if (criteria.equals("newest")) {
			listProjects = Project.findNewProjects();
			title = "Last created projects";
		} else if (criteria.equals("starting")) {
			listProjects = Project.findStartingSoonProjects();
			title = "Projects starting soon";
		} else if (criteria.equals("followed")) {
			if (request.getSession().getAttribute("idUser") != null) {
				listProjects = Project.findFollowedProjects((Integer) (request
						.getSession().getAttribute("idUser")));
				title = "Your followed projects";
			}
		} else if (criteria.equals("funded")) {
			if (request.getSession().getAttribute("idUser") != null) {
				listProjects = Project.findFundedProjects((Integer) (request
						.getSession().getAttribute("idUser")));
				title = "Your funded projects";
			}
		}
		model.addAttribute("title", title);
		model.addAttribute("listProjects", listProjects);
		return "project/list";
	}

	/*----------ACCES A EDITION DE PROJET-------------*/
	@RequestMapping(value = "/editProject/{idProject}", produces = "text/html", method = RequestMethod.GET)
	public String editProject(@PathVariable Integer idProject,
			HttpServletRequest request, ModelMap model) {
		if (request.getSession().getAttribute("idUser") != null) {
			List<Enumeration> project_types = Enumeration
					.findEnumerationsByType("project_type");
			model.addAttribute("project_types", project_types);

			Project myproject = new Project().findProject(idProject);
			model.addAttribute("myproject", myproject);
			return "project/edit";
		}
		return "resourceNotFound";

	}

	/*---------- EDITION DU PROJET-------------*/
	@RequestMapping(value = "/persistEditProject", produces = "text/html")
	public String persistEditProject(
			org.springframework.web.context.request.WebRequest webRequest,
			@ModelAttribute("uploadForm") MultipartFiles multipartFiles,
			HttpServletRequest httpServletRequest) {

		List<MultipartFile> files = multipartFiles.getFiles();

		/* dates */
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

		/* edit du projet */
		Project project = Project.findProject(Integer.parseInt(webRequest
				.getParameter("idProject")));
		project.setName(webRequest.getParameter("title"));
		project.setDescription(webRequest.getParameter("description"));
		project.setTypeId(Enumeration.findEnumeration(Integer
				.parseInt(webRequest.getParameter("project_type"))));
		project.setStartDate(startDate);
		project.setMinEndDate(minDate);
		project.setMaxEndDate(maxDate);
		project.setEffectiveEndDate(maxDate);
		project.setFund(Integer.parseInt(webRequest.getParameter("amount_fund")));
		project.setDateCreated(Calendar.getInstance());
		project.setActive(1);
		project.setRank(0);
		project.persist();
		if (files.get(0) != null) {
			if (!files.get(0).getOriginalFilename().equals("")) {
				project.setPictureUrl(files.get(0).getOriginalFilename());

				try {
					project.setPictureBytes(files.get(0).getBytes());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				project.persist();
			}
		}

		ManageUserProject manageUserProject = ManageUserProject
				.findManageUserProject(Integer.parseInt(webRequest
						.getParameter("idManage")));
		manageUserProject.setProjectId(Project.findProject(project
				.getIdProject()));
		manageUserProject.setUserId(User.findUser(Integer.parseInt(webRequest
				.getParameter("user_id"))));
		manageUserProject.persist();

		if (webRequest.getParameter("hidden-languages") != null) {
			String languages = webRequest.getParameter("hidden-languages")
					.toString();
			if (!languages.equals("")) {
				String languagesArray[] = languages.split(",");
				for (String languageString : languagesArray) {
					if (languageString != "") {
						// TechnologyProjectEnumeration language =
						// TechnologyProjectEnumeration.findTechnologyProjectEnumeration(Integer.parseInt(webRequest.getParameter("idTechnology")));
						TechnologyProjectEnumeration language = new TechnologyProjectEnumeration();
						language.setProjectId(Project.findProject(project
								.getIdProject()));
						language.setTechnoEnumId(Enumeration
								.findEnumerationByNameAndType(languageString,
										"language"));
						language.persist();
					}
				}
			}
		}
		if (webRequest.getParameter("hidden-frameworks") != null) {
			String frameworks = webRequest.getParameter("hidden-frameworks")
					.toString();
			if (!frameworks.equals("")) {
				String frameworksArray[] = frameworks.split(",");
				for (String frameworkString : frameworksArray) {
					if (frameworkString != "") {
						// TechnologyProjectEnumeration framework = new
						// TechnologyProjectEnumeration().findTechnologyProjectEnumeration(Integer.parseInt(webRequest.getParameter("idTechnology")));
						TechnologyProjectEnumeration framework = new TechnologyProjectEnumeration();
						framework.setProjectId(Project.findProject(project
								.getIdProject()));
						framework.setTechnoEnumId(Enumeration
								.findEnumerationByNameAndType(frameworkString,
										"framework"));
						framework.persist();
					}
				}
			}
		}

		/*-------------FICHIERS---------*/
		for (int i = 1; i <= 5; i++) {
			if ((webRequest.getParameter("doc" + i + "_title") != null)
					&& (files.get(i) != null)) {
				UploadedFile file = new UploadedFile();
				try {
					file.setBytes(files.get(i).getBytes());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				file.setProjectId(Project.findProject(project.getIdProject()));
				file.setTitle(webRequest.getParameter("doc" + i + "_title"));
				file.setUrl(files.get(i).getOriginalFilename());
				file.persist();
			}
		}
		return "redirect:/project/show/" + project.getIdProject().toString();
	}

	/*----------------AUTOCOMPLETION LANGUAGES---------------*/
	@RequestMapping(value = "/languageAutocomplete", method = RequestMethod.POST)
	@ResponseBody
	public String languageAutocomplete(@RequestParam("typeahead") String name) {
		List<Enumeration> enums = Enumeration.searchEnumerationsByNameAndType(
				name, "language");
		String languages = "";
		if (enums != null) {
			int i = 0;
			for (Enumeration enumf : enums) {
				if (i != 0) {
					languages += ";";
				}
				languages += enumf.getName();
				i++;
			}
		}
		return languages;
	}

	/*----------------AUTOCOMPLETION FRAMEWORKS---------------*/
	@RequestMapping(value = "/frameworkAutocomplete", method = RequestMethod.POST)
	@ResponseBody
	public String frameworkAutocomplete(@RequestParam("typeahead") String name) {
		List<Enumeration> enums = Enumeration.searchEnumerationsByNameAndType(
				name, "framework");
		String frameworks = "";
		if (enums != null) {
			int i = 0;
			for (Enumeration enumf : enums) {
				if (i != 0) {
					frameworks += ";";
				}
				frameworks += enumf.getName();
				i++;
			}
		}
		return frameworks;
	}

}
