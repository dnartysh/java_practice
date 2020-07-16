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
    }

    public static Integer sumDigits(Integer number)
    {
        int sumNumbers = 0;

        while (number != 0)
        {
            sumNumbers += number % 10;
            number /= 10;
        }

        return sumNumbers;
    }
}
