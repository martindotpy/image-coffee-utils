package xyz.cupscoffee.imagecoffeeutils.resize.shared.application.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import xyz.cupscoffee.imagecoffeeutils.resize.shared.application.port.in.ResizePort;
import xyz.cupscoffee.imagecoffeeutils.resize.shared.application.util.ImageUtils;
import xyz.cupscoffee.imagecoffeeutils.shared.adapter.out.annotations.UseCase;

@UseCase
public class ResizeUseCase implements ResizePort {
    @Override
    public byte[] resizeImage(int width, int height, MultipartFile imageFile) {
        try {
            BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());
            BufferedImage resizedImage = ImageUtils.resize(originalImage, width, height);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "jpeg", outputStream);

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error resizing image", e);
        }
    }
}
