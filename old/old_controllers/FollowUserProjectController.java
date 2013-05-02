package isep.rose.devstarter.web;

import isep.rose.devstarter.domain.FollowUserProject;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/followuserprojects")
@Controller
@RooWebScaffold(path = "followuserprojects", formBackingObject = FollowUserProject.class)
public class FollowUserProjectController {
}
