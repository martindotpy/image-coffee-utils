package com.imagecoffee.resize.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class ResizeServiceImpl implements ResizeService {

    @Override
    public byte[] resizeImage(MultipartFile image, int width, int height) throws Exception {
        try (InputStream inputStream = image.getInputStream()) {
            BufferedImage originalImage = ImageIO.read(inputStream);
            BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
            
            Graphics2D graphics = resizedImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics.drawImage(originalImage, 0, 0, width, height, null);
            graphics.dispose();

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ImageIO.write(resizedImage, "jpeg", outputStream);
                return outputStream.toByteArray();
            }
        }
    }
}
