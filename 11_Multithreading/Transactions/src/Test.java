import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    private Bank bank;
    private int countAccounts;
    private int countIterations;
    private long initialBalance = 100000;
    private final String ACC_NUM_PATTERN = "1-";
    private Random random = new Random();

    public Test(Bank bank, int countAccounts, int countIterations) {
        this.bank = bank;
        this.countAccounts = countAccounts;
        this.countIterations = countIterations;
    }

    private void addAccounts() {
        for (int i = 0; i <= countAccounts; i++) {
            bank.addAccount(ACC_NUM_PATTERN + i, initialBalance);
        }
    }

    public void start() {
        addAccounts();
        executeConcurrentTransaction();
    }

    public String getRandomNumAcc() {
        return ACC_NUM_PATTERN + random.nextInt(countAccounts);
    }

    public void executeConcurrentTransaction() {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime()
                .availableProcessors());

        for (int i = 0; i < countIterations; i++) {
            long randomAmount = Math.round(50000 * Math.random());
            String from = getRandomNumAcc();
            String to = getRandomNumAcc();

            executor.submit(() -> {
                try {
                    bank.transfer(from, to, randomAmount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
    }
}













