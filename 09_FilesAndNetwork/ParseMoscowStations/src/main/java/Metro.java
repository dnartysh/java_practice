import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Metro {
    private String urlSite = "";
    private String filePath = "src/main/data/file.json";
    private TreeMap<String, String> lines = new TreeMap<>();
    private TreeMap<String, String> stations = new TreeMap<>();
    private HashSet<HashMap<String, String>> connections = new HashSet<>();

    public Metro(String url) throws FileNotFoundException {
        this.urlSite = url;
        createJsonFile();
    }

    public void printJSONFile(String path) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        int countStations = 0;

        JSONObject file = (JSONObject) parser.parse(new FileReader(path));
        JSONObject stationsObject = (JSONObject) file.get("stations");

        for (Object objKey : stationsObject.keySet()) {
            JSONArray stationsArray = (JSONArray) stationsObject.get(objKey.toString());
            System.out.println("Количество станций на линии: " + objKey.toString() + " - " + stationsArray.size());

            countStations += stationsArray.size();
        }

        System.out.println("Количество станций в московском метро: " + countStations);
    }

    private void createJsonFile() throws FileNotFoundException {
        if (urlSite.isEmpty()) {
            throw new FileNotFoundException("Вы ввели некорректный URL сайта");
        } else {
            Document doc = getDocumentPage(urlSite);
            addLines(doc);
            addStations(doc);
        }
    }

    private List<JSONObject> getLines() {
        List<JSONObject> listLines = new ArrayList<>();

        for (Map.Entry<String, String> line : lines.entrySet()) {
            JSONObject lineJSON = new JSONObject();
            lineJSON.put("number", line.getKey());
            lineJSON.put("name", line.getValue());
            listLines.add(lineJSON);
        }

        return listLines;
    }

    private List<List<JSONObject>> getConnections() {
        List<List<JSONObject>> listConnections = new ArrayList<>();

        for (Map<String, String> item : connections) {
            List<JSONObject> list = new ArrayList<>();

            for (Map.Entry<String, String> connection : item.entrySet()) {
                JSONObject connectionsJSON = new JSONObject();
                connectionsJSON.put("line", connection.getValue());
                connectionsJSON.put("station", connection.getKey());
                list.add(connectionsJSON);
            }

            listConnections.add(list);
        }

        return listConnections;
    }

    private JSONObject getStations() {
        JSONObject itemsJSON = new JSONObject();

        for (Map.Entry<String, String> item : stations.entrySet()) {

            List<String> list = stations.entrySet().stream()
                    .filter(s -> s.getValue().equals(item.getValue()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            itemsJSON.put(item.getValue(), list);
        }

        return itemsJSON;
    }

    public void flushJson() {
        if (connections.size() == 0 || lines.size() == 0 || stations.size() == 0) {
            System.out.println("К сожалению не удалось получить список станций");
        } else {
            JSONObject fileJSON = new JSONObject();

            fileJSON.put("stations", getStations());
            fileJSON.put("connections", getConnections());
            fileJSON.put("lines", getLines());
            try {
                FileWriter fw = new FileWriter(filePath);
                fw.write(fileJSON.toJSONString());
                fw.flush();
                fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void addConnections(Elements elements, HashMap<String, String> connectionsMap) {
        if (elements.size() > 2) {
            String line = "";
            String station = "";

            for (int i = 2; i < elements.size(); i++) {
                for (Attribute att : elements.get(i).attributes()) {
                    String value = "";
                    value = att.getValue();

                    if (att.getKey().equals("class")) {
                        line = value.substring(value.indexOf("ln-") + 3);
                    } else if (att.getKey().equals("title")) {
                        station = value.substring(value.indexOf("«") + 1, value.indexOf("»"));
                    }
                }

                if (!"".equals(station) && !"".equals(line)) {
                    connectionsMap.put(station, line);
                }
            }

            if (connectionsMap.size() > 0) {
                connections.add(connectionsMap);
            }
        }
    }

    private void addStations(Document doc) {
        Elements elements = doc.select("div[data-line]");

        for (Element element : elements) {
            if (element.attributes().hasKey("data-line")) {
                for (Element child : element.children()) {
                    Elements transfers = child.select("span");
                    if (transfers.size() != 0) {
                        HashMap<String, String> connectionsMap = new HashMap<>();
                        connectionsMap.put(child.text().split(" ")[1], element.attr("data-line"));
                        addConnections(transfers, connectionsMap);
                    }

                    stations.put(child.text().split(" ")[1], element.attr("data-line"));
                }
            }
        }
    }

    private void addLines(Document doc) {
        Elements elements = doc.select("span[data-line]");

        for (Element element : elements) {
            for (Attribute att : element.attributes()) {
                if (att.getKey().equals("data-line")) {
                    lines.put(att.getValue(), element.text());
                }
            }
        }
    }

    private Document getDocumentPage(String url) {
        try {
            return Jsoup.connect(url).maxBodySize(0).get();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
