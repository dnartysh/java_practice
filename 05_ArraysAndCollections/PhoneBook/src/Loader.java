import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loader {

	private final static int LENGTH_PHONE_NUMBER = 11;

	static TreeMap<String, String> phoneBook = new TreeMap<>();

	public static void main(String[] args) {
		System.out.println("Введите имя или номер абонента. Например: Антон или 79535673785. Для вывода списка используйте LIST");

		while (true) {
			String input = readFromConsole();

			if (input.equals("LIST")) {
				printPhoneBook();
			} else {
				NameOrPhone nameOrPhone = new NameOrPhone();
				nameOrPhone = setItem(input, nameOrPhone);

				if (!"".equals(nameOrPhone.firstname) || !"".equals(nameOrPhone.phoneNumber)) {
					printExistingOrAddNewItem(nameOrPhone);
				} else {
					System.out.println("Запрос введен некорректно! Введите запрос заново!");
				}
			}
		}
	}

	private static String readFromConsole() {
		Scanner scanner = new Scanner(System.in);

		return scanner.nextLine();
	}

	private static class NameOrPhone {
		String phoneNumber = "";
		String firstname = "";
	}

	private static void printPhoneBook() {
		if (phoneBook.size() != 0) {
			for (Map.Entry<String, String> record: phoneBook.entrySet()) {
				System.out.println("Имя: " + record.getValue() + " -> номер: " + record.getKey());
			}
		} else {
			System.out.println("Список абонентов пуст!");
		}
	}

	private static NameOrPhone setItem(String inputValue, NameOrPhone nameOrPhone) {
		Matcher inputM = Pattern.compile("^(?<firstname>[а-яА-Яa-zA-Z]+)$|^(?<phone>[0-9]+)$")
				.matcher(inputValue);

		if (inputM.find()) {
			if (inputM.group("firstname") != null) {
				nameOrPhone.firstname = inputM.group("firstname");
			} else if (inputM.group("phone") != null) {
				if (isCorrectInputNumber(inputM.group("phone"))) {
					nameOrPhone.phoneNumber = inputM.group("phone");
				}
			}
		}

		return nameOrPhone;
	}

	private static void printExistingOrAddNewItem(NameOrPhone nameOrPhone) {
		if (phoneBook.containsValue(nameOrPhone.firstname)) {
			System.out.println("Введенное имя -> " + nameOrPhone.firstname + ". Найден телефон -> " + getFirstnameFromPhoneBook(
					nameOrPhone.firstname));
		} else if (phoneBook.containsKey(nameOrPhone.phoneNumber)) {
			System.out.println("Введенный телефон -> " + nameOrPhone.phoneNumber + ". Найдено имя -> "
					+ getTelephoneFromPhoneBook(nameOrPhone.phoneNumber));
		} else {
			addItemInPhoneBook(nameOrPhone);
		}
	}

	private static void addItemInPhoneBook(NameOrPhone nameOrPhone) {
		if ("".equals(nameOrPhone.firstname)) {
			System.out.println("Введенный телефон не обнаружен в телефонной книге. Введите имя:");
		} else if ("".equals(nameOrPhone.phoneNumber)) {
			System.out.println("Введенное имя не найдено в телефонной книге. Введите номер телефона:");
		}

		while ("".equals(nameOrPhone.firstname) || "".equals(nameOrPhone.phoneNumber)) {
			String input = readFromConsole();
			nameOrPhone = setItem(input, nameOrPhone);

			if (!"".equals(nameOrPhone.firstname) && !"".equals(nameOrPhone.phoneNumber)) {
				phoneBook.put(nameOrPhone.phoneNumber, nameOrPhone.firstname);
				System.out.println("Запись успешно добавлена!");
			} else if ("".equals(nameOrPhone.firstname)) {
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
		return phoneBook.get(inputTelephone);
	}

	private static boolean isCorrectInputNumber(String inputNumber) {
		return inputNumber.length() == LENGTH_PHONE_NUMBER;
	}
}
