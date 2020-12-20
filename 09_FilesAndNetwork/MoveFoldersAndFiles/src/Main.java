import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Paths;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws Exception {
        String inputFrom = getInputPath("Откуда копируем файлы? Например /home/denis/Desktop/Folder1\n"
                + "Введите путь:");
        String inputTo = getInputPath("Куда копируем файлы? Например /home/denis/Desktop/Folder2\n"
                + "Введите путь:");

        if (!isFolder(inputFrom) || !isFolder(inputTo)) {
            throw new NotDirectoryException("Вы указали файл вместо папки! "
                    + "Или указали несуществующий путь!");
        } else {
            copyFolder(inputFrom, inputTo);
            System.out.println("\nВсе файлы успешно скопированы!");
        }
    }

    private static void copyFolder(String inputFrom, String inputTo) throws IOException {
        File from = new File(inputFrom);

        for (File file : from.listFiles()) {
            if (file.isFile()) {
                File to = new File(inputTo);
                copyFiles(file.toString(), to.toString() + "/" + file.getName());
            } else {
                File to = new File(inputTo + "/" + file.getName());

                if (to.mkdir()) {
                    System.out.println("Создана директория " + to.getAbsolutePath());
                }

                copyFolder(file.toString(), to.toString());
            }
        }
    }

    private static void copyFiles(String inputFrom, String inputTo) throws IOException {
        File from = new File(inputFrom);
        File to = new File(inputTo);

        Files.copy(Paths.get(from.getAbsolutePath()), Paths.get(to.getAbsolutePath()));
        System.out.println("Файл " + from.getAbsolutePath() + " скопирован");
    }

    private static String getInputPath(String message) {
        Scanner input = new Scanner(System.in);
        System.out.println(message);

        return input.nextLine();
    }

    private static boolean isFolder(String path) {
        File file = new File(path);

        return file.isDirectory();
    }
}
