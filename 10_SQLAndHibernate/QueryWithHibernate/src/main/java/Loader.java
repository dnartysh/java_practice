import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
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

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/skillbox",
                    "testtest", "1234567890");
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM PurchaseList;";
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()) {
                String studentName = rs.getString("student_name");
                String courseName = rs.getString("course_name");
                int price = rs.getInt("price");
                Date subscriptionDate = rs.getDate("subscription_date");

                int studentId = getStudentId(session,"From " + Student.class.getSimpleName()
                        + " Where name = '" + studentName + "'");
                int courseId = getCourseId(session,"From " + Course.class.getSimpleName()
                        + " Where name = '" + courseName + "'");

                session.createSQLQuery("INSERT INTO LinkedPurchaseList"
                    + "(student_id, course_id, student_name, course_name, price, subscription_date) VALUES ("
                    + studentId + ", "
                    + courseId + ", "
                    + "'" + studentName + "', "
                    + "'" + courseName + "', "
                    + price + ", "
                    + "'" + subscriptionDate + "')").executeUpdate();
            }

            transaction.commit();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static int getStudentId(Session session, String query) {
        List<Student> foundedList = session.createQuery(query).getResultList();

        return foundedList.get(0).getId();
    }

    public static int getCourseId(Session session, String query) {
        List<Course> foundedList = session.createQuery(query).getResultList();

        return foundedList.get(0).getId();
    }

    private static Session getSession(String fileSettings) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(fileSettings).build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        return sessionFactory.openSession();
    }
}


















