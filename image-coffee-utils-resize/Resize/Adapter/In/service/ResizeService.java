package com.imagecoffee.resize.service;

import org.springframework.web.multipart.MultipartFile;

public interface ResizeService {
    byte[] resizeImage(MultipartFile image, int width, int height) throws Exception;
}
