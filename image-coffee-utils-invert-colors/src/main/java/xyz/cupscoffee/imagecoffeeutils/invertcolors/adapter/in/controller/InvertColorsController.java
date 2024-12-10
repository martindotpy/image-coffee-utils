package xyz.cupscoffee.imagecoffeeutils.invertcolors.adapter.in.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
        // Validar que el archivo es un PNG, JPEG o JPG
        String contentType = image.getContentType();
        if (!MediaType.IMAGE_JPEG_VALUE.equals(contentType)
                && !MediaType.IMAGE_PNG_VALUE.equals(contentType)
                && !"image/jpg".equals(contentType)) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body("Only JPEG, JPG, and PNG images are supported.".getBytes());
        }

        // Invertir los colores de la imagen
        byte[] invertedImage = invertColorsService.invertColors(image);

        // Devolver la respuesta con el tipo MIME adecuado
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(invertedImage);
    }
}