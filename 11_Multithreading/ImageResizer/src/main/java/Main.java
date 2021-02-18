import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    private static final int newWidth = 300;
    private static ConcurrentLinkedQueue<File> queueFiles;

    public static void main(String[] args) throws InterruptedException {
        String srcFolder = "E:\\STUDY\\Folders_and_files\\src";
        String dstFolder = "E:\\STUDY\\Folders_and_files\\dst";

        File srcDir = new File(srcFolder);

        File[] files = srcDir.listFiles();
        int countCores = getCountProcessorCores();

        if (files.length > 0) {
            queueFiles = new ConcurrentLinkedQueue<>(Arrays.asList(files));
        }

        ResizePictureThread resizePictureThread = new ResizePictureThread(newWidth, queueFiles, dstFolder);

        while (countCores > 0) {
            new Thread(resizePictureThread).start();
            countCores--;
        }
    }

    public static int getCountProcessorCores() {
        return Runtime.getRuntime().availableProcessors();
    }
}
