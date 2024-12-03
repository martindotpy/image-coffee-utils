package xyz.cupscoffee.imagecoffeeutils.resize.shared.domain.model;

public class Image {
    private int width;
    private int height;
    private byte[] content;

    public Image(int width, int height, byte[] content) {
        this.width = width;
        this.height = height;
        this.content = content;
    }

    // Getters and Setters
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

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
