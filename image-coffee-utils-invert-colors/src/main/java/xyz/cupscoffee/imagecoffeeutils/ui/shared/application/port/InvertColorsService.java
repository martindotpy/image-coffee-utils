package xyz.cupscoffee.imagecoffeeutils.ui.application.port;

import org.springframework.web.multipart.MultipartFile;

public interface InvertColorsService {
    byte[] invertColors(MultipartFile image);
}
