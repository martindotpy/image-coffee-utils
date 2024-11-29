package com.imagecoffee.invertcolors.controller;

import com.imagecoffee.invertcolors.dto.InvertColorsResponse;
import com.imagecoffee.invertcolors.service.InvertColorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v0/invert-colors")
public class InvertColorsController {

    @Autowired
    private InvertColorsService invertColorsService;

    @PostMapping
    public ResponseEntity<InvertColorsResponse> invertImageColors(@RequestParam("image") MultipartFile image) {
        try {
            byte[] invertedImage = invertColorsService.invertColors(image);
            InvertColorsResponse response = new InvertColorsResponse(true, "Colors inverted successfully", invertedImage);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                new InvertColorsResponse(false, "Error inverting colors: " + e.getMessage(), null));
        }
    }
}
