package isep.rose.devstarter.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import isep.rose.devstarter.domain.Enumeration;
import isep.rose.devstarter.domain.User;
import isep.rose.devstarter.domain.Forum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/forum/**")
@Controller
public class ForumController {

	@RequestMapping(method = RequestMethod.POST, value = "{id}")
	public void post(@PathVariable Long id, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
	}

	/*----------LIST OF TOPICS----------------*/
	@RequestMapping(value = "/topics", produces = "text/html")
	public String topics(HttpServletRequest request, ModelMap model) {

		List<Forum> topics = Forum.findAllTopics();
		//ArrayList<Integer> posts = new ArrayList(topics.size());
		int i=0;
		for(Forum top:topics){
			Forum.findAllMessageTopic(top.getParentId());
			//posts.set(i, 2);
			i++;
		}
		int posts=1;
		model.addAttribute("topics", topics);
		model.addAttribute("posts", posts);
		return "forum/topics";
	}
	
	/*----------ACCESS TO TOPIC MESSAGES----------------*/
	@RequestMapping(value = "/topic/{idTopic}", produces = "text/html", method = RequestMethod.GET)
	public String showTopic(@PathVariable Integer idTopic,
			HttpServletRequest request, ModelMap model) {

		Forum topic = Forum.findForum(idTopic);
		List<Forum> messages = Forum.findAllMessageTopic(idTopic);
		model.addAttribute("posts", messages);
		model.addAttribute("topic", topic);
		
		return "forum/show";
	}

	/*----------ACCESS TO NEW TOPIC----------------*/
	@RequestMapping(value = "/newTopic", produces = "text/html")
	public String newTopic(HttpServletRequest request, ModelMap model) {

		return "forum/create";
	}
	
	/*----------CREATE NEW TOPIC----------------*/
	@RequestMapping(value = "/createTopic", produces = "text/html", method = RequestMethod.POST)
	public String createTopic(@RequestParam("title") String title,@RequestParam("message") String message, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String topicCreated ="";
		if (request.getSession().getAttribute("idUser") != null) {
		
		Forum newTopic = new Forum();
		User user = new User().findUser((Integer) (request.getSession()
				.getAttribute("idUser")));
		
		newTopic.setDateCreated(Calendar.getInstance());
		newTopic.setUserId(user);
		newTopic.setTitle(title);
		newTopic.setMessage(message);
		newTopic.setParentId(0);//topic
		newTopic.setTypeEnumId(Enumeration.findEnumerationByName("topic"));
		newTopic.persist();
			
		//int idTopic=1;
		topicCreated = "<div class=\"alert alert-success\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Your topic has been created !</div>";
		redirectAttributes.addFlashAttribute("message", topicCreated);
		//return "forum/showTopic/"+idTopic;
		return "redirect:/forum/topics";
		
		}else{
			topicCreated = "<div class=\"alert alert-danger\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>You have to be logged to create a topic !</div>";
			redirectAttributes.addFlashAttribute("message", topicCreated);
			return "redirect:/forum/topics";
		}
	}

	/*----------CREATE NEW MESSAGE ON TOPIC----------------*/
	@RequestMapping(value = "/newMessage", produces = "text/html", method = RequestMethod.POST)
	public String newMessage(@RequestParam("topic") Integer idTopic, @RequestParam("comment") String comment, HttpServletRequest request, RedirectAttributes redirectAttributes) {

		String messageCreated ="";
		if (request.getSession().getAttribute("idUser") != null) {
		
		Forum newTopic = new Forum();
		User user = new User().findUser((Integer) (request.getSession()
				.getAttribute("idUser")));
		
		newTopic.setDateCreated(Calendar.getInstance());
		newTopic.setUserId(user);
		newTopic.setTitle("Reply");
		newTopic.setMessage(comment);
		newTopic.setParentId(idTopic);//topic
		newTopic.setTypeEnumId(Enumeration.findEnumerationByName("message"));
		newTopic.persist();
			
		messageCreated = "<div class=\"alert alert-success\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>Your message has been posted !</div>";
		redirectAttributes.addFlashAttribute("message", messageCreated);
		return "redirect:/forum/topic/"+idTopic;
		
		}else{
			messageCreated = "<div class=\"alert alert-danger\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>You have to be logged to post a message !</div>";
			redirectAttributes.addFlashAttribute("message",messageCreated);
			return "redirect:/forum/topic/"+idTopic;
		}
	}

	/*----------EDIT MESSAGE ACCESS----------------*/
	@RequestMapping(value = "/editMessage", produces = "text/html")
	public String editMessage(HttpServletRequest request, ModelMap model) {

		return "forum/edit";
	}
}