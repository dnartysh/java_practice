import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;

public class ResizePictureThread implements Runnable {

    private final int newWidth;
    private final File file;
    private final String dstFolder;

    public ResizePictureThread(int newWidth, File file, String dstFolder) {
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
        this.file = file;
    }

    @Override
    public void run() {
        try {
            long start = System.currentTimeMillis();

            BufferedImage image = ImageIO.read(file);
            if (image == null) {
                throw new NullPointerException(
                        "Файл: " + file.getName() + " не удалось прочитать");
            }

            int newHeight = (int) Math.round(image.getHeight() /
                    (image.getWidth() / (double) newWidth));

            BufferedImage newImage = Scalr.resize(image, newWidth, newHeight);

            File newFile = new File(dstFolder + "/" + file.getName());
            ImageIO.write(newImage, "jpg", newFile);

            long timeThread = System.currentTimeMillis() - start;

            System.out.println(Thread.currentThread().getName() + " | Time: " + timeThread +
                    " | File: " + newFile.getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
