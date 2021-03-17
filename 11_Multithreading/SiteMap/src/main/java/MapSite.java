import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class MapSite {
    private String root;
    private List<String> completeMap;

    public MapSite(String baseUrl) {
        this.root = baseUrl;
        this.completeMap = new ArrayList<>();
    }

    private HashSet<String> parse() {
        return new MapSiteRecursive(root).invoke();
    }

    public List<String> getMap() throws MapNotParsedException {
        HashSet<String> list = parse();
        int depth = getMinCountSlash(list);

        buildMap(list, depth);

        if (completeMap.isEmpty()) {
            throw new MapNotParsedException();
        }

        return completeMap;
    }

    private void buildMap(HashSet<String> list, int depth) {
        List<String> listUrls = getUrl(list, depth);
        int countTab = 0;
        int maxCount = getMaxCountSlash(list);

        if (depth <= maxCount) {
            for (String str : listUrls) {
                if (completeMap.contains(getTab(countTab + 1) + str + "\n")) {
                    continue;
                }

                completeMap.add(getTab(countTab) + str + "\n");
                addChildUrl(str, list, countTab + 1);
            }

            buildMap(list, depth + 1);
        }
    }

    private void addChildUrl(String url, HashSet<String> list, int countTab) {
        List<String> listChild = list.stream()
                .filter(str -> str.matches(url + ".+$"))
                .collect(Collectors.toList());

        if (listChild.size() > 0) {
            for (String str : listChild) {
                completeMap.add(getTab(countTab) + str + "\n");
                addChildUrl(str, list, countTab + 1);
            }
        }
    }

    private List<String> getUrl(HashSet<String> list, int depth) {
        return list.stream().filter(str -> getCountSlash(str) == depth).collect(Collectors.toList());
    }

    private int getMinCountSlash(HashSet<String> list) {
        int value;
        int minCount = list.size();

        for (String str : list) {
            value = getCountSlash(str);

            if (value < minCount) {
                minCount = value;
            }
        }

        return minCount;
    }

    private int getMaxCountSlash(HashSet<String> list) {
        int value;
        int maxCount = list.size();

        for (String str : list) {
            value = getCountSlash(str);

            if (value > maxCount) {
                maxCount = value;
            }
        }

        return maxCount;
    }

    private int getCountSlash(String url) {
        return (int) url.chars().filter((ch) -> ch == '/').count();
    }

    private String getTab(int countTab) {
        String tab = "\t".repeat(countTab);

        return tab;
    }
}

class MapNotParsedException extends Exception {
    public MapNotParsedException() {
        super();
    }

    public MapNotParsedException(String s) {
        super(s);
    }
}
