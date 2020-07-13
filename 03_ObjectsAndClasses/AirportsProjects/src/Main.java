import com.skillbox.airport.Airport;

public class Main {

    public static void main(String[] args) {
        listAircrafts();
        countAircraftsInAirport();
    }

    public static void listAircrafts() {
        Airport airport = Airport.getInstance();
        System.out.println(airport.getAllAircrafts());
    }

    public static void countAircraftsInAirport() {
        Airport airport = Airport.getInstance();
        System.out.println("Count aircrafts in airport: " + airport.getAllAircrafts().size());
    }
}
