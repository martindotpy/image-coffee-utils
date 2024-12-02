package xyz.cupscoffee.imagecoffeeutils.ui.shared.adapter.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.cupscoffee.imagecoffeeutils.ui.application.port.InvertColorsService;

@RestController
@RequestMapping("/api/v1/invert-colors")
public class InvertColorsController {

    @Autowired
    private InvertColorsService invertColorsService;

    @PostMapping
    public ResponseEntity<byte[]> invertColors(@RequestParam MultipartFile image) {
        byte[] invertedImage = invertColorsService.invertColors(image);
        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(invertedImage);
    }
}
