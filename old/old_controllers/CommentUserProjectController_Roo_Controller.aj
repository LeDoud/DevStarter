// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package isep.rose.devstarter.web;

import isep.rose.devstarter.domain.CommentUserProject;
import isep.rose.devstarter.domain.Project;
import isep.rose.devstarter.domain.User;
import isep.rose.devstarter.web.CommentUserProjectController;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect CommentUserProjectController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String CommentUserProjectController.create(@Valid CommentUserProject commentUserProject, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, commentUserProject);
            return "commentuserprojects/create";
        }
        uiModel.asMap().clear();
        commentUserProject.persist();
        return "redirect:/commentuserprojects/" + encodeUrlPathSegment(commentUserProject.getIdComment().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String CommentUserProjectController.createForm(Model uiModel) {
        populateEditForm(uiModel, new CommentUserProject());
        return "commentuserprojects/create";
    }
    
    @RequestMapping(value = "/{idComment}", produces = "text/html")
    public String CommentUserProjectController.show(@PathVariable("idComment") Integer idComment, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("commentuserproject", CommentUserProject.findCommentUserProject(idComment));
        uiModel.addAttribute("itemId", idComment);
        return "commentuserprojects/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String CommentUserProjectController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("commentuserprojects", CommentUserProject.findCommentUserProjectEntries(firstResult, sizeNo));
            float nrOfPages = (float) CommentUserProject.countCommentUserProjects() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("commentuserprojects", CommentUserProject.findAllCommentUserProjects());
        }
        addDateTimeFormatPatterns(uiModel);
        return "commentuserprojects/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String CommentUserProjectController.update(@Valid CommentUserProject commentUserProject, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, commentUserProject);
            return "commentuserprojects/update";
        }
        uiModel.asMap().clear();
        commentUserProject.merge();
        return "redirect:/commentuserprojects/" + encodeUrlPathSegment(commentUserProject.getIdComment().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{idComment}", params = "form", produces = "text/html")
    public String CommentUserProjectController.updateForm(@PathVariable("idComment") Integer idComment, Model uiModel) {
        populateEditForm(uiModel, CommentUserProject.findCommentUserProject(idComment));
        return "commentuserprojects/update";
    }
    
    @RequestMapping(value = "/{idComment}", method = RequestMethod.DELETE, produces = "text/html")
    public String CommentUserProjectController.delete(@PathVariable("idComment") Integer idComment, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        CommentUserProject commentUserProject = CommentUserProject.findCommentUserProject(idComment);
        commentUserProject.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/commentuserprojects";
    }
    
    void CommentUserProjectController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("commentUserProject_createddate_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }
    
    void CommentUserProjectController.populateEditForm(Model uiModel, CommentUserProject commentUserProject) {
        uiModel.addAttribute("commentUserProject", commentUserProject);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("projects", Project.findAllProjects());
        uiModel.addAttribute("users", User.findAllUsers());
    }
    
    String CommentUserProjectController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
