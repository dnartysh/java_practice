import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "Courses")
public class Course {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Setter
    private String name;
    @Setter
    private int duration;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('DESIGN', 'PROGRAMMING', 'MARKETING', 'MANAGEMENT', 'BUSINESS'")
    private CourseType type;
    @Setter
    private String description;

    @Setter
    @ManyToOne
    private Teacher teacher;

    @Setter
    @Column(name = "students_count")
    private int studentsCount;
    private int price;

    @Setter
    @Column(name = "price_per_hour")
    private float pricePerHour;

    @OneToMany(mappedBy = "id.course", cascade = CascadeType.ALL)
    private List<Subscription> subscriptionsList;

    public void addSubscription(Subscription subscription) {
        subscriptionsList.add(subscription);
    }
}
