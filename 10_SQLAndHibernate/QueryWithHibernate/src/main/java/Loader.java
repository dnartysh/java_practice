import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private static Logger logger = LogManager.getLogger();
    private static Set<String> notFoundedItems = new HashSet<>();

    public static void main(String[] args) {

        try {
            Session session = getSession("hibernate.cfg.xml");
            Transaction transaction = session.beginTransaction();

            int studentId;
            int courseId;
            List<Object> listPurchases = getListFromClass(session, PurchaseList.class);

            for (Object item : listPurchases) {
                PurchaseList purchase = (PurchaseList) item;
                studentId = getStudentId(session, purchase.getId().getStudentName());
                courseId = getCourseId(session, purchase.getId().getCourseName());

                if (studentId != 0 && courseId != 0) {
                    session.createSQLQuery("INSERT INTO LinkedPurchaseList"
                            + "(student_id, course_id, student_name, "
                            + "course_name, price, subscription_date) VALUES ("
                            + studentId + ", "
                            + courseId + ", "
                            + "'" + purchase.getId().getStudentName() + "', "
                            + "'" + purchase.getId().getCourseName() + "', "
                            + purchase.getPrice() + ", "
                            + "'" + purchase.getSubscriptionDate() + "')").executeUpdate();
                } else {
                    notFoundedItems.add(studentId == 0 ? purchase.getId().getStudentName()
                            : purchase.getId().getCourseName());
                    printErrorMessage(studentId == 0 ? "Студент: " +
                            purchase.getId().getStudentName() + " - не найден" : "Курс: " +
                            purchase.getId().getCourseName() + " - не найден");
                }
            }

            if (notFoundedItems.size() != 0) {
                printErrorMessage();
            }

            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void printErrorMessage() {
        System.out.println("Следующие объекты не найдены в БД:");
        notFoundedItems.forEach(System.out::println);
        System.out.println("Более подробно см. лог 'logs/exceptions.log'");
    }

    public static void printErrorMessage(String message) {
        logger.error(message);
    }

    public static int getStudentId(Session session, String name) {
        List<Object> foundedList = getListFromClass(session, Student.class);

        for (Object item : foundedList) {
            Student student = (Student) item;
            if (student.getName().equals(name)) {
                return student.getId();
            }
        }

        return 0;
    }

    public static int getCourseId(Session session, String name) {
        List<Object> foundedList = getListFromClass(session, Course.class);

        for (Object item : foundedList) {
            Course course = (Course) item;
            if (course.getName().equals(name)) {
                return course.getId();
            }
        }

        return 0;
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


















