import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private final static int KB_IN_MB = 1024000;
    private final static int KB_IN_GB = 1048576000;

    private static Double filesSize;
    private static List<File> nestedFolders = new ArrayList<>();

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
                getNestedFiles(file);
                printFilesSize("Размер выбранной папки " + path + " составляет - ");
            } else {
                calculateFilesSize(file);
                printFilesSize("Размер выбранного файла " + path + " составляет - ");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void getNestedFiles(File file) {
        for (File nestedFile : file.listFiles()) {
            if (nestedFile.isFile()) {
                calculateFilesSize(nestedFile);
            } else {
                nestedFolders.add(nestedFile);
            }
        }

        while (nestedFolders.size() != 0) {
            getNestedFolders(nestedFolders);
        }
    }

    private static void getNestedFolders(List<File> folders) {
        List<File> bufferFolders = new ArrayList<>();

        for (File nestedFolder : folders) {
            for (File nestedFile : nestedFolder.listFiles()) {
                if (nestedFile.isFile()) {
                    calculateFilesSize(nestedFile);
                } else {
                    bufferFolders.add(nestedFile);
                }
            }
        }

        nestedFolders.clear();
        nestedFolders.addAll(bufferFolders);
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

        if (length <= 12) {
            return String.format("%.2f", size / KB_IN_MB) + " mb";
        } else if (length > 13) {
            return String.format("%.2f" ,size / KB_IN_GB) + " gb";
        }

        return size + " kb";
    }
}
