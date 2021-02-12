import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class LinkedPurchaseList {
    @EmbeddedId
    private LinkedPurchaseListKey id;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "course_name")
    private String courseName;

    private Integer price;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    public LinkedPurchaseList() {}

    public LinkedPurchaseList(LinkedPurchaseListKey id) {
        this.id = id;
    }
}
