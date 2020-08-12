import java.util.Scanner;
import java.util.TreeSet;

public class Loader {

    public static void main(String[] args) {
        TreeSet<String> listEmails = new TreeSet<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Пример запроса: ADD user@domen.com, ADD user@host.domen.net, LIST");

        while (true) {
            System.out.println("Введите запрос: ");
            String input = scanner.nextLine();

            if (getInputMethod(input).equals("ADD")) {
                Email email = setParsedText(input);
                if (email != null) {
                    addListEmail(listEmails, email);
                } else {
                    System.out.println("Некорректно введен email. Введите email правильно.");
                }
            } else if (getInputMethod(input).equals("LIST")) {
                    printListEmail(listEmails);
            } else {
                System.out.println("Некорректно введен запрос. Введите запрос корректно.");
            }
        }
    }

    private static void printListEmail(TreeSet<String> listEmails) {
        if (getSizeList(listEmails) != 0) {
            for (String Item : listEmails) {
                System.out.println(Item);
            }
        } else {
            System.out.println("Список адресов пуст!");
        }

    }

    private static void addListEmail(TreeSet<String> listEmails, Email email) {
        listEmails.add(email.addressEmail);
        System.out.println("Адрес успешно добавлен!");
    }

    private static Email setParsedText(String input) {
        Email email = new Email();

        if (isCorrectEmail(input)) {
            email.addressEmail = getEmailAddress(input);
            return email;
        } else {
            return null;
        }
    }

    private static String getInputMethod(String input) {
        String[] arrayInput = input.split("\\s+");

        return arrayInput[0];
    }

    private static boolean isCorrectEmail(String addressEmail) {
        return addressEmail.matches("(.*)@(.*)[.](.*)");
    }

    private static String getEmailAddress(String input) {
        String[] arrayInput = input.split("\\s+");

        return arrayInput[1];
    }

    static class Email {
        String addressEmail = "";
    }

    private static int getSizeList(TreeSet<String> listEmail) {
        return listEmail.size();
    }
}
