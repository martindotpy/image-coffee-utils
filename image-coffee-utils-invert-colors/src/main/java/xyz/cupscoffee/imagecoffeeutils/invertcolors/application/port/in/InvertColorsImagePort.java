package xyz.cupscoffee.imagecoffeeutils.invertcolors.application.port.in;

import org.springframework.web.multipart.MultipartFile;

public interface InvertColorsImagePort {
    byte[] invertColors(MultipartFile image);
}
