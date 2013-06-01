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
@RooJpaActiveRecord(versionField = "", table = "DONATION_USER_PROJECT")
@RooDbManaged(automaticallyDelete = true)
public class DonationUserProject {

    public static List<isep.rose.devstarter.domain.DonationUserProject> findDonationUserProjectByUserId(User user) {
        List<DonationUserProject> donationUserProject = new ArrayList<DonationUserProject>();
        Query query = entityManager().createQuery("select donation from DonationUserProject donation " + "where userId = :userId", DonationUserProject.class);
        query.setParameter("userId", user);
        donationUserProject = query.getResultList();
        if (donationUserProject.isEmpty()) {
            return null;
        }
        return donationUserProject;
    }
}
