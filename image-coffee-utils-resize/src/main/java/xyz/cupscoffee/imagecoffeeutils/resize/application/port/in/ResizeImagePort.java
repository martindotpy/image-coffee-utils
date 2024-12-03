package xyz.cupscoffee.imagecoffeeutils.resize.application.port.in;

import org.springframework.web.multipart.MultipartFile;

public interface ResizeImagePort {
    byte[] resizeImage(int width, int height, MultipartFile image);
}
