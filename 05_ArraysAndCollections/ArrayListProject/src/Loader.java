import java.util.ArrayList;
import java.util.Scanner;

public class Loader {

	public final static int MAX_VALUE_NUMBER_IN_CHAR = '9';

	public static void main(String[] args) {
		ArrayList<String> taskList = new ArrayList<>();

		Scanner scanner = new Scanner(System.in);
		System.out.println("Примеры запросов: LIST, ADD Дело1, ADD 1 Дело2, EDIT 4 Дело ред., DELETE 1");

		while (true) {
			System.out.print("Пожалуйста, введите запрос: ");
			String enterValue = scanner.nextLine();

			if (getCommandName(enterValue).equals("LIST")) {
				takeItemList(taskList);
			} else if (getCommandName(enterValue).equals("ADD")) {
				Item item = setParsedText(enterValue);
				addItemList(taskList, item);
			} else if (getCommandName(enterValue).equals("EDIT")) {
				Item item = setParsedText(enterValue);
				editItemList(taskList, item);
			} else if (getCommandName(enterValue).equals("DELETE")) {
				Item item = setParsedText(enterValue);
				deleteItemList(taskList, item);
			} else {
				System.out.println("Неправильно введен запрос. Введите запрос правильно.");
			}
		}
	}

	private static String getCommandName(String enterValue) {
		String[] arrayEnterValue = enterValue.split("\\s+");

		return arrayEnterValue[0];
	}

	private static void takeItemList(ArrayList<String> taskList) {
		if (taskList.size() == 0) {
			System.out.println("Список пуст!");
		} else {
			for (int i = 0; i < taskList.size(); i++) {
				System.out.println(i + " - " + taskList.get(i));
			}
		}
	}

	private static void addItemList(ArrayList<String> taskList, Item item) {
		if (item.numberItem >= 0) {
			if (isCorrectInputNumber(taskList, item)) {
				taskList.add(item.numberItem, item.textItem);
				System.out.println("Запись успешно добавлена!");
			} else {
				System.out.println("Введен некорректный номер записи!");
			}
		} else {
			taskList.add(item.textItem);
			System.out.println("Запись успешно добавлена!");
		}
	}

	private static void editItemList(ArrayList<String> taskList, Item item) {
		if (isCorrectInputNumber(taskList, item)) {
			taskList.set(item.numberItem, item.textItem);
			System.out.println("Запись успешно изменена!");
		} else {
			System.out.println("Введен некорректный номер записи!");
		}
	}

	private static void deleteItemList(ArrayList<String> taskList, Item item) {
		if (isCorrectInputNumber(taskList, item)) {
			taskList.remove(item.numberItem);
			System.out.println("Запись успешно удалена!");
		} else {
			System.out.println("Введен некорректный номер записи!");
		}
	}

	private static Item setParsedText(String parseText) {
		Item item = new Item();

		String[] arrayParseText = parseText.split("\\s+");

		if (isNumericWithMatches(arrayParseText[1])) {
			item.numberItem = Integer.parseInt(arrayParseText[1]);

			for (int i = 2; i < arrayParseText.length; i++) {
				item.textItem += " " + arrayParseText[i];
			}
		} else {
			for (int i = 1; i < arrayParseText.length; i++) {
				item.textItem += " " + arrayParseText[i];
			}
		}
		item.textItem = item.textItem.trim();

		return item;
	}

	private static boolean isCorrectInputNumber(ArrayList<String> taskList, Item item) {
		return taskList.size() >= item.numberItem;
	}

	private static boolean isNumericWithMatches(String numericText) {
		return numericText.matches("\\d+");
	}

	private static boolean isNumeric(String numericText) {
		for (int i = 0; i < numericText.length(); i++) {
			if (!(numericText.charAt(i) <= MAX_VALUE_NUMBER_IN_CHAR)) {
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
