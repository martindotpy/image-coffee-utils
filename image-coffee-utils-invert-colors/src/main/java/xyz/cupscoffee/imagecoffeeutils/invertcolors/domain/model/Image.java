package xyz.cupscoffee.imagecoffeeutils.invertcolors.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Image {
    private int width;
    private int height;
    private byte[] content;
}
