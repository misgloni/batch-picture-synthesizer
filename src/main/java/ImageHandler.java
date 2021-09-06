import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageHandler {

    public BufferedImage merge(int width_count, int height_count, int unit_width, int unit_height, ArrayList<BufferedImageData> bufferedImages) {
        BufferedImage ret = new BufferedImage(width_count * unit_width, unit_height * height_count, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g = ret.createGraphics();
        for (int i = 0; i < bufferedImages.size(); i++) {
            int x = i % width_count * unit_width;
            int y = i / width_count * unit_height;
            g.drawImage(bufferedImages.get(i).getBufferedImage(), x, y, null);
        }
        g.dispose();
        return ret;
    }

    public ArrayList<BufferedImageData> readImagesFromFolder(String folder_path) throws IOException {
        File file = new File(folder_path);
        ArrayList<BufferedImageData> ret = new ArrayList<BufferedImageData>();
        String[] file_names = file.list();
        for (int i = 0; i < file_names.length; i++) {
            BufferedImage image = ImageIO.read(new File(folder_path + File.separator + file_names[i]));
            ret.add(new BufferedImageData(image, file_names[i]));
        }
        return ret;
    }

    public WidthAndHeight checkWidthAndHeight(ArrayList<BufferedImageData> bufferedImages) {
        int width = 0, height = 0;
        for (int i = 0; i < bufferedImages.size(); i++) {
            if (i == 0) {
                height = bufferedImages.get(0).getBufferedImage().getHeight();
                width = bufferedImages.get(0).getBufferedImage().getWidth();
            } else {
                if (bufferedImages.get(i).getBufferedImage().getWidth() != width || bufferedImages.get(i).getBufferedImage().getHeight() != height) {
                    throw new RuntimeException(String.format("\"%s\"文件的像素不符合条件%d * %d", bufferedImages.get(i).getFilename(), width, height));
                }
            }
        }

        return new WidthAndHeight(width, height);
    }

    public void createImage(int width_count, int height_count, String folder_path, String save_path) {
        try {
            ArrayList<BufferedImageData> bufferedImages = readImagesFromFolder(folder_path);
            WidthAndHeight widthAndHeight = checkWidthAndHeight(bufferedImages);
            BufferedImage mergeImage = merge(width_count, height_count, widthAndHeight.width, widthAndHeight.height, bufferedImages);
            ImageIO.write(mergeImage, "png", new File(save_path));
        } catch (IOException e) {
            throw new RuntimeException("IO读取失败");
        }
    }

}

class WidthAndHeight {
    public int width;
    public int height;

    public WidthAndHeight(int width, int height) {
        this.width = width;
        this.height = height;
    }
}

class BufferedImageData {
    BufferedImage bufferedImage;
    String filename;

    public BufferedImageData(BufferedImage bufferedImage, String filename) {
        this.bufferedImage = bufferedImage;
        this.filename = filename;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public String getFilename() {
        return filename;
    }
}