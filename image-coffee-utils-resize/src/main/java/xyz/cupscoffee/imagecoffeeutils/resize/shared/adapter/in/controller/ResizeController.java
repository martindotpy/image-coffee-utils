package xyz.cupscoffee.imagecoffeeutils.resize.shared.adapter.in.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import xyz.cupscoffee.imagecoffeeutils.resize.shared.application.port.in.ResizePort;

@RestController
public class ResizeController {

    @Autowired
    private ResizePort resizeService;

    @PostMapping
    public ResponseEntity<byte[]> resizeImage(
            @RequestParam int width,
            @RequestParam int height,
            @RequestParam MultipartFile image) {
        byte[] resizedImage = resizeService.resizeImage(width, height, image);
        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(resizedImage);
    }
}
