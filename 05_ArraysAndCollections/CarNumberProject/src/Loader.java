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
			printForBinaryHashTreeSearch(input);
		}
	}

	private static void printForBinaryHashTreeSearch(String input) {
		long start = 0;
		long delayFor = 0;
		long delayBinary = 0;
		long delayHash = 0;
		long delayTree = 0;

		start = System.nanoTime();
		for (String value : arrayList) {
			if (input.equals(value)) {
				delayFor = System.nanoTime() - start;
				break;
			}
		}

		start = System.nanoTime();
		if (Collections.binarySearch(arrayList, input) >= 0) {
			delayBinary = System.nanoTime() - start;
		}

		start = System.nanoTime();
		if (hashSet.contains(input)) {
			delayHash = System.nanoTime() - start;
		}

		start = System.nanoTime();
		if (treeSet.contains(input)) {
			delayTree = System.nanoTime() - start;
		}

		printDelays(input, delayFor, delayBinary, delayHash, delayTree);
	}

	private static void printDelays(String input, long delayFor, long delayBinary, long delayHash, long delayTree) {
		System.out.println("Время поиска номера - " + input + ":\n"
				+ "Перебором - " + delayFor + " нс\n"
				+ "Бинарным поиском - " + delayBinary + " нс\n"
				+ "Hash - " + delayHash + " нс\n"
				+ "Tree - " + delayTree + " нс\n");
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
