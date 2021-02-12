import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PurchaseList {
    @EmbeddedId
    private PurchaseListKey id;

    private Integer price;

    @Column(name = "subscription_date")
    private Date subscriptionDate;
}
