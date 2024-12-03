package xyz.cupscoffee.imagecoffeeutils.resize.shared.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Image {
    private int width;
    private int height;
    private byte[] content;
}
