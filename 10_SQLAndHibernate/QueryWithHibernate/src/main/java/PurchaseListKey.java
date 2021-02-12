import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseListKey implements Serializable {
    @Column(name = "student_name", insertable = false, updatable = false)
    private String studentName;
    @Column(name = "course_name", insertable = false, updatable = false)
    private String courseName;

    public PurchaseListKey() {}

    public PurchaseListKey(String studentName, String courseName) {
        this.studentName = studentName;
        this.courseName = courseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PurchaseListKey that = (PurchaseListKey) o;
        return Objects.equals(studentName, that.studentName) && Objects
                .equals(courseName, that.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentName, courseName);
    }
}
