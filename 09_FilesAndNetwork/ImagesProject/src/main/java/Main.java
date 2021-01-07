import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Main {

    private final static String URL_SITE = "https://lenta.ru/";
    private final static String DOWNLOAD_PATH = "src/main/img/";

    public static void main(String[] args) throws IOException {
        Document doc = getDocumentPage(connectPage());

        if (doc != null) {
            List<String> listImages = getListImages(doc);

            if (listImages.size() != 0) {
                downloadImagesFromList(listImages);
                System.out.println("Изображения сохранены в директорию");
            } else {
                System.out.println("Не удалось найти изображения на странице :(");
            }
        } else {
            System.out.println("Ошибка подключения :(");
        }
    }

    private static void downloadImagesFromList(List<String> list) throws IOException {
        String nameFile = "";

        for (String img : list) {
            nameFile = img.substring(img.lastIndexOf("/") + 1);

            if (isPicture(nameFile)) {
                URL url = new URL(img);
                Files.copy(url.openStream(), Paths.get(DOWNLOAD_PATH + nameFile));
            }
        }

    }

    private static boolean isPicture(String nameFile) {
        return nameFile.matches(".+(\\.)(jpg|jpeg|bmp|png)");
    }

    private static List<String> getListImages(Document doc) {
        List<String> listImages = new ArrayList<>();

        Elements elements = doc.select("img");

        elements.forEach(element -> {

            for (Attribute att : element.attributes()) {
                if (att.getKey().equals("src")) {
                    listImages.add(att.getValue());
                }
            }
        });

        return listImages;
    }

    private static Connection connectPage() {
        return Jsoup.connect(URL_SITE);
    }

    private static Document getDocumentPage(Connection connection) {
        try {
            return connection.get();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
