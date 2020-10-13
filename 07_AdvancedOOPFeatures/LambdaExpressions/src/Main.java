import com.skillbox.airport.Flight.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import com.skillbox.airport.*;

public class Main {

    private static String staffFile = "data/staff.txt";
    private static String dateFormat = "dd.MM.yyyy";

    public static void main(String[] args) {
        ArrayList<Employee> staff = loadStaffFromFile();
/*
        staff.sort(((o1, o2) -> {
            if (o1.getSalary().compareTo(o2.getSalary()) == 0) {
                return o1.getName().compareTo(o2.getName());
            } else {
                return o1.getSalary().compareTo(o2.getSalary());
            }
        }));

        staff.sort(Comparator.comparing(Employee::getSalary).thenComparing(Employee::getName));

        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 1, 1);
        Date currentDate = calendar.getTime();

        staff.stream()
                .filter((e) -> e.getWorkStart().getYear() == currentDate.getYear())
                .max(Comparator.comparing(Employee::getSalary))
                .ifPresent(System.out::println);
*/

        Airport airport = Airport.getInstance();

        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date();
        Date plusDate = new Date();
        currentDate.setTime(calendar.getTimeInMillis());

        calendar.add(Calendar.HOUR, 2);
        plusDate.setTime(calendar.getTimeInMillis());



        airport.getTerminals()
                .stream().flatMap(terminal -> terminal.getFlights().stream())
                .filter(flight ->
                {
                    return (flight.getDate().getDay() == currentDate.getDay()
                                    && flight.getDate().getHours() >= currentDate.getHours()
                                    && flight.getDate().getHours() <= plusDate.getHours())
                                    && (flight.getDate().getMinutes() <= plusDate.getMinutes());
                })
                .sorted(new Comparator<Flight>() {
                    @Override
                    public int compare(Flight o1, Flight o2) {
                        return Long.compare(o1.getDate().getTime(), o2.getDate().getTime());
                    }
                })
                .forEach(f -> System.out.println(f.getCode() + " " + f.getAircraft() + " " + f.getDate() + " "
                + f.getType()));
    }

    private static ArrayList<Employee> loadStaffFromFile() {
        ArrayList<Employee> staff = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(staffFile));
            for (String line : lines) {
                String[] fragments = line.split("\t");

                if (fragments.length != 3) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }

                staff.add(new Employee(
                        fragments[0],
                        Integer.parseInt(fragments[1]),
                        (new SimpleDateFormat(dateFormat)).parse(fragments[2])
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return staff;
    }
}