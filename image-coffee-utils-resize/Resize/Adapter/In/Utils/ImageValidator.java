package com.imagecoffee.resize.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class ImageValidator {
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");

    public static boolean isValidImage(MultipartFile file) {
        String extension = file.getOriginalFilename().split("\\.")[1].toLowerCase();
        return ALLOWED_EXTENSIONS.contains(extension);
    }
}
