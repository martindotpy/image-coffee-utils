package xyz.cupscoffee.imagecoffeeutils.resize.application.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageUtils {

    public static BufferedImage resize(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
}
