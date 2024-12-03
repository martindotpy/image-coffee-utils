package xyz.cupscoffee.imagecoffeeutils.resize.adapter.in.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import xyz.cupscoffee.imagecoffeeutils.resize.application.port.in.ResizeImagePort;

@RestController
@RequiredArgsConstructor
public class ResizeController {
    private final ResizeImagePort resizeService;

    /**
     * Resizes the given image to the specified width and height.
     *
     * @param width  the desired width of the resized image
     * @param height the desired height of the resized image
     * @param image  the image file to be resized
     * @return a ResponseEntity containing the resized image as a byte array
     */
    @Operation(summary = "Resize an image to the specified width and height")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> resizeImage(
            @RequestParam int width,
            @RequestParam int height,
            @RequestParam MultipartFile image) {
        byte[] resizedImage = resizeService.resizeImage(width, height, image);
        return ResponseEntity.ok().header("Content-Type", "image/jpeg").body(resizedImage);
    }
}
