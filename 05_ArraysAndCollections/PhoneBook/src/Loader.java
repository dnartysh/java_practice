import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loader {

	private final static int LENGTH_PHONE_NUMBER = 11;

	static TreeMap<String, String> phoneBook = new TreeMap<>();

	public static void main(String[] args) {
		System.out.println("Введите имя или номер абонента. Например: Антон или 79535673785");

		while (true) {
			String input = getInputConsole();

			Item item = createItem();
			item = setItem(input, item);

			if (!"".equals(item.firstname) || !"".equals(item.phoneNumber)) {
				getItemFromPhoneBook(item);
			} else {
				System.out.println("Запрос введен некорректно! Введите запрос заново!");
			}
		}
	}

	private static String getInputConsole() {
		Scanner scanner = new Scanner(System.in);

		return scanner.nextLine();
	}

	private static Item createItem() {
		Item item = new Item();

		return item;
	}

	private static class Item {
		String phoneNumber = "";
		String firstname = "";
	}

	private static Item setItem(String inputValue, Item item) {
		Matcher inputM = Pattern.compile("^(?<firstname>[а-яА-Яa-zA-Z]+)$|^(?<phone>[0-9]+)$")
				.matcher(inputValue);

		if (inputM.find()) {
			if (inputM.group("firstname") != null) {
				item.firstname = inputM.group("firstname");
			} else if (inputM.group("phone") != null) {
				if (isCorrectInputNumber(inputM.group("phone"))) {
					item.phoneNumber = inputM.group("phone");
				}
			}
		}

		return item;
	}

	private static void getItemFromPhoneBook(Item item) {
		if (phoneBook.containsValue(item.firstname)) {
			System.out.println("Введенное имя -> " + item.firstname + ". Найден телефон -> " + getFirstnameFromPhoneBook(
					item.firstname));
		} else if (phoneBook.containsKey(item.phoneNumber)) {
			System.out.println("Введенный телефон -> " + item.phoneNumber + ". Найдено имя -> "
					+ getTelephoneFromPhoneBook(item.phoneNumber));
		} else {
			addItemInPhoneBook(item);
		}
	}

	private static void addItemInPhoneBook(Item item) {
		if ("".equals(item.firstname)) {
			System.out.println("Введенный телефон не обнаружен в телефонной книге. Введите имя:");
		} else if ("".equals(item.phoneNumber)) {
			System.out.println("Введенное имя не найдено в телефонной книге. Введите номер телефона:");
		}

		while ("".equals(item.firstname) || "".equals(item.phoneNumber)) {
			String input = getInputConsole();
			item = setItem(input, item);

			if (!"".equals(item.firstname) && !"".equals(item.phoneNumber)) {
				phoneBook.put(item.phoneNumber, item.firstname);
				System.out.println("Запись успешно добавлена!");
			} else if ("".equals(item.firstname)) {
				System.out.println("Имя введено некорректно! Введите заново!");
			} else {
				System.out.println("Телефон введен некорректно! Введите заново!");
			}
		}
	}

	private static String getFirstnameFromPhoneBook(String inputFirstname) {
		for (Map.Entry<String, String> map: phoneBook.entrySet()) {
			if (map.getValue().equals(inputFirstname)) {
				return map.getKey();
			}
		}
		return "";
	}

	private static String getTelephoneFromPhoneBook(String inputTelephone) {
		for (Map.Entry<String, String> map : phoneBook.entrySet()) {
			if (map.getKey().equals(inputTelephone)) {
				return map.getValue();
			}
		}

		return "";
	}

	private static boolean isCorrectInputNumber(String inputNumber) {
		return inputNumber.length() == LENGTH_PHONE_NUMBER;
	}
}
