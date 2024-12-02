package xyz.cupscoffee.imagecoffeeutils.ui.application.port;

import org.springframework.web.multipart.MultipartFile;

public interface ResizeService {
    byte[] resizeImage(int width, int height, MultipartFile image);
}
