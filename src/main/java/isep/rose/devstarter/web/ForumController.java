package isep.rose.devstarter.web;

import isep.rose.devstarter.domain.User;
import isep.rose.devstarter.domain.Forum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/forum/**")
@Controller
public class ForumController {

	@RequestMapping(method = RequestMethod.POST, value = "{id}")
	public void post(@PathVariable Long id, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
	}

	@RequestMapping(value = "/topics", produces = "text/html")
	public String topics(HttpServletRequest request, ModelMap model) {

		return "forum/topics";
	}
	
	@RequestMapping(value = "/topic/{idTopic}", produces = "text/html", method = RequestMethod.GET)
	public String showTopic(@PathVariable Integer idTopic,
			HttpServletRequest request, ModelMap model) {

		Forum topic = new Forum().findForum(idTopic);
		model.addAttribute("topic", topic);
		
		return "forum/show";
	}

	@RequestMapping(value = "/newTopic", produces = "text/html")
	public String newTopic(HttpServletRequest request, ModelMap model) {

		return "forum/create";
	}

	@RequestMapping(value = "/newMessage", produces = "text/html")
	public String newMessage(HttpServletRequest request, ModelMap model) {

		return "forum/show";
	}

	@RequestMapping(value = "/editMessage", produces = "text/html")
	public String editMessage(HttpServletRequest request, ModelMap model) {

		return "forum/edit";
	}
}