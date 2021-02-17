import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;

public class ResizePictureThread implements Runnable {
    private int newWidth;
    private File[] files;
    private String dstFolder;

    public ResizePictureThread(int newWidth, File[] files, String dstFolder) {
        this.newWidth = newWidth;
        this.files = files;
        this.dstFolder = dstFolder;
    }

    @Override
    public void run() {
        try {
            for (File file : files) {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    continue;
                }

                int newHeight = (int) Math.round(image.getHeight() /
                        (image.getWidth() / (double) newWidth));

                BufferedImage newImage = Scalr.resize(image, newWidth, newHeight);

                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);

                System.out.println(Thread.currentThread().getName() +
                        " | File: " + newFile.getName());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
