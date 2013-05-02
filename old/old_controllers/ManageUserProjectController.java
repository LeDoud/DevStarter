package isep.rose.devstarter.web;

import isep.rose.devstarter.domain.ManageUserProject;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/manageuserprojects")
@Controller
@RooWebScaffold(path = "manageuserprojects", formBackingObject = ManageUserProject.class)
public class ManageUserProjectController {
}
