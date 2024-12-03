package xyz.cupscoffee.imagecoffeeutils.resize.shared.application.port;

import org.springframework.web.multipart.MultipartFile;

public interface ResizeService {
    byte[] resizeImage(int width, int height, MultipartFile image);
}
