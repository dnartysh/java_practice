import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loader {

	private static TreeSet<String> listEmails = new TreeSet<>();

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Пример запроса: ADD user@domen.com, ADD user@host.domen.net, LIST");

		while (true) {
			Matcher input = Pattern.compile("^(?<cmd>ADD|LIST)\\s*(?<addressEmail>.+@.+\\..+)?$").matcher(scanner.nextLine());

			if (input.find()) {
				switch (input.group("cmd")) {
					case "ADD":
						if (input.group("addressEmail") != null) {
							listEmails.add(input.group("addressEmail"));
							System.out.println("E-mail успешно добавлен!");
						} else {
							System.out.println("Запрос введен неправильно. Введите правильный запрос.");
						}
						break;
					case "LIST":
						if (input.group("addressEmail") == null) {
							printListEmails();
						} else {
							System.out.println("Запрос введен неправильно. Введите правильный запрос.");
						}
						break;
				}
			} else {
				System.out.println("Запрос введен неправильно. Введите правильный запрос.");
			}
		}
	}

	private static void printListEmails() {
		if (listEmails.size() != 0) {
			for (String Item : listEmails) {
				System.out.println(Item);
			}
		} else {
			System.out.println("Список адресов пуст!");
		}
	}
}
