package com.imagecoffee.resize.dto;

import org.springframework.web.multipart.MultipartFile;

public class ResizeRequest {
    private MultipartFile image;
    private int width;
    private int height;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
