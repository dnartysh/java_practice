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
import java.util.stream.Collectors;

public class MapSiteRecursive extends RecursiveTask<HashSet<String>> {
    private final String root;
    private final HashSet<String> listUrls;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_PURPLE = "\u001B[35m";

    private MapSiteRecursive(String root, HashSet<String> listUrls) {
        this.root = root;
        this.listUrls = listUrls;
    }

    public MapSiteRecursive(String root) {
        this.root = root;
        this.listUrls = new HashSet<>();
    }

    @Override
    protected HashSet<String> compute() {
        List<MapSiteRecursive> taskList = new ArrayList<>();

        try {
            Thread.sleep(20);
            Document doc = Jsoup.connect(root).get();
            Elements elements = new Elements();
            System.out.printf("Поток - %s. Ссылка - %s\n", Thread.currentThread().getName(), root);

            if (doc.body() != null) {
                elements = doc.body().select("a");
            }

            List<Element> listElements = elements.stream()
                    .filter(el -> {
                        String url = el.absUrl("href");
                        return isCorrectUrl(url) && !listUrls.contains(url);
                    })
                    .collect(Collectors.toList());

            System.out.printf("Поток %s. По ссылке - %s, найдено %d элементов\n",
                    Thread.currentThread().getName(), root, listElements.size());

            if (listElements.size() > 0) {
                for (Element element : listElements) {
                    String baseUrl = element.absUrl("href").toLowerCase();

                    if (listUrls.contains(baseUrl)) {
                        continue;
                    }

                    MapSiteRecursive mapSite = new MapSiteRecursive(baseUrl, listUrls);
                    taskList.add(mapSite);
                    mapSite.fork();
                    listUrls.add(baseUrl);

                    System.out.println(ANSI_GREEN + "Запись " + baseUrl + " добавлена." + ANSI_RESET);
                    System.err.printf(ANSI_PURPLE + "Поток %s. Создана новая задача c url %s\n" + ANSI_RESET,
                            Thread.currentThread().getName(), baseUrl);
                }

                System.out.println(ANSI_YELLOW + "Количество ссылок " + listUrls.size() + ANSI_RESET);
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
        Pattern patternRoot = Pattern.compile("^" + getDomain() + ".+$");
        Pattern patternFile = Pattern.compile("^.+(png|jpg|bmp|gif|pdf)$");
        Pattern patternTags = Pattern.compile("^" + getDomain() + "(\\#.+|.+\\#|.+\\#.+|\\#)$");

        return !baseUrl.matches(patternFile.pattern())
                && baseUrl.matches(patternRoot.pattern())
                && !baseUrl.matches(patternTags.pattern())
                && !baseUrl.isEmpty();
    }

    private String getDomain() {
        return root.substring(0, root.indexOf('/', root.indexOf('/') + 2) + 1);
    }
}
