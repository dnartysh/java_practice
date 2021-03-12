public class Loader {

    public static void main(String[] args) {

        MapSite mapSite = new MapSite("https://meduza.io/");
        mapSite.saveFile("src/main/files/mapSite.txt");
    }

}
