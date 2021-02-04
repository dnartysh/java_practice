import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Purchase {
    private String studentName;
    private String courseName;
    private int price;
    private Date subscriptionDate;
}
