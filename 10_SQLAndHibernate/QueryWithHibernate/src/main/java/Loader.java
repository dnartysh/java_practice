import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Loader {

    public static void main(String[] args) {
        try {
            Session session = getSession("hibernate.cfg.xml");
            Transaction transaction = session.beginTransaction();

            int studentId;
            int courseId;
            List<PurchaseList> listPurchases = getListPurchases(session);

            for (PurchaseList purchase : listPurchases) {
                studentId = getStudentId(session, purchase.getId().getStudentName());
                courseId = getCourseId(session, purchase.getId().getCourseName());

                session.createSQLQuery("INSERT INTO LinkedPurchaseList"
                    + "(student_id, course_id, student_name, course_name, price, subscription_date) VALUES ("
                    + studentId + ", "
                    + courseId + ", "
                    + "'" + purchase.getId().getStudentName() + "', "
                    + "'" + purchase.getId().getCourseName() + "', "
                    + purchase.getPrice() + ", "
                    + "'" + purchase.getSubscriptionDate() + "')").executeUpdate();
            }

//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/skillbox",
//                    "testtest", "1234567890");
//            Statement statement = connection.createStatement();
//            String query = "SELECT * FROM PurchaseList;";
//            ResultSet rs = statement.executeQuery(query);

//            while(rs.next()) {
//                String studentName = rs.getString("student_name");
//                String courseName = rs.getString("course_name");
//                int price = rs.getInt("price");
//                Date subscriptionDate = rs.getDate("subscription_date");
//
//                int studentId = getStudentId(session,"From " + Student.class.getSimpleName()
//                        + " Where name = '" + studentName + "'");
//                int courseId = getCourseId(session,"From " + Course.class.getSimpleName()
//                        + " Where name = '" + courseName + "'");
//
//                session.createSQLQuery("INSERT INTO LinkedPurchaseList"
//                    + "(student_id, course_id, student_name, course_name, price, subscription_date) VALUES ("
//                    + studentId + ", "
//                    + courseId + ", "
//                    + "'" + studentName + "', "
//                    + "'" + courseName + "', "
//                    + price + ", "
//                    + "'" + subscriptionDate + "')").executeUpdate();
//            }

            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static int getStudentId(Session session, String name) {
        List<Student> foundedList = getListStudents(session);

        for (Student student : foundedList) {
            if (student.getName().equals(name)) {
                return student.getId();
            }
        }

        return 0;
    }

    public static int getCourseId(Session session, String name) {
        List<Course> foundedList = getListCourses(session);

        for (Course course : foundedList) {
            if (course.getName().equals(name)) {
                return course.getId();
            }
        }

        return 0;
    }

    private static List<PurchaseList> getListPurchases(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PurchaseList> queryCriteria = cb.createQuery(PurchaseList.class);
        Root<PurchaseList> root = queryCriteria.from(PurchaseList.class);
        queryCriteria.select(root);

        return session.createQuery(queryCriteria).getResultList();
    }

    private static List<Student> getListStudents(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Student> queryCriteria = cb.createQuery(Student.class);
        Root<Student> root = queryCriteria.from(Student.class);
        queryCriteria.select(root);

        return session.createQuery(queryCriteria).getResultList();
    }

    private static List<Course> getListCourses(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Course> queryCriteria = cb.createQuery(Course.class);
        Root<Course> root = queryCriteria.from(Course.class);
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


















