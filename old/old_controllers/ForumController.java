package isep.rose.devstarter.web;

import isep.rose.devstarter.domain.Forum;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/forums")
@Controller
@RooWebScaffold(path = "forums", formBackingObject = Forum.class)
public class ForumController {
}
