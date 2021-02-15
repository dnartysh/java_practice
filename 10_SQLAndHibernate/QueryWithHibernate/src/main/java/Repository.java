import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

public class Repository<T> {
    private Class<T> clazz;

    public Repository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public List<T> getAll(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> queryCriteria = cb.createQuery(clazz);
        Root<T> root = queryCriteria.from(clazz);
        queryCriteria.select(root);

        return session.createQuery(queryCriteria).getResultList();
    }
}
