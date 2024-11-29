package com.imagecoffee.invertcolors.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class InvertColorsServiceImpl implements InvertColorsService {

    @Override
    public byte[] invertColors(MultipartFile image) throws Exception {
        try (InputStream inputStream = image.getInputStream()) {
            BufferedImage originalImage = ImageIO.read(inputStream);

            for (int x = 0; x < originalImage.getWidth(); x++) {
                for (int y = 0; y < originalImage.getHeight(); y++) {
                    int rgba = originalImage.getRGB(x, y);
                    int alpha = (rgba >> 24) & 0xff;
                    int red = 255 - ((rgba >> 16) & 0xff);
                    int green = 255 - ((rgba >> 8) & 0xff);
                    int blue = 255 - (rgba & 0xff);

                    int invertedRGBA = (alpha << 24) | (red << 16) | (green << 8) | blue;
                    originalImage.setRGB(x, y, invertedRGBA);
                }
            }

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ImageIO.write(originalImage, "jpeg", outputStream);
                return outputStream.toByteArray();
            }
        }
    }
}
