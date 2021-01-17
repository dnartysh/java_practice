import java.io.IOException;
import org.json.simple.parser.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        Metro moscow = new Metro("https://www.moscowmap.ru/metro.html#lines");
        moscow.flushJson();
        moscow.printJSONFile("src/main/data/file.json");
    }

}
