import java.io.FileWriter;
import java.io.IOException;

public class MyRun implements Runnable {
    private final String path;

    public MyRun(String path) {
        this.path = path;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();

        try {
            FileWriter writer = new FileWriter(path);

            char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
            for (int regionCode = 50; regionCode < 200; regionCode++) {
                StringBuilder sb = new StringBuilder();

                for (int number = 1; number < 1000; number++) {
                    for (char firstLetter : letters) {
                        for (char secondLetter : letters) {
                            for (char thirdLetter : letters) {
                                sb.append(firstLetter).append(padNumber(number, 3))
                                        .append(secondLetter).append(thirdLetter)
                                        .append(padNumber(regionCode, 2))
                                        .append("\n");
                            }
                        }
                    }
                }

                writer.write(sb.toString());
            }

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
