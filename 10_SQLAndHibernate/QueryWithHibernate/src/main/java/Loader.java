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

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        System.out.println("Подписка по id студента и id курса:\n");
        Student student = session.get(Student.class, 1);
        Course course = session.get(Course.class, 2);
        Subscription sp = session.get(Subscription.class, new SubscriptionKey(student, course));
        System.out.println("Студент: " + sp.getId().getStudent().getName() +
                " Подписка на курс: " + sp.getId().getCourse().getName() + "\n------\n");

        System.out.println("Список курсов по учителю:");
        List<Course> courses = session.get(Teacher.class, 10).getCourses();
        for (Course item : courses) {
            System.out.println("Учитель - " + item.getTeacher().getName() +
                    ", курс - " + item.getName() + ", цена курса - " + item.getPrice());
        }
        System.out.println("\n------\n");

        System.out.println("Список курсов по студенту:");
        List<Subscription> sbList = session.get(Student.class, 10).getSubscriptionsList();
        for (Subscription sb : sbList) {
            System.out.println("Студент - " + sb.getId().getStudent().getName() +
                    ", подписка на курс - " + sb.getId().getCourse().getName() +
                    ", цена курса - " + sb.getId().getCourse().getPrice());
        }
        System.out.println("\n------\n");

        transaction.commit();
    }

}
