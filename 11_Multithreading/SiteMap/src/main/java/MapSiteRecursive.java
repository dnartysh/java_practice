import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.regex.Pattern;

public class MapSiteRecursive extends RecursiveTask<HashSet<String>> {
    private String root;
    private HashSet<String> listUrls;

    public MapSiteRecursive(String root) {
        this.root = root;
        this.listUrls = new HashSet<>();
    }

    @Override
    protected HashSet<String> compute() {
        List<MapSiteRecursive> taskList = new ArrayList<>();

        try {
            Thread.sleep(1);
            Document doc = Jsoup.connect(root).get();
            Elements elements = new Elements();

            if (doc.body() != null) {
                elements = doc.body().select("a");
            }

            if (elements.size() > 0) {
                for (Element element : elements) {
                    String baseUrl = element.absUrl("href").toLowerCase();

                    if (isCorrectUrl(baseUrl)) {
                        MapSiteRecursive mapSite = new MapSiteRecursive(baseUrl);
                        taskList.add(mapSite);
                        mapSite.fork();
                        listUrls.add(baseUrl);
                    }
                }
            }

            for (MapSiteRecursive task : taskList) {
                task.join();
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex);
        }

        return listUrls;
    }

    private boolean isCorrectUrl(String baseUrl) {
        Pattern patternRoot = Pattern.compile("^" + root + ".+$");
        Pattern patternFile = Pattern.compile("^.+(png|jpg|bmp|gif|pdf)$");
        Pattern patternTags = Pattern.compile("^" + root + "(.+\\#|.+\\#.+)$");

        return !baseUrl.matches(patternFile.pattern())
                && baseUrl.matches(patternRoot.pattern())
                && !baseUrl.matches(patternTags.pattern())
                && !baseUrl.isEmpty();
    }
}
