import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetroData {
    private List<Line> lines;
    private Map<String, List<String>> stations;
    private List<List<Connection>> connections;

    public MetroData() {
        lines = new ArrayList<>();
        stations = new HashMap<>();
        connections = new ArrayList<>();
    }

    public List<Line> getLines() {
        return lines;
    }

    public List<List<Connection>> getConnections() {
        return connections;
    }

    public Map<String, List<String>> getStations() {
        return stations;
    }

    public void addConnections(Map<String, String> mapConnections) {
        List<Connection> list = new ArrayList<>();

        for (Map.Entry<String, String> item : mapConnections.entrySet()) {
            if (!"".equals(item.getKey()) && !"".equals(item.getValue())) {
                list.add(new Connection(item.getValue(), item.getKey()));
            }
        }

        connections.add(list);
    }

    public void addLine(String number, String name) {
        lines.add(new Line(number, name));
    }

    public void addStation(String number, String name) {
        if (stations.containsKey(number)) {
            stations.get(number).add(name);
        } else {
            List<String> list = new ArrayList<>();
            list.add(name);
            stations.put(number, list);
        }
    }

}
