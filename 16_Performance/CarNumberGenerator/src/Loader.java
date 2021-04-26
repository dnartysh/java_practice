import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Loader {

    private static ExecutorService service = Executors.newFixedThreadPool(32);

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 1; i <= 200; i++) {
            service.submit(new MyRun("res/numbers" + i + ".txt", i));
        }

        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);

        System.out.println(System.currentTimeMillis() - start);
    }
}