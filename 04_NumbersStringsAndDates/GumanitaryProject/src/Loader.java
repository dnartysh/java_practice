import java.util.Scanner;

public class Loader {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter count boxes: ");
        generateParcel(scanner.nextInt());
    }

    public static void generateParcel(int count) {
        Parcel parcel = new Parcel(count);
        parcel.printListParcel();
    }
}
