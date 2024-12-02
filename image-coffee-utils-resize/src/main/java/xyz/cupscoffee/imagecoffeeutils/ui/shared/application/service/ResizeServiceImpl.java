package xyz.cupscoffee.imagecoffeeutils.ui.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.cupscoffee.imagecoffeeutils.ui.application.port.ResizeService;
import xyz.cupscoffee.imagecoffeeutils.ui.application.util.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class ResizeServiceImpl implements ResizeService {

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
