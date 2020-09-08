import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

public class Loader {

	private final static int MAX_REGIONS = 250;
	private final static String[] lettersPattern = {"С","М","Т","B","A","P","O","H","E","У"};

	private static ArrayList<String> arrayList = new ArrayList<>();
	private static HashSet<String> hashSet= new HashSet<>();
	private static TreeSet<String> treeSet= new TreeSet<>();

	public static void main(String[] args) {
		System.out.println("В коллекции " + setArraysAndGetSize() + " автомобильных номеров!");
		System.out.println("Примеры ввода: С888МТ198, М444ТТ150, A111AA05");

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("Пожалуйста, введите номер автомобиля: ");
			String input = scanner.nextLine();
			printFor(input);
			printBinarySearch(input);
			printTreeSet(input);
			printHashSet(input);
		}
	}

	private static void printFor(String input) {
		String text = "Поиск перебором: Номер - " + input + " не найден! Время поиска";
		long start = System.nanoTime();

		for (String value : arrayList) {
			if (input.equals(value)) {
				long delay = System.nanoTime() - start;
				text = "Поиск перебором: Номер - " + input + " найден! Время поиска - " + delay + " нс";
				break;
			}
		}

		System.out.println(text);
	}

	private static void printBinarySearch(String input) {
		String text = "Бинарный поиск: Номер - " + input + " не найден! Время поиска";
		long start = System.nanoTime();

		if (Collections.binarySearch(arrayList, input) >= 0) {
			long delay = System.nanoTime() - start;
			text = "Бинарный поиск: Номер - " + input + " найден! Время поиска - " + delay + " нс";
		}

		System.out.println(text);
	}

	private static void printHashSet(String input) {
		String text = "Поиск в HashSet: Номер - " + input + " не найден!";
		long start = System.nanoTime();

		if (hashSet.contains(input)) {
			long delay = System.nanoTime() - start;
			text = "Поиск в HashSet: Номер - " + input + " найден! Время поиска - " + delay + " нс";
		}

		System.out.println(text);
	}

	private static void printTreeSet(String input) {
		String text = "Поиск в TreeSet: Номер - " + input + " не найден!";
		long start = System.nanoTime();

		if (treeSet.contains(input)) {
			long delay = System.nanoTime() - start;
			text = "Поиск в TreeSet: Номер - " + input + " найден! Время поиска - " + delay + " нс";
		}

		System.out.println(text);
	}

	private static int setArraysAndGetSize() {
		for (int iReg = 1; iReg <= MAX_REGIONS; iReg++) {
			String reg = getNormalizedNumberReg(String.valueOf(iReg));

			for (int iNum = 111; iNum <= 999; iNum += 111) {
				for (int iLet = 0; iLet < lettersPattern.length; iLet++) {
					for (int iLet2 = 0; iLet2 < lettersPattern.length; iLet2++) {
						for (int iLet3 = 0; iLet3 < lettersPattern.length; iLet3++) {
							arrayList.add(lettersPattern[iLet] + iNum + lettersPattern[iLet2] + lettersPattern[iLet3] + reg);
						}
					}
				}
			}
		}

		Collections.sort(arrayList);
		treeSet.addAll(arrayList);
		hashSet.addAll(arrayList);

		return arrayList.size();
	}

	private static String getNormalizedNumberReg(String numberReg) {
		if (numberReg.length() == 1) {
			numberReg = "0" + numberReg;
		}

		return numberReg;
	}
}
