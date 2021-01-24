import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

public class Station {

    private String number;
    private String name;

    public Station(String number, String name) {
        this.number = number;
        this.name = name;
    }

    @JsonIgnore
    public String getNumber() {
        return number;
    }

    @JsonValue
    public String getName() {
        return name;
    }

}
