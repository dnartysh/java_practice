import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Loader {

    private static Logger loggerError = LogManager.getLogger();

    public static void main(String[] args) {

        try {
            Session session = getSession("hibernate.cfg.xml");
            Transaction transaction = session.beginTransaction();

            int studentId;
            int courseId;
            List<PurchaseList> listPurchases = new Repository<>(PurchaseList.class).getAll(session);

            for (PurchaseList purchase : listPurchases) {
                studentId = getStudentId(session, purchase.getId().getStudentName());
                courseId = getCourseId(session, purchase.getId().getCourseName());

                session.createSQLQuery("INSERT INTO LinkedPurchaseList"
                        + "(student_id, course_id, student_name, "
                        + "course_name, price, subscription_date) VALUES ("
                        + studentId + ", "
                        + courseId + ", "
                        + "'" + purchase.getId().getStudentName() + "', "
                        + "'" + purchase.getId().getCourseName() + "', "
                        + purchase.getPrice() + ", "
                        + "'" + purchase.getSubscriptionDate() + "')").executeUpdate();
            }

            transaction.commit();
        } catch (Exception ex) {
            loggerError.error(ex);
            ex.printStackTrace();
        }
    }

    public static int getStudentId(Session session, String name) throws Exception {
        List<Student> foundedList = new Repository<>(Student.class).getAll(session);

        for (Student student : foundedList) {
            if (student.getName().equals(name)) {
                return student.getId();
            }
        }

        throw new Exception("Студент с именем: " + name + " не найден");
    }

    public static int getCourseId(Session session, String name) throws Exception {
        List<Course> foundedList = new Repository<>(Course.class).getAll(session);

        for (Course course : foundedList) {
            if (course.getName().equals(name)) {
                return course.getId();
            }
        }

        throw new Exception("Курс с именем: " + name + " не найден");
    }

    public int getStudentIdWithStream(Session session, String name) throws Exception {
        return new Repository<>(Student.class).getAll(session)
                .stream()
                .filter(st -> st.getName().equals(name))
                .map(Student::getId)
                .findFirst()
                .orElseThrow(() -> new Exception("Студент с именем:" + name +
                        " не найден"));
    }

    public int getCourseIdWithStream(Session session, String name) throws Exception {
        return new Repository<>(Course.class).getAll(session)
                .stream()
                .filter(c -> c.getName().equals(name))
                .map(Course::getId)
                .findFirst()
                .orElseThrow(() -> new Exception("Курс с именем: " + name +
                        " не найден"));
    }

    private static List<Object> getListFromClass(Session session, Class c) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Object> queryCriteria = cb.createQuery(c);
        Root<Object> root = queryCriteria.from(c);
        queryCriteria.select(root);

        return session.createQuery(queryCriteria).getResultList();
    }

    private static Session getSession(String fileSettings) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(fileSettings).build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        return sessionFactory.openSession();
    }
}


















