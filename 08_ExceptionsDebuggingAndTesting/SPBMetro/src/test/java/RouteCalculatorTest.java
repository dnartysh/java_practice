import core.Line;
import core.Station;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
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
        }

        ArrayList<Station> connectedStation = new ArrayList<>();

        connectedStation.add(new Station("Площадь Восстания", red));
        connectedStation.add(new Station("Маяковская", green));
        stationIndex.addConnection(connectedStation);
        connectedStation.clear();

        connectedStation.add(new Station("Достоевская", orange));
        connectedStation.add(new Station("Владимирская", red));
        stationIndex.addConnection(connectedStation);
        connectedStation.clear();

        connectedStation.add(new Station("Площадь Александра Невского - 1", green));
        connectedStation.add(new Station("Площадь Александра Невского - 2", orange));
        stationIndex.addConnection(connectedStation);
        connectedStation.clear();

        connectedStation.add(new Station("Звенигородская", violet));
        connectedStation.add(new Station("Пушкинская", red));
        stationIndex.addConnection(connectedStation);
        connectedStation.clear();

        return true;
    }

    @Test
    public void getShortestRouteOneLine() {
        ArrayList<Station> expected = new ArrayList<>();
        Station stationA = new Station("Новочеркасская", orange);
        Station stationB = new Station("Спасская", orange);

        expected.add(new Station("Новочеркасская", orange));
        expected.add(new Station("Площадь Александра Невского - 2", orange));
        expected.add(new Station("Лиговский проспект", orange));
        expected.add(new Station("Достоевская", orange));
        expected.add(new Station("Спасская", orange));

        assertEquals(expected, routeCalculator.getShortestRoute(stationA, stationB));
    }

    @Test
    public void getShortestRouteTwoLines() {
        ArrayList<Station> expected = new ArrayList<>();
        Station stationA = new Station("Новочеркасская", orange);
        Station stationB = new Station("Василеостровская", green);

        expected.add(new Station("Новочеркасская", orange));
        expected.add(new Station("Площадь Александра Невского - 2", orange));
        expected.add(new Station("Площадь Александра Невского - 1", green));
        expected.add(new Station("Маяковская", green));
        expected.add(new Station("Гостиный двор", green));
        expected.add(new Station("Василеостровская", green));

        assertEquals(expected, routeCalculator.getShortestRoute(stationA, stationB));
    }

    @Test
    public void getShortestRouteThreeLines() {
        ArrayList<Station> expected = new ArrayList<>();
        Station stationA = new Station("Волковская", violet);
        Station stationB = new Station("Василеостровская", green);

        expected.add(new Station("Волковская", violet));
        expected.add(new Station("Звенигородская", violet));
        expected.add(new Station("Пушкинская", red));
        expected.add(new Station("Владимирская", red));
        expected.add(new Station("Площадь Восстания", red));
        expected.add(new Station("Маяковская", green));
        expected.add(new Station("Гостиный двор", green));
        expected.add(new Station("Василеостровская", green));

        assertEquals(expected, routeCalculator.getShortestRoute(stationA, stationB));
    }

    @Test
    public void calculateDuration() {
        ArrayList<Station> route = new ArrayList<>();

        route.add(new Station("Волковская", violet));
        route.add(new Station("Звенигородская", violet));
        route.add(new Station("Пушкинская", red));
        route.add(new Station("Владимирская", red));
        route.add(new Station("Площадь Восстания", red));
        route.add(new Station("Маяковская", green));
        route.add(new Station("Гостиный двор", green));
        route.add(new Station("Василеостровская", green));

        double expected = 19.5;

        assertEquals(expected, RouteCalculator.calculateDuration(route));
    }
}
