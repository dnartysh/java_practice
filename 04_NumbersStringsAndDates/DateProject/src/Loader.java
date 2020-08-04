import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Loader {

	public static void main(String[] args) {
		printBirthday();
	}

	private static void printBirthday() {
		int day = 22;
		int month = Calendar.AUGUST;
		int year = 1993;

		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy - EEEE");
		Calendar calendarBirthday = Calendar.getInstance();
		calendarBirthday.set(year, month, day);

		Calendar calendar = Calendar.getInstance();

		int countBirthday = 0;

		while (calendarBirthday.get(Calendar.YEAR) < calendar.get(Calendar.YEAR)) {
			System.out.println(countBirthday++ + " - " + dateFormat.format(calendarBirthday.getTime()));

			calendarBirthday.add(Calendar.YEAR, 1);
		}

	}

}
