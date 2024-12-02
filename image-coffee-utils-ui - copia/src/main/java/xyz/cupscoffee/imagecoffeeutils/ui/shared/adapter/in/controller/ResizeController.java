package xyz.cupscoffee.imagecoffeeutils.ui.shared.adapter.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.cupscoffee.imagecoffeeutils.ui.application.port.ResizeService;

@RestController
@RequestMapping("/api/v1/resize")
public class ResizeController {

    @Autowired
    private ResizeService resizeService;

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
