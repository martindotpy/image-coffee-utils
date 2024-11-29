package com.imagecoffee.invertcolors.service;

import org.springframework.web.multipart.MultipartFile;

public interface InvertColorsService {
    byte[] invertColors(MultipartFile image) throws Exception;
}
