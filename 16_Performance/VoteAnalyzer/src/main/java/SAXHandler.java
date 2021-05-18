import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try {
            if (qName.equals("voter")) {
                Voter voter = new Voter(attributes.getValue(0),
                        getBirthdayLong(attributes.getValue(1)));
                Integer count = Storage.getVoterCounts().get(voter);
                Storage.addVoter(voter, count == null ? 1 : count + 1);
            } else if (qName.equals("visit")) {
                int stationNumber = Integer.parseInt(attributes.getValue(0));
                WorkTime wt = Storage.getVoteStationWorkTimes()
                        .get(stationNumber);

                if (wt == null) {
                    wt = new WorkTime();
                    Storage.addStation(stationNumber, wt);
                }

                wt.addVisitTime(getBirthdayLong(attributes.getValue(1)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private long getBirthdayLong(String input) {
        input = input.replace(".", "").replace(" ", "")
        .replace(":", "");

        return Long.parseLong(input);
    }
}
