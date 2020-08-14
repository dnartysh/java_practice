import java.util.ArrayList;
import java.util.Scanner;

public class Loader {

	private static ArrayList<String> taskList = new ArrayList<>();
	private static String[] arrayEnterValue = new String[0];

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Примеры запросов: LIST, ADD Дело1, ADD 1 Дело2, EDIT 4 Дело ред., DELETE 1");

		while (true) {
			System.out.print("Пожалуйста, введите запрос: ");
			setArrayEnterValue(scanner.nextLine());

			if (isCorrectInput()) {
				Item item = setItem();

				switch (getCommandName()) {
					case "LIST":
						printItemList();
						break;
					case "ADD":
						addItemList(item);
						break;
					case "EDIT":
						editItemList(item);
						break;
					case "DELETE":
						deleteItemList(item);
						break;
					default:
						System.out.println("Неправильно введен запрос. Введите запрос правильно.");
						break;
				}
			} else {
				System.out.println("Неправильно введен запрос. Введите запрос правильно.");
			}
		}
	}

	private static void setArrayEnterValue(String inputText) {
		arrayEnterValue = inputText.split("\\s+");
	}

	private static boolean isCorrectInput() {
		String item = arrayEnterValue[0];

		if (item.equals("ADD") || item.equals("LIST") || item.equals("EDIT") || item.equals("DELETE")) {
			if (!item.equals("LIST") && arrayEnterValue.length <= 1) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	private static String getCommandName() {
		return arrayEnterValue[0];
	}

	private static void printItemList() {
		if (taskList.size() == 0) {
			System.out.println("Список пуст!");
		} else {
			for (int i = 0; i < taskList.size(); i++) {
				System.out.println(i + " - " + taskList.get(i));
			}
			System.out.println("Всего записей - " + taskList.size());
		}
	}

	private static void addItemList(Item item) {
		if (item.numberItem >= 0 && !"".equals(item.textItem) && isCorrectInputNumber(item)) {
			taskList.add(item.numberItem, item.textItem);
			System.out.println("Запись успешно добавлена!");
		} else if ("".equals(item.textItem)){
			System.out.println("Не задан текст!");
		} else if (!isCorrectInputNumber(item)) {
			System.out.println("Введенный индекс превышает размер списка! Последний индекс записи - " + getNumberLastRecord());
		} else {
			taskList.add(item.textItem);
			System.out.println("Запись успешно добавлена!");
		}
	}

	private static void editItemList(Item item) {
		if (item.numberItem >= 0 && !"".equals(item.textItem) && isCorrectInputNumber(item)) {
			taskList.set(item.numberItem, item.textItem);
			System.out.println("Запись успешно изменена!");
		} else if (item.numberItem == -1) {
			System.out.println("Не задан индекс!");
		} else if (!isCorrectInputNumber(item)){
			System.out.println("Введенный индекс превышает размер списка! Последний индекс записи - " + getNumberLastRecord());
		} else {
			System.out.println("Не задан текст!");
		}
	}

	private static void deleteItemList(Item item) {
		if (isNumericWithMatches(String.valueOf(item.numberItem))) {
			if (isCorrectInputNumber(item)) {
				taskList.remove(item.numberItem);
				System.out.println("Запись успешно удалена!");
			} else {
				System.out.println("Введенный индекс превышает размер списка! Последний индекс записи - " + getNumberLastRecord());
			}
		} else {
			System.out.println("Введен некорректный номер записи! Последний индекс записи - " + getNumberLastRecord());
		}
	}

	private static Item setItem() {
		if (arrayEnterValue.length > 1) {
			Item item = new Item();

			if (isNumericWithMatches(arrayEnterValue[1])) {
				item.numberItem = Integer.parseInt(arrayEnterValue[1]);

				for (int i = 2; i < arrayEnterValue.length; i++) {
					item.textItem += " " + arrayEnterValue[i];
				}
			} else {
				for (int i = 1; i < arrayEnterValue.length; i++) {
					item.textItem += " " + arrayEnterValue[i];
				}
			}
			item.textItem = item.textItem.trim();

			return item;
		} else {
			return null;
		}
	}

	private static boolean isCorrectInputNumber(Item item) {
		return taskList.size() >= item.numberItem;
	}

	private static boolean isNumericWithMatches(String numericText) {
		return numericText.matches("\\d+");
	}

	private static int getNumberLastRecord() {
		return taskList.size() - 1;
	}

	private static boolean isNumeric(String numericText) {
		for (int i = 0; i < numericText.length(); i++) {
			if (numericText.charAt(i) >= '0' && numericText.charAt(i) <= '9') {
				return false;
			}
		}
		return true;
	}

	static class Item {
		int numberItem = -1;
		String textItem = "";
	}
}
