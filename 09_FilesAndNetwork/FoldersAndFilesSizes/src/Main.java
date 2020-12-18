import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public class Main {

    private final static int KB_IN_B = 1024;
    private final static int KB_IN_MB = 1024000;
    private final static int KB_IN_GB = 1048576000;

    private static Double filesSize;

    public static void main(String[] args) {
        while (true) {
            getFoldersAndFiles(getInputPath());
        }
    }

    private static void getFoldersAndFiles(String path) {
        try {
            filesSize = 0.0;

            File file = new File(path);

            if (file.isDirectory()) {
                Files.walkFileTree(Paths.get(path), new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                            throws IOException {
                        filesSize += attrs.size();
                        return super.visitFile(file, attrs);
                    }
                });
                printFilesSize("Размер выбранной папки " + path + " составляет - ");
            } else {
                calculateFilesSize(file);
                printFilesSize("Размер выбранного файла " + path + " составляет - ");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void calculateFilesSize(File file) {
        filesSize += file.length();
    }

    private static String getInputPath() {
        Scanner input = new Scanner(System.in);
        System.out.println("Введите путь до папки/файла:");

        return input.nextLine();
    }

    private static void printFilesSize(String message) {
        System.out.println(message + getFormattedSize(filesSize));
    }

    private static String getFormattedSize(Double size) {
        int length = String.valueOf(size).length();

        if (length > 8  && length <= 12) {
            return String.format("%.2f", size / KB_IN_MB) + " Mb";
        } else if (length >= 13) {
            return String.format("%.2f", size / KB_IN_GB) + " Gb";
        }

        return String.format("%.2f", size / KB_IN_B) + " Kb";
    }
}
