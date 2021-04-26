import java.io.FileWriter;
import java.io.IOException;

public class MyRun implements Runnable {

    private final String path;
    private final int currentRegion;

    public MyRun(String path, int currentRegion) {
        this.path = path;
        this.currentRegion = currentRegion;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();

        try {
            FileWriter writer = new FileWriter(path);

            char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
            StringBuilder sb = new StringBuilder();
            String region = padNumber(currentRegion, 2);

            for (int number = 1; number < 1000; number++) {
                String numberValue = padNumber(number, 3);

                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            sb.append(firstLetter).append(numberValue)
                                    .append(secondLetter).append(thirdLetter)
                                    .append(region)
                                    .append("\n");
                        }
                    }
                }
            }

            writer.write(sb.toString());
            writer.flush();
            writer.close();

            System.out.println("Thread: " + Thread.currentThread().getName()
                    + " - " + (System.currentTimeMillis() - start) + " ms.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String padNumber(int number, int numberLength) {
        String numberStr = Integer.toString(number);
        int padSize = numberLength - numberStr.length();

        if (padSize > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("0".repeat(padSize)).append(numberStr);

            return sb.toString();
        }

        return numberStr;
    }
}
