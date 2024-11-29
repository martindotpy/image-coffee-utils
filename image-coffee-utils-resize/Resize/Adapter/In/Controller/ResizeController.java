package com.imagecoffee.resize.controller;

import com.imagecoffee.resize.dto.ResizeRequest;
import com.imagecoffee.resize.dto.ResizeResponse;
import com.imagecoffee.resize.service.ResizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v0/resize")
public class ResizeController {

    @Autowired
    private ResizeService resizeService;

    @PostMapping
    public ResponseEntity<ResizeResponse> resizeImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("width") int width,
            @RequestParam("height") int height) {
        try {
            byte[] resizedImage = resizeService.resizeImage(image, width, height);
            ResizeResponse response = new ResizeResponse(true, "Image resized successfully", resizedImage);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                new ResizeResponse(false, "Error resizing image: " + e.getMessage(), null));
        }
    }
}

