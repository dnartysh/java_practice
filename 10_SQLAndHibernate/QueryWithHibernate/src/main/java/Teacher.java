import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "Teachers")
public class Teacher {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Setter
    private String name;
    @Setter
    private Integer salary;
    @Setter
    private Integer age;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Course> courses;

    public void addCourse(Course course) {
        courses.add(course);
    }
}