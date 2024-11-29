package com.imagecoffee.invertcolors.dto;

public class InvertColorsResponse {
    private boolean success;
    private String message;
    private byte[] invertedImage;

    public InvertColorsResponse(boolean success, String message, byte[] invertedImage) {
        this.success = success;
        this.message = message;
        this.invertedImage = invertedImage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getInvertedImage() {
        return invertedImage;
    }

    public void setInvertedImage(byte[] invertedImage) {
        this.invertedImage = invertedImage;
    }
}

