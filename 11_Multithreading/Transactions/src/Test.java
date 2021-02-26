import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {
    private Bank bank;
    private int countAccounts;
    private int countIterations;
    private final long initialBalance = 100000;
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

        try {
            executeConcurrentTransaction();
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public String getRandomNumAcc() {
        return ACC_NUM_PATTERN + random.nextInt(countAccounts);
    }

    public void executeConcurrentTransaction() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime()
                .availableProcessors());

        long bankSumStart = bank.getSum();

        for (int i = 0; i < countIterations; i++) {
            long randomAmount = Math.round(50000 * Math.random());
            String from = getRandomNumAcc();
            String to = getRandomNumAcc();

            executor.submit(() -> {bank.transfer(from, to, randomAmount);});
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);

        System.out.printf("%d%n%d%n", bankSumStart, bank.getSum());
    }
}













