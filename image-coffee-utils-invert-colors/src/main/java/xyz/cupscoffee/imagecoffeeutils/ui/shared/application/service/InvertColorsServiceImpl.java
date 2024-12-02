package xyz.cupscoffee.imagecoffeeutils.ui.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.cupscoffee.imagecoffeeutils.ui.application.port.InvertColorsService;
import xyz.cupscoffee.imagecoffeeutils.ui.application.util.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class InvertColorsServiceImpl implements InvertColorsService {

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
