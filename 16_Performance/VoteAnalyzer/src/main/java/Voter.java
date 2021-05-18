public class Voter {

    private String name;
    private long birthDay;

    public Voter(String name, long birthDay) {
        this.name = name;
        this.birthDay = birthDay;
    }

    @Override
    public boolean equals(Object obj) {
        Voter voter = (Voter) obj;
        return name.equals(voter.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
//        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
//        return name + " (" + dayFormat.format(birthDay) + ")";

        String birthDayFormat = String.valueOf(birthDay);

        return name + "(" + birthDayFormat.substring(0, 4) + "." + birthDayFormat.substring(4, 6)
                + "." + birthDayFormat.substring(6, 8) + ")";
    }

    public String getName() {
        return name;
    }

    public long getBirthDay() {
        return birthDay;
    }
}
