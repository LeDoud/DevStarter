package isep.rose.devstarter.web;

import isep.rose.devstarter.domain.TechnologyProjectEnumeration;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/technologyprojectenumerations")
@Controller
@RooWebScaffold(path = "technologyprojectenumerations", formBackingObject = TechnologyProjectEnumeration.class)
public class TechnologyProjectEnumerationController {
}
