import java.util.ArrayList;
import java.util.Scanner;

public class Loader {

	private static int numberItem = -1;
	private static String textItem = "";

	public static void main(String[] args) {
		ArrayList<String> taskList = new ArrayList<>();

		Scanner scanner = new Scanner(System.in);
		System.out.println("Примеры запросов: LIST, ADD Дело1, ADD 1 Дело2, EDIT 4 Дело ред., DELETE 1");

		while (true) {
			clearVariable();

			System.out.print("Пожалуйста, введите запрос: ");
			String enterValue = scanner.nextLine();

			if (checkNameMethod(enterValue).equals("LIST")) {
				takeItemList(taskList);
			} else if (checkNameMethod(enterValue).equals("ADD")) {
				setParseText(enterValue);
				addItemList(taskList, enterValue);
			} else if (checkNameMethod(enterValue).equals("EDIT")) {
				setParseText(enterValue);
				editItemList(taskList);
			} else if (checkNameMethod(enterValue).equals("DELETE")) {
				setParseText(enterValue);
				deleteItemList(taskList);
			} else {
				System.out.println("Неправильно введен запрос. Введите запрос правильно.");
			}
		}
	}

	private static String checkNameMethod(String enterValue) {
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

	private static void addItemList(ArrayList<String> taskList, String enterValue) {
		if (numberItem >= 0) {
			if (isCorrectInputNumber(taskList)) {
				taskList.add(numberItem, textItem);
				System.out.println("Запись успешно добавлена!");
			} else {
				System.out.println("Введен некорректный номер записи!");
			}
		} else {
			taskList.add(textItem);
			System.out.println("Запись успешно добавлена!");
		}
	}

	private static void editItemList(ArrayList<String> taskList) {
		if (isCorrectInputNumber(taskList)) {
			taskList.set(numberItem, textItem);
			System.out.println("Запись успешно изменена!");
		} else {
			System.out.println("Введен некорректный номер записи!");
		}
	}

	private static void deleteItemList(ArrayList<String> taskList) {
		if (isCorrectInputNumber(taskList)) {
			taskList.remove(numberItem);
			System.out.println("Запись успешно удалена!");
		}
	}

	private static void setParseText(String parseText) {
		String[] arrayParseText = parseText.split("\\s+");

		if (isNumeric(arrayParseText[1])) {
			numberItem = Integer.parseInt(arrayParseText[1]);

			for (int i = 2; i < arrayParseText.length; i++) {
				textItem += " " + arrayParseText[i];
			}
		} else {
			for (int i = 1; i < arrayParseText.length; i++) {
				textItem += " " + arrayParseText[i];
			}
		}
		textItem = textItem.trim();
	}

	private static boolean isCorrectInputNumber(ArrayList<String> taskList) {
		return taskList.size() >= numberItem;
	}

	private static boolean isNumeric(String numericText) {
		for (int i = 0; i < numericText.length(); i++) {
			if (!(numericText.charAt(i) <= 57)) {
				return false;
			}
		}
		return true;
	}

	private static void clearVariable() {
		numberItem = -1;
		textItem = "";
	}
}
