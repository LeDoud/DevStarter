package isep.rose.devstarter.web;

import isep.rose.devstarter.domain.DonationUserProject;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/donationuserprojects")
@Controller
@RooWebScaffold(path = "donationuserprojects", formBackingObject = DonationUserProject.class)
public class DonationUserProjectController {
}
