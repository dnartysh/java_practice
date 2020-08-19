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
						if (isInputAddressEmail(input.group("addressEmail"))) {
							addListEmail(input.group("addressEmail"));
						} else {
							System.out.println("Запрос введен неправильно. Введите правильный запрос.");
						}
						break;
					case "LIST":
						if (!isInputAddressEmail(input.group("addressEmail"))) {
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
		if (getSizeList() != 0) {
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

	private static int getSizeList() {
		return listEmails.size();
	}

	private static boolean isInputAddressEmail(String inputText) {
		return inputText != null;
	}
}
