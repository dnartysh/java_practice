import java.util.Scanner;

public class Loader {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

//        System.out.println("Пожалуйста, введите ваши Фамилию, Имя и Отчество. Для разделения используйте пробел: ");
//        System.out.println(searchFio(scanner.nextLine()));

//        System.out.println("Теперь введите номер кредитной карты: ");
//        System.out.println(searchAndReplaceDiamonds(scanner.nextLine(), "****"));

//        printSalary("Вася заработал 5000 рублей, Петя - 7563 рубля, а Маша - 30000 рублей");
//        printChar();

        // Lesson 4.5
//        printSalaryWithRegular("Вася заработал 5000 рублей, Петя - 7563 рубля, а Маша - 30000 рублей");

//        printWordsFromText("The current severe economic downturn in America affects people on every level. "
//            + "Rising fuel costs have impacted everything from the price of groceries to the cost of getting to work everyday. "
//            + "Due to this economy issue, there are the changes in the way people spend their leisure time. "
//            + "There are media reports that tell about the trend away from vacations and toward staycations, where people stay at home instead of travelling. "
//            + "At the same time, there’s a significant trend toward finding recreational activities. They are called crafts for kids, quilting, and gardening.");

//        System.out.println("Пожалуйста, введите ваши Фамилию, Имя и Отчество. Для разделения используйте пробел: ");
//        printFioWithRegular(scanner.nextLine());

        System.out.println(refactoringTelephoneNumber("+7 909 123-45-67"));
        System.out.println(refactoringTelephoneNumber("+7 (909) 1234567"));
        System.out.println(refactoringTelephoneNumber("8-905-1234567"));
        System.out.println(refactoringTelephoneNumber("9-453-1234567"));
        System.out.println(refactoringTelephoneNumber("8-905-123"));
        System.out.println(refactoringTelephoneNumber("905-1234567"));
        System.out.println(refactoringTelephoneNumber("8-905-12345672342"));
//        System.out.println("Введите номер кредитной карты: ");
//        System.out.println(searchAndReplaceDiamondsWithRegular(scanner.nextLine(), "****"));
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

    private static void printSalaryWithRegular(String text) {
        int sumSalary = 0;

        String stringSalaryAll = text.replaceAll("[^0-9 ]", "").trim();
        String[] salaryAllArray = stringSalaryAll.split("\\s+");

        for (int i = 0; i < salaryAllArray.length; i++) {
            sumSalary += Integer.parseInt(salaryAllArray[i]);
        }

        System.out.println("Sum salary - " + sumSalary + " rub.");
    }

    private static void printWordsFromText(String text) {
        String[] explicitText = text.split("\\s|\\.\\s|,\\s|\\.");

        for (int i = 0; i < explicitText.length; i++) {
            System.out.println(explicitText[i]);
        }
    }

    private static void printFioWithRegular(String text) {
        String[] textArray = text.split("\\s");
        if (isNotHaveMiddleNameWithRegular(textArray.length)) {
            System.out.println("Фамилия: " + textArray[0] + "\nИмя: " + textArray[1]);
        } else {
            System.out.println("Фамилия: " + textArray[0] + "\nИмя: " + textArray[1] + "\nОтчество: " + textArray[2]);
        }
    }

    private static boolean isNotHaveMiddleNameWithRegular(int length) {
        return length <= 2;
    }

    private static String refactoringTelephoneNumber(String number) {
        String refactoredNumber = number.replaceAll("[+()\\s\\-]", "").trim();

        return checkAndEditTelephoneNumber(refactoredNumber);
    }

    private static String checkAndEditTelephoneNumber(String number) {
        if (number.length() == 11) {
            if (number.indexOf("7") == 0) {
                return number;
            } else if (number.indexOf("8") == 0) {
                return number.replaceAll(String.valueOf(number.indexOf("8")), "7");
            } else {
                return "Неправильно введен номер телефона!";
            }
        } else if (number.length() == 10){
            return "7" + number;
        } else {
            return "Неправильно введен номер телефона!";
        }
    }

    private static String searchAndReplaceDiamondsWithRegular(String text, String placeholder) {
        String refactoredText = text.substring(text.indexOf("<"), text.indexOf(">") + 1).trim();

        return text.replaceAll(refactoredText, placeholder);
    }
}