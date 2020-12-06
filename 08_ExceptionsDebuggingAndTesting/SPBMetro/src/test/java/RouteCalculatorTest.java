import core.Line;
import core.Station;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Схема метро
 * <pre>{@code
 *
 *                                             Чернышевская(red)
 *                                                   |
 *                                            Площадь Восстания(red)
 *                                                   |
 *  Василеостровская(green) -> Гостиный двор(green) -> Маяковская(green)   -> Площадь Александра Невского - 1(green)
 *                                                   |                                              |
 *                                 Спасская(orange) -> Достоевская(orange) -> Площадь Александра Невского - 2(orange)
 *                                                   |                                              |
 *                                           Владимирская(red)                            Новочеркасская(orange)
 *                                                   |
 *       Звенигородская(violet)       <-      Пушкинская(red)
 *                 |
 *         Волковская(violet)
 *
 * }</pre>
 */

public class RouteCalculatorTest {
    private StationIndex stationIndex;
    private RouteCalculator routeCalculator;
    private Line red;
    private Line green;
    private Line orange;
    private Line violet;
    private ArrayList<Station> stationsSet;

    @BeforeEach
    protected void setUp() {
        red = new Line(1, "Красная линия");
        green = new Line(2, "Зеленая линия");
        orange = new Line(3, "Оранжевая линия");
        violet = new Line(3, "Фиолетовая линия");

        stationIndex = new StationIndex();
        stationsSet = new ArrayList<>();

        if (addStationsLinesConnectionsToMap()) {
            routeCalculator = new RouteCalculator(stationIndex);
        }
    }

    private boolean addStationsLinesConnectionsToMap() {
        stationIndex.addLine(red);
        stationIndex.addLine(green);
        stationIndex.addLine(orange);
        stationIndex.addLine(violet);

        stationsSet.add(new Station("Чернышевская", red));
        stationsSet.add(new Station("Площадь Восстания", red));
        stationsSet.add(new Station("Владимирская", red));
        stationsSet.add(new Station("Пушкинская", red));
        stationsSet.add(new Station("Василеостровская", green));
        stationsSet.add(new Station("Гостиный двор", green));
        stationsSet.add(new Station("Маяковская", green));
        stationsSet.add(new Station("Площадь Александра Невского - 1", green));
        stationsSet.add(new Station("Спасская", orange));
        stationsSet.add(new Station("Достоевская", orange));
        stationsSet.add(new Station("Лиговский проспект", orange));
        stationsSet.add(new Station("Площадь Александра Невского - 2", orange));
        stationsSet.add(new Station("Новочеркасская", orange));
        stationsSet.add(new Station("Звенигородская", violet));
        stationsSet.add(new Station("Волковская", violet));

        for (Station station : stationsSet) {
            Line line = station.getLine();
            line.addStation(station);
            stationIndex.addStation(station);
        }

        ArrayList<Station> connectedStation = new ArrayList<>();

        connectedStation.add(stationIndex.getStation("Площадь Восстания"));
        connectedStation.add(stationIndex.getStation("Маяковская"));
        stationIndex.addConnection(connectedStation);
        connectedStation.clear();

        connectedStation.add(stationIndex.getStation("Достоевская"));
        connectedStation.add(stationIndex.getStation("Владимирская"));
        stationIndex.addConnection(connectedStation);
        connectedStation.clear();

        connectedStation.add(stationIndex.getStation("Площадь Александра Невского - 1"));
        connectedStation.add(stationIndex.getStation("Площадь Александра Невского - 2"));
        stationIndex.addConnection(connectedStation);
        connectedStation.clear();

        connectedStation.add(stationIndex.getStation("Звенигородская"));
        connectedStation.add(stationIndex.getStation("Пушкинская"));
        stationIndex.addConnection(connectedStation);
        connectedStation.clear();

        return true;
    }

    private List<Station> getRouteByString(String stringRoute) {
        List<Station> stations = new ArrayList<>();

        String[] stationsList = stringRoute.split("\\-\\>");

        for (String value : stationsList) {
            stations.add(stationIndex.getStation(value.trim()));
        }

        return stations;
    }

    @Test
    public void getShortestRouteOneLine() {
        Station stationA = stationIndex.getStation("Новочеркасская");
        Station stationB = stationIndex.getStation("Спасская");

        List<Station> expected = getRouteByString("Новочеркасская -> "
                + "Площадь Александра Невского - 2 ->"
                + "Лиговский проспект ->"
                + "Достоевская ->"
                + "Спасская");

        assertEquals(expected, routeCalculator.getShortestRoute(stationA, stationB));
    }

    @Test
    public void getShortestRouteTwoLines() {
        Station stationA = new Station("Новочеркасская", orange);
        Station stationB = new Station("Василеостровская", green);

        List<Station> expected = getRouteByString("Новочеркасская ->"
                + "Площадь Александра Невского - 2 ->"
                + "Площадь Александра Невского - 1 ->"
                + "Маяковская ->"
                + "Гостиный двор ->"
                + "Василеостровская");

        assertEquals(expected, routeCalculator.getShortestRoute(stationA, stationB));
    }

    @Test
    public void getShortestRouteThreeLines() {
        Station stationA = new Station("Волковская", violet);
        Station stationB = new Station("Василеостровская", green);

        List<Station> expected = getRouteByString("Волковская ->"
                + "Звенигородская ->"
                + "Пушкинская ->"
                + "Владимирская ->"
                + "Площадь Восстания ->"
                + "Маяковская ->"
                + "Гостиный двор ->"
                + "Василеостровская");

        assertEquals(expected, routeCalculator.getShortestRoute(stationA, stationB));
    }

    @Test
    public void calculateDuration() {
        List<Station> route = getRouteByString("Волковская ->"
                + "Звенигородская ->"
                + "Пушкинская ->"
                + "Владимирская ->"
                + "Площадь Восстания ->"
                + "Маяковская ->"
                + "Гостиный двор ->"
                + "Василеостровская");

        double expected = 19.5;

        assertEquals(expected, RouteCalculator.calculateDuration(route));
    }
}
