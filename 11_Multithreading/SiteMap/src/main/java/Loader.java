import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Loader {

    public static void main(String[] args) {
        MapSite mapSite = new MapSite("https://www.pobeda.aero/");
//        MapSite mapSite = new MapSite("https://skillbox.ru/");

        try {
            Files.write(Path.of("src/main/files/mapSite.txt"), mapSite.getMap().toString().getBytes());
        } catch (IOException | MapNotParsedException ex) {
            ex.printStackTrace();
        }
    }

}
