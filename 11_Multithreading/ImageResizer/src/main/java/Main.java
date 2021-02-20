import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Main {
    private static final int newWidth = 300;
    private static ConcurrentLinkedQueue<File> queueFiles;
    private static ExecutorService executorService;

    public static void main(String[] args) throws InterruptedException {
        String srcFolder = "/home/denis/Downloads/jpg/src/";
        String dstFolder = "/home/denis/Downloads/jpg/dst/";

        File srcDir = new File(srcFolder);
        File[] files = srcDir.listFiles();
        int countCores = getCountProcessorCores();

        executorService = Executors.newFixedThreadPool(countCores);

        if (files.length > 0) {
            queueFiles = new ConcurrentLinkedQueue<>(Arrays.asList(files));
        }

        ResizePictureThread resizePictureThread = new ResizePictureThread(newWidth, queueFiles, dstFolder);

        while (countCores > 0) {
            executorService.execute(resizePictureThread);
            countCores--;
        }

        executorService.shutdown();
    }

    public static int getCountProcessorCores() {
        return Runtime.getRuntime().availableProcessors();
    }
}
