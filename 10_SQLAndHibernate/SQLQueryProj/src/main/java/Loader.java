import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Loader {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/skillbox";
        String username = "testtest";
        String password = "1234567890";

        try (Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("select course_name, "
                        + "timestampdiff(month, min(subscription_date), max(subscription_date)) / count(*) "
                        + "as avg_sell from PurchaseList group by course_name;"); ) {
            while(result.next()) {
                System.out.println(result.getString("course_name") +
                        " - " + result.getDouble("avg_sell"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
