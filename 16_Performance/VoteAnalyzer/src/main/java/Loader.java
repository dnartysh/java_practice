import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Loader {

    public static void main(String[] args) throws Exception {
        String fileName = "res/data-1572M.xml";

//        DOMParseFile(fileName);
//        Storage.dropAll();

        SAXParseFile(fileName);
        loadToDB();
        Storage.dropAll();
    }

    private static void loadToDB() throws SQLException {
        HashMap<Voter, Integer> list = Storage.getVoterCounts();

        long start = System.currentTimeMillis();

        for (Map.Entry<Voter, Integer> item : list.entrySet()) {
            DBConnection.countVoter(item.getKey().getName(), item.getKey().getBirthDay());
        }

        DBConnection.executeMultiInsert();

        System.out.printf("Обработка завершена за - %d мс", System.currentTimeMillis() - start);
    }

    private static void printResults(String message) {
        System.out.println(message);
        System.out.println("Voting station work times: ");
        for (Integer votingStation : Storage.getVoteStationWorkTimes().keySet()) {
            WorkTime workTime = Storage.getVoteStationWorkTimes().get(votingStation);
            System.out.println("\t" + votingStation + " - " + workTime);
        }

        System.out.println("Duplicated voters: ");
        for (Voter voter : Storage.getVoterCounts().keySet()) {
            Integer count = Storage.getVoterCounts().get(voter);
            if (count > 1) {
                System.out.println("\t" + voter + " - " + count);
            }
        }
    }

    private static void DOMParseFile(String fileName) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(fileName));

        int startMem = getUsageMemory();
        findEqualVoters(doc);
        fixWorkTimes(doc);

        printResults("DOM RESULTS\nUsage memory by DOM parser ~ " + (getUsageMemory() - startMem) + " mb");
    }

    private static void SAXParseFile(String fileName)
            throws ParserConfigurationException, SAXException, IOException {

        int startMem = getUsageMemory();
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        saxParser.parse(new File(fileName), new SAXHandler());

        printResults("SAX RESULTS\nUsage memory by SAX parser ~ " + (getUsageMemory() - startMem) + " mb");
    }

    private static int getUsageMemory() {
        return Math.round(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000;
    }

    private static void findEqualVoters(Document doc) throws Exception {
        NodeList voters = doc.getElementsByTagName("voter");
        int votersCount = voters.getLength();
        for (int i = 0; i < votersCount; i++) {
            Node node = voters.item(i);
            NamedNodeMap attributes = node.getAttributes();

            String name = attributes.getNamedItem("name").getNodeValue();
            long birthDay = getBirthdayLong(attributes.getNamedItem("birthDay").getNodeValue());

            Voter voter = new Voter(name, birthDay);
            Integer count = Storage.getVoterCounts().get(voter);
            Storage.addVoter(voter, count == null ? 1 : count + 1);
        }
    }

    private static void fixWorkTimes(Document doc) throws Exception {
        NodeList visits = doc.getElementsByTagName("visit");
        int visitCount = visits.getLength();

        for (int i = 0; i < visitCount; i++) {
            Node node = visits.item(i);
            NamedNodeMap attributes = node.getAttributes();

            Integer station = Integer.parseInt(attributes.getNamedItem("station").getNodeValue());
            long time = getBirthdayLong(attributes.getNamedItem("time").getNodeValue());
            WorkTime workTime = Storage.getVoteStationWorkTimes().get(station);

            if (workTime == null) {
                workTime = new WorkTime();
                Storage.addStation(station, workTime);
            }

            workTime.addVisitTime(time);
        }
    }

    private static long getBirthdayLong(String input) {
        input = input.replace(".", "").replace(" ", "")
                .replace(":", "");

        return Long.parseLong(input);
    }
}