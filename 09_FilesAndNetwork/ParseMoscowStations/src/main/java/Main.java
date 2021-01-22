import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Metro moscowMetro = new Metro("https://www.moscowmap.ru/metro.html#lines");
        moscowMetro.saveJsonFile();
    }

}
