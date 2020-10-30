import core.Line;
import core.Station;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;

public class RouteCalculatorTest extends TestCase {

    List<Station> route = new ArrayList<>();
    Line red = new Line(1, "Красная ветка");
    Line green = new Line(2, "Зеленая ветка");
    Line blue = new Line(3, "Синяя ветка");

//    @Override
//    protected void setUp() throws Exception {
//
//    }

    private List<Station> getRouteOnTheLine() {
        route.clear();
        route.add(new Station("Выборгская", red));
        route.add(new Station("Площадь Ленина", red));
        route.add(new Station("Чернышевская", red));
        route.add(new Station("Площадь Восстания", red));
        route.add(new Station("Владимирская", red));
        route.add(new Station("Пушкинская", red));
        route.add(new Station("Технологический институт-1", red));

        return route;
    }

    private List<Station> getRouteWithOneConnection() {
        route.clear();
        route.add(new Station("Выборгская", red));
        route.add(new Station("Площадь Ленина", red));
        route.add(new Station("Чернышевская", red));
        route.add(new Station("Площадь Восстания", red));
        route.add(new Station("Маяковская", green));
        route.add(new Station("Площадь Александра Невского-1", green));

        return route;
    }

    private List<Station> getRouteWithTwoConnections() {
        route.clear();
        route.add(new Station("Выборгская", red));
        route.add(new Station("Площадь Ленина", red));
        route.add(new Station("Чернышевская", red));
        route.add(new Station("Площадь Восстания", red));
        route.add(new Station("Владимирская", red));
        route.add(new Station("Пушкинская", red));
        route.add(new Station("Технологический институт-1", red));
        route.add(new Station("Технологический институт-2", blue));
        route.add(new Station("Сенная", blue));
        route.add(new Station("Невский проспект", blue));
        route.add(new Station("Гостиный двор", green));
        route.add(new Station("Маяковская", green));
        route.add(new Station("Площадь Александра Невского-1", green));

        return route;
    }

    public void testCalculateDuration() {
        double expected = 23.5;
        assertEquals(expected, RouteCalculator.calculateDuration(route));
    }

    public void testGetShortestRoute() {

    }

}
