import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loader {

	private static TreeSet<String> listEmails = new TreeSet<>();

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Пример запроса: ADD user@domen.com, ADD user@host.domen.net, LIST");

		// ^(.{3,4})(.+@.+)$
		while (true) {
			Matcher input = Pattern.compile("^(.{3,4})(.+@.+)?$").matcher(scanner.nextLine());

			if (input.find()) {
                switch (input.group(1).trim()) {
                    case "LIST":
                        printListEmails();
                        break;
                    case "ADD":
                        if (input.group(2) != null) {
                            addListEmail(input.group(2));
                        } else {
                            System.out.println("Неправильно введен e-mail. Введите правильный e-mail");
                        }
                        break;
                    default:
                        System.out.println("Введенная команда неопознана. Используйте ADD или LIST.");
                        break;
                }
            } else {
                System.out.println("Неправильно введен e-mail. Введите правильный e-mail");
            }
		}
	}

	private static void printListEmails() {
		if (getSizeList(listEmails) != 0) {
			for (String Item : listEmails) {
				System.out.println(Item);
			}
		} else {
			System.out.println("Список адресов пуст!");
		}
	}

	private static void addListEmail(String addressEmail) {
		listEmails.add(addressEmail);
		System.out.println("Адрес успешно добавлен!");
	}

	private static int getSizeList(TreeSet<String> listEmail) {
		return listEmail.size();
	}
}
