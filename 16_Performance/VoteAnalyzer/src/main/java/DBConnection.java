import java.sql.*;

public class DBConnection {

    private static Connection connection;

    private static final int MAX_STRING_LENGTH = 2147483647;

    private static String dbName = "LEARN";
    private static String dbUser = "test";
    private static String dbPass = "1234567890";

    private static StringBuilder insertQuery = new StringBuilder();

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + dbName +
                                "?user=" + dbUser + "&password=" + dbPass);
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                connection.createStatement().execute("CREATE TABLE voter_count(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT NOT NULL, " +
                        "birthDate DATE NOT NULL, " +
                        "`count` INT NOT NULL, " +
                        "PRIMARY KEY(id), " +
                        "UNIQUE KEY name_date(name(50), birthDate))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

    public static void executeMultiInsert() throws SQLException {
        String query = "INSERT INTO voter_count(name, birthDate, `count`) "
                + "VALUES " + insertQuery.toString() +
                "ON DUPLICATE KEY UPDATE `count`=`count` + 1";
        DBConnection.getConnection().createStatement().execute(query);
    }

    public static void countVoter(String name, long birthDay) throws SQLException {
        if (MAX_STRING_LENGTH / insertQuery.capacity() < 100) {
            executeMultiInsert();
            insertQuery = new StringBuilder();
        }

        insertQuery.append(insertQuery.length() == 0 ? "" : ", ")
                .append("('" + name + "', '" + getFormatDate(birthDay) + "', 1)");
    }

    public static void printVoterCounts() throws SQLException {
        String sql = "SELECT name, birthDate, `count` FROM voter_count WHERE `count` > 1";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while (rs.next()) {
            System.out.println("\t" + rs.getString("name") + " (" +
                    rs.getString("birthDate") + ") - " + rs.getInt("count"));
        }
    }

    private static String getFormatDate(long value) {
        String stringDate = String.valueOf(value);

        return stringDate.substring(0, 4) + "-" + stringDate.substring(4, 6) + "-" +
                stringDate.substring(6, 8);
    }
}
