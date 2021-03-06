package isep.rose.devstarter.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "COMMENT_USER_PROJECT")
@RooDbManaged(automaticallyDelete = true)
public class CommentUserProject {

}
