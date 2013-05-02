package isep.rose.devstarter.web;

import isep.rose.devstarter.domain.CommentUserProject;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/commentuserprojects")
@Controller
@RooWebScaffold(path = "commentuserprojects", formBackingObject = CommentUserProject.class)
public class CommentUserProjectController {
}
