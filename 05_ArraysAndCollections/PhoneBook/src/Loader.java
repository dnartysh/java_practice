import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.Name;
import org.w3c.dom.ls.LSOutput;

public class Loader {

    private final static int LENGTH_PHONE_NUMBER = 11;

    static TreeMap<String, String> phoneBook = new TreeMap<>();

    public static void main(String[] args) {
        System.out.println(
                "Введите имя или номер абонента. Например: Антон или 79535673785. Для вывода списка используйте LIST");

        while (true) {
            NameOrPhone nameOrPhone = readFromConsole();

            if (nameOrPhone != null) {
                if ("LIST".equals(nameOrPhone.firstname)) {
                    printPhoneBook();
                } else {
                    printExistingOrAddNewItem(nameOrPhone);
                }
            } else {
                System.out.println("Неправильно введены данные!");
            }
        }
    }

    private static class NameOrPhone {

        final String phoneNumber;
        final String firstname;

        private NameOrPhone(String phoneNumber, String firstname) {
            this.phoneNumber = phoneNumber;
            this.firstname = firstname;
        }

        private static NameOrPhone fromName(String name) {
            return new NameOrPhone(null, name);
        }

        private static NameOrPhone fromPhone(String phone) {
            return new NameOrPhone(phone, null);
        }
    }

    private static NameOrPhone readFromConsole() {
        Scanner scanner = new Scanner(System.in);
        String inputValue = scanner.nextLine();

        if (inputValue.matches("^([0-9]+)$")) {
            if (isCorrectInputNumber(inputValue)) {
                return NameOrPhone.fromPhone(inputValue);
            } else {
                return null;
            }
        } else if (inputValue.matches("^([а-яА-Яa-zA-Z]+)$")) {
            return NameOrPhone.fromName(inputValue);
        } else {
            return null;
        }
    }

    private static void printPhoneBook() {
        if (phoneBook.size() != 0) {
            for (Map.Entry<String, String> record : phoneBook.entrySet()) {
                System.out.println("Имя: " + record.getValue() + " -> номер: " + record.getKey());
            }
        } else {
            System.out.println("Список абонентов пуст!");
        }
    }

    private static void printExistingOrAddNewItem(NameOrPhone nameOrPhone) {
        if (nameOrPhone.firstname != null && phoneBook.containsValue(nameOrPhone.firstname)) {
            System.out.println("Введенное имя -> " + nameOrPhone.firstname + ". Найден телефон -> "
                    + getFirstnameFromPhoneBook(
                    nameOrPhone.firstname));
        } else if (nameOrPhone.phoneNumber != null && phoneBook
                .containsKey(nameOrPhone.phoneNumber)) {
            System.out
                    .println("Введенный телефон -> " + nameOrPhone.phoneNumber + ". Найдено имя -> "
                            + getTelephoneFromPhoneBook(nameOrPhone.phoneNumber));
        } else {
            addItemInPhoneBook(
                    isPhone(nameOrPhone) ? nameOrPhone.phoneNumber : nameOrPhone.firstname,
                    isPhone(nameOrPhone));
        }
    }

    private static boolean isPhone(NameOrPhone nameOrPhone) {
        return nameOrPhone.phoneNumber != null;
    }

    private static void addItemInPhoneBook(String value, boolean isPhone) {
        if (isPhone) {
            System.out.println("Для введенного телефона не найдено имя. Введите имя:");
        } else {
            System.out.println("Для введенного имени не найден телефон. Введите телефон:");
        }

        NameOrPhone nameOrPhone = readFromConsole();

        if (nameOrPhone != null) {
            if (nameOrPhone.phoneNumber != null) {
                phoneBook.put(nameOrPhone.phoneNumber, value);
            } else {
                phoneBook.put(value, nameOrPhone.firstname);
            }
            System.out.println("Запись успешно добавлена!");
        } else {
            System.out.println("Неправильно введены данные!");
        }
    }

    private static String getFirstnameFromPhoneBook(String firstname) {
        for (Map.Entry<String, String> map : phoneBook.entrySet()) {
            if (map.getValue().equals(firstname)) {
                return map.getKey();
            }
        }

        return null;
    }

    private static String getTelephoneFromPhoneBook(String phone) {
        return phoneBook.get(phone);
    }

    private static boolean isCorrectInputNumber(String number) {
        return number.length() == LENGTH_PHONE_NUMBER;
    }
}
