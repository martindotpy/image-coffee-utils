package xyz.cupscoffee.imagecoffeeutils.invertcolors.application.usecase;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import xyz.cupscoffee.imagecoffeeutils.invertcolors.application.port.in.InvertColorsImagePort;
import xyz.cupscoffee.imagecoffeeutils.invertcolors.application.util.ImageUtils;

@Service
public class InvertColorsImageUseCase implements InvertColorsImagePort {
    @Override
    public byte[] invertColors(MultipartFile imageFile) {
        try {
            BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());
            BufferedImage invertedImage = ImageUtils.invertColors(originalImage);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(invertedImage, "jpeg", outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error inverting image colors", e);
        }
    }
}
