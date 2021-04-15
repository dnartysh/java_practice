import java.util.Scanner;

public class Main {
    private static final String URI = "localhost";
    private static final int PORT = 27017;
    private static final String DB_NAME = "shopiss";

    public static void main(String[] args) {
        Management management = new Management(URI, PORT, DB_NAME);
        management.initialize();

        Scanner scn = new Scanner(System.in);

        while (true) {
            System.out.println("Enter your query: ");
            String[] query = scn.nextLine().split("\\s++");

            switch (query[0].toUpperCase()) {
                case "ADD_SHOP":
                    management.addShop(query[1]);
                    break;
                case "ADD_PRODUCT":
                    if (isValidProductValue(query)) {
                        management.addProduct(query[1], Double.parseDouble(query[2]));
                    } else {
                        System.out.println("Invalid parameters count");
                    }
                    break;
                case "EXPOSE_PRODUCT":
                    if (isValidProductValue(query)) {
                        management.exposeProduct(query[1], query[2]);
                    } else {
                        System.out.println("Invalid parameters count");
                    }
                    break;
                case "STATISTIC_PRODUCT":
                    management.productsStatistics();
                    break;
                case "CLEAR":
                    management.dropAll();
                    System.out.println("Success");
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }
    }

    private static boolean isValidProductValue(String[] value) {
        return value.length > 2;
    }
}
