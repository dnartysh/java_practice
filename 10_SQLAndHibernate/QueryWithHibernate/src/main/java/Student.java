import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "Students")
public class Student {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Setter
    private String name;
    @Setter
    private int age;

    @Setter
    @Column(name = "registration_date")
    private Date registrationDate;

    @OneToMany(mappedBy = "id.student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subscription> subscriptionsList = new ArrayList<>();

    public void addSubscription(Subscription sub) {
        subscriptionsList.add(sub);
    }
}
