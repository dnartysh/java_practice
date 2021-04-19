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
            System.out.println(Constants.COMMAND_REQUESTED);
            String[] query = scn.nextLine().split("\\s++");

            switch (query[0].toUpperCase()) {
                case Constants.ADD_STORE:
                    management.addShop(query[1]);
                    break;
                case Constants.ADD_PRODUCT:
                    if (isValidProductValue(query)) {
                        management.addProduct(query[1], Double.parseDouble(query[2]));
                    } else {
                        System.out.println(Constants.WRONG_COMMAND_FORMAT);
                    }
                    break;
                case Constants.ADD_PRODUCT_TO_STORE:
                    if (isValidProductValue(query)) {
                        management.exposeProduct(query[1], query[2]);
                    } else {
                        System.out.println(Constants.WRONG_COMMAND_FORMAT);
                    }
                    break;
                case Constants.STATISTICS:
                    management.productsStatistics();
                    break;
                case Constants.CLEAR:
                    management.dropAll();
                    System.out.println(Constants.SUCCESS);
                    break;
                default:
                    System.out.println(Constants.WRONG_COMMAND);
                    break;
            }
        }
    }

    private static boolean isValidProductValue(String[] value) {
        return value.length > 2;
    }
}
