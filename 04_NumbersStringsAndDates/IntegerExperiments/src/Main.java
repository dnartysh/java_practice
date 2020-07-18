import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Container container = new Container();
        container.count += 7843;
        givenSumNumbers();
    }

    public static void givenSumNumbers()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number: ");
        System.out.println("Sum numbers: " + sumDigits(scanner.nextInt()));
        System.out.print("Enter number for variant 2: ");
        System.out.println("Sum numbers: " + sumDigitsVersion2(scanner.nextInt()));
    }

    public static Integer sumDigits(Integer number)
    {
        int sumNumbers = 0;

        String someText = Integer.toString(number);

        for (int i = 0; i <= someText.length() - 1; i++) {
            sumNumbers += Integer.parseInt(String.valueOf(someText.charAt(i)));
        }

        return sumNumbers;
    }

    public static Integer sumDigitsVersion2(Integer number)
    {
        int sumNumbers = 0;

        String someText = Integer.toString(number);

        for (int i = 0; i <= someText.length() - 1; i++) {
            sumNumbers += Character.getNumericValue(someText.charAt(i));
        }

        return sumNumbers;
    }
}
