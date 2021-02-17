import java.io.File;

public class Main {
    private static final int newWidth = 300;
    private static int startPoint;
    private static int middleSize;

    public static void main(String[] args) {
        String srcFolder = "E:\\STUDY\\Folders_and_files\\src";
        String dstFolder = "E:\\STUDY\\Folders_and_files\\dst";

        File srcDir = new File(srcFolder);

        File[] files = srcDir.listFiles();
        int countCores = getCountProcessorCores();

        if (countCores > files.length) {
            middleSize = 1;
        } else {
            middleSize = files.length / countCores;
        }
        startPoint = 0;

        do {
            if (countCores == 1 && middleSize != files.length) {
                middleSize = files.length - startPoint;
            }

            File[] resizedListFiles = new File[middleSize];

            if (startPoint < files.length) {
                System.arraycopy(files, startPoint, resizedListFiles, 0, resizedListFiles.length);
            } else {
                break;
            }

            startPoint += resizedListFiles.length;
            countCores--;

            new Thread(new ResizePictureThread(newWidth, resizedListFiles, dstFolder)).start();
        } while (countCores > 0);
    }

    public static int getCountProcessorCores() {
        return Runtime.getRuntime().availableProcessors();
    }
}
