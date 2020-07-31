import java.util.Scanner;

public class Loader {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Пожалуйста, введите ваши Фамилию, Имя и Отчество. Для разделения используйте пробел: ");
        System.out.println(searchFio(scanner.nextLine()));

//        System.out.println("Теперь введите номер кредитной карты: ");
//        System.out.println(searchAndReplaceDiamonds(scanner.nextLine(), "****"));

//        printSalary("Вася заработал 5000 рублей, Петя - 7563 рубля, а Маша - 30000 рублей");
//        printChar();
    }

    private static String searchAndReplaceDiamonds(String text, String placeholder) {
        String textWithPlaceholder = "";

        if (text.indexOf("<") == 0) {
            textWithPlaceholder = placeholder + text.substring(text.indexOf(">") + 1);
        } else {
            if (text.indexOf(">") + 1 == text.length()) {
                textWithPlaceholder = text.substring(0, text.indexOf("<")) + placeholder;
            } else {
                textWithPlaceholder = text.substring(0, text.indexOf("<")) + placeholder + text.substring(text.indexOf(">") + 1);
            }
        }

        return textWithPlaceholder;
    }

    private static String searchFio(String text) {
        String firstName = "";
        String lastName = "";
        String middleName = "";

        firstName = text.substring(0, text.indexOf(" ") + 1).trim();

        if (isNotHaveMiddleName(text)) {
            lastName = text.substring(text.indexOf(' ') + 1).trim();
        } else {
            lastName = text.substring(text.indexOf(" ") + 1, text.indexOf(" ", text.indexOf(" ") + 1) + 1).trim();
            middleName = text.substring(text.indexOf(" ", text.indexOf(" ") + 1) + 1);
        }

        return "Имя: " + firstName + "\nФамилия: " + lastName + "\nОтчество: " + middleName;
    }

    private static boolean isNotHaveMiddleName(String text) {
        return text.indexOf(' ') == text.lastIndexOf(' ');
    }

    private static void printSalary(String text) {
        String sumVasya = text.substring(text.indexOf("Вася заработал") + 14, text.indexOf("руб", text.indexOf("Вася заработал") + 14)).trim();

        String sumPetya = text.substring(text.indexOf("Петя - ") + 7, text.indexOf("руб", text.indexOf("Петя - ") + 7)).trim();

        String sumMasha = text.substring(text.indexOf("Маша - ") + 7, text.lastIndexOf("руб")).trim();

        System.out.print("Сумма денег Васи, Пети и Маши - ");
        System.out.println(Integer.parseInt(sumVasya) + Integer.parseInt(sumPetya) + Integer.parseInt(sumMasha) + " руб.");
    }

    private static void printChar() {
        String someText = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i <= someText.length() - 1; i++) {
            System.out.println("Char '" + someText.charAt(i) + "' - code '" + (int)someText.charAt(i) + "'");
        }
    }

}