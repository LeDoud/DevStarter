package isep.rose.devstarter.web;

import isep.rose.devstarter.domain.File;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/files")
@Controller
@RooWebScaffold(path = "files", formBackingObject = File.class)
public class FileController {
}
