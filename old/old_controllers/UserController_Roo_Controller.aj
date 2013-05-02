// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package isep.rose.devstarter.web;

import isep.rose.devstarter.domain.CommentUserProject;
import isep.rose.devstarter.domain.DonationUserProject;
import isep.rose.devstarter.domain.Enumeration;
import isep.rose.devstarter.domain.FollowUserProject;
import isep.rose.devstarter.domain.Forum;
import isep.rose.devstarter.domain.ManageUserProject;
import isep.rose.devstarter.domain.User;
import isep.rose.devstarter.web.UserController;
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

privileged aspect UserController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String UserController.create(@Valid User user, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, user);
            return "users/create";
        }
        uiModel.asMap().clear();
        user.persist();
        return "redirect:/users/" + encodeUrlPathSegment(user.getIdUser().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String UserController.createForm(Model uiModel) {
        populateEditForm(uiModel, new User());
        return "users/create";
    }
    
    @RequestMapping(value = "/{idUser}", produces = "text/html")
    public String UserController.show(@PathVariable("idUser") Integer idUser, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("user", User.findUser(idUser));
        uiModel.addAttribute("itemId", idUser);
        return "users/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String UserController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("users", User.findUserEntries(firstResult, sizeNo));
            float nrOfPages = (float) User.countUsers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("users", User.findAllUsers());
        }
        addDateTimeFormatPatterns(uiModel);
        return "users/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String UserController.update(@Valid User user, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, user);
            return "users/update";
        }
        uiModel.asMap().clear();
        user.merge();
        return "redirect:/users/" + encodeUrlPathSegment(user.getIdUser().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{idUser}", params = "form", produces = "text/html")
    public String UserController.updateForm(@PathVariable("idUser") Integer idUser, Model uiModel) {
        populateEditForm(uiModel, User.findUser(idUser));
        return "users/update";
    }
    
    @RequestMapping(value = "/{idUser}", method = RequestMethod.DELETE, produces = "text/html")
    public String UserController.delete(@PathVariable("idUser") Integer idUser, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        User user = User.findUser(idUser);
        user.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/users";
    }
    
    void UserController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("user_createddate_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }
    
    void UserController.populateEditForm(Model uiModel, User user) {
        uiModel.addAttribute("user", user);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("commentuserprojects", CommentUserProject.findAllCommentUserProjects());
        uiModel.addAttribute("donationuserprojects", DonationUserProject.findAllDonationUserProjects());
        uiModel.addAttribute("enumerations", Enumeration.findAllEnumerations());
        uiModel.addAttribute("followuserprojects", FollowUserProject.findAllFollowUserProjects());
        uiModel.addAttribute("forums", Forum.findAllForums());
        uiModel.addAttribute("manageuserprojects", ManageUserProject.findAllManageUserProjects());
    }
    
    String UserController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
