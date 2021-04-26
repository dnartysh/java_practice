import java.util.HashMap;

public class Storage {
    private static HashMap<Integer, WorkTime> voteStationWorkTimes = new HashMap<>();
    private static HashMap<Voter, Integer> voterCounts = new HashMap<>();

    public static void addVoter(Voter voter, int count) {
        voterCounts.put(voter, count);
    }

    public static void addStation(int station, WorkTime workTime) {
        voteStationWorkTimes.put(station, workTime);
    }

    public static HashMap<Integer, WorkTime> getVoteStationWorkTimes() {
        return voteStationWorkTimes;
    }

    public static HashMap<Voter, Integer> getVoterCounts() {
        return voterCounts;
    }

    public static void dropAll() {
        voteStationWorkTimes.clear();
        voterCounts.clear();
    }
}
