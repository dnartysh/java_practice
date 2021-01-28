import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

        Course course = session.get(Course.class, 2);
        System.out.println("Курс: " + course.getName() + ", Количество студентов: " + course.getStudentsCount());

        Teacher teacher = session.get(Teacher.class, 5);
        System.out.println("ФИО: " + teacher.getName() + ", Возраст: " + teacher.getAge());

    }

}
