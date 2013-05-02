package isep.rose.devstarter.web;

import isep.rose.devstarter.domain.Enumeration;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/enumerations")
@Controller
@RooWebScaffold(path = "enumerations", formBackingObject = Enumeration.class)
public class EnumerationController {
}
