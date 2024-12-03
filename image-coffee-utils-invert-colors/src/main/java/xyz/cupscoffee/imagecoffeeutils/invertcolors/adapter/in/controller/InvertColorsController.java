package xyz.cupscoffee.imagecoffeeutils.invertcolors.adapter.in.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import xyz.cupscoffee.imagecoffeeutils.invertcolors.application.port.in.InvertColorsImagePort;

@Tag(name = "Invert Colors", description = "Invert colors of an image")
@RestController
@RequiredArgsConstructor
public class InvertColorsController {
    private final InvertColorsImagePort invertColorsService;

    @Operation(summary = "Invert colors of an image")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> invertColors(@RequestParam MultipartFile image) {
        byte[] invertedImage = invertColorsService.invertColors(image);
        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(invertedImage);
    }
}
