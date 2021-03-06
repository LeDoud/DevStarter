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
@RooJpaActiveRecord(versionField = "", table = "ENUMERATION")
@RooDbManaged(automaticallyDelete = true)
public class Enumeration {

    public static isep.rose.devstarter.domain.Enumeration findEnumerationByName(String name) {
        List<Enumeration> enums = new ArrayList<Enumeration>();
        Query query = entityManager().createQuery("select enumeration from Enumeration enumeration " + "where name = :name ", Enumeration.class);
        query.setParameter("name", name);
        enums = query.getResultList();
        if (enums.isEmpty()) {
            return null;
        }
        return enums.get(0);
    }
	
	public static isep.rose.devstarter.domain.Enumeration findEnumerationByNameAndType(String name, String type) {
        List<Enumeration> enums = new ArrayList<Enumeration>();
        Query query = entityManager().createQuery("select enumeration from Enumeration enumeration " + "where name = :name " + " and type_id=(SELECT type.idEnumeration from Enumeration type where name= :type)", Enumeration.class);
        query.setParameter("name", name);
        query.setParameter("type", type);
        enums = query.getResultList();
        if (enums.isEmpty()) {
            return null;
        }
        return enums.get(0);
    }

    public static List<isep.rose.devstarter.domain.Enumeration> findEnumerationsByNameAndType(String name, String type) {
        List<Enumeration> enums = new ArrayList<Enumeration>();
        Query query = entityManager().createQuery("select enumeration from Enumeration enumeration " + "where name = :name " + " and type_id=(SELECT type.idEnumeration from Enumeration type where name= :type)", Enumeration.class);
        query.setParameter("name", name);
        query.setParameter("type", type);
        enums = query.getResultList();
        if (enums.isEmpty()) {
            return null;
        }
        return enums;
    }

    public static List<isep.rose.devstarter.domain.Enumeration> searchEnumerationsByNameAndType(String name, String type) {
        List<Enumeration> enums = new ArrayList<Enumeration>();
        String param = "%" + name + "%";
        Query query = entityManager().createQuery("select enumeration from Enumeration enumeration " + "where name LIKE :param " + " and type_id=(SELECT type.idEnumeration from Enumeration type where name= :type)", Enumeration.class);
        query.setParameter("param", param);
        query.setParameter("type", type);
        enums = query.getResultList();
        if (enums.isEmpty()) {
            return null;
        }
        return enums;
    }

    public static List<isep.rose.devstarter.domain.Enumeration> findEnumerationsByType(String type) {
        List<Enumeration> enums = new ArrayList<Enumeration>();
        Query query = entityManager().createQuery("select enumeration from Enumeration enumeration " + "where type_id=(SELECT type.idEnumeration from Enumeration type where name= :type)", Enumeration.class);
        query.setParameter("type", type);
        enums = query.getResultList();
        if (enums.isEmpty()) {
            return null;
        }
        return enums;
    }
}
