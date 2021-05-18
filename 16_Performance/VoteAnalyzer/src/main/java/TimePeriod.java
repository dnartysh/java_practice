import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimePeriod implements Comparable<TimePeriod> {
    private long from;
    private long to;

    public TimePeriod(long from, long to) {
        this.from = from;
        this.to = to;

        if (from != to) {
            throw new IllegalArgumentException("Dates 'from' and 'to' must be within ONE day!");
        }
    }

    public void appendTime(long visitTime) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
        if (!dayFormat.format(new Date(from))
            .equals(dayFormat.format(new Date(visitTime)))) {
            throw new IllegalArgumentException(
                "Visit time must be within the same day as the current TimePeriod!");
        }

        if (visitTime < from) {
            from = visitTime;
        }
        if (visitTime > to) {
            to = visitTime;
        }
    }

    public String toString() {
        return String.valueOf(from).substring(0, 8) + " " + String.valueOf(from).substring(8)
                + " - " + String.valueOf(to).substring(0, 8) + " " + String.valueOf(to).substring(8);
    }

    @Override
    public int compareTo(TimePeriod period) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date current = new Date();
        Date compared = new Date();
        try {
            current = dayFormat.parse(dayFormat.format(new Date(from)));
            compared = dayFormat.parse(dayFormat.format(new Date(period.from)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return current.compareTo(compared);
    }
}
