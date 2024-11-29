package com.imagecoffee.resize.dto;

public class ResizeResponse {
    private boolean success;
    private String message;
    private byte[] resizedImage;

    public ResizeResponse(boolean success, String message, byte[] resizedImage) {
        this.success = success;
        this.message = message;
        this.resizedImage = resizedImage;
    }

    // Getters y setters
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

    public byte[] getResizedImage() {
        return resizedImage;
    }

    public void setResizedImage(byte[] resizedImage) {
        this.resizedImage = resizedImage;
    }
}
