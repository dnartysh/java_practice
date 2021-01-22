import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Metro {
    private final MetroData metroData;
    private final String FILE_PATH = "src/main/data/filenew.json";

    private String urlSite = "";

    public Metro(String url) throws FileNotFoundException {
        this.urlSite = url;
        metroData = new MetroData();
        addDataToJsonFile();
    }

    public void saveJsonFile() throws IOException {
        File newFile = new File(FILE_PATH);

        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        jsonMapper.writeValue(newFile, metroData);
    }

    private void addDataToJsonFile() throws FileNotFoundException {
        if (urlSite.isEmpty()) {
            throw new FileNotFoundException("Вы ввели некорректный URL сайта");
        } else {
            Document doc = getDocumentPage(urlSite);
            addLines(doc);
            addStations(doc);
        }
    }

    private void addConnections(Elements elements, Map<String, String> connectionsMap) {
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
                metroData.addConnections(connectionsMap);
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
                        Map<String, String> connectionsMap = new HashMap<>();
                        connectionsMap.put(child.text().split(" ")[1],
                                element.attr("data-line"));

                        addConnections(transfers, connectionsMap);
                    }

                    metroData.addStation(element.attr("data-line"),
                            child.text().split(" ")[1]);
                }
            }
        }
    }

    private void addLines(Document doc) {
        Elements elements = doc.select("span[data-line]");

        for (Element element : elements) {
            for (Attribute att : element.attributes()) {
                if (att.getKey().equals("data-line")) {
                    metroData.addLine(att.getValue(), element.text());
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
