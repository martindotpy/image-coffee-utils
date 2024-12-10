package xyz.cupscoffee.imagecoffeeutils.ui.shared.application.port.in.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import lombok.Data;

@Named
@SessionScoped
@Data
public class CropUploaderBean implements Serializable {

    private CroppedImage croppedImage;
    private UploadedFile originalImageFile;
    private StreamedContent cropped;
    private String apiUrl = "https://image-coffee-utils.cupscoffee.xyz/api/v0/crop";

    @Autowired
    private ObjectMapper objectMapper;

    public void handleFileUpload(FileUploadEvent event) {
        this.originalImageFile = event.getFile();
        this.croppedImage = null;
        this.cropped = null;

        if (originalImageFile != null && originalImageFile.getContent() != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully",
                            originalImageFile.getFileName()));
        }
    }

    public StreamedContent getImage() {
        if (originalImageFile == null) {
            return null;
        }
        return DefaultStreamedContent.builder()
                .stream(() -> new ByteArrayInputStream(originalImageFile.getContent()))
                .contentType(originalImageFile.getContentType())
                .build();
    }

    public void crop() {
        if (croppedImage == null || originalImageFile == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                            "No image or cropping coordinates provided."));
            return;
        }

        try {
            HttpClient client = HttpClient.newHttpClient();

            String boundary = "boundary-" + System.currentTimeMillis();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .POST(createMultipartBody(boundary))
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if (response.statusCode() == 200) {

                cropped = DefaultStreamedContent.builder()
                        .stream(() -> new ByteArrayInputStream(response.body()))
                        .contentType("image/jpg")
                        .name("cropped-image.jpg")
                        .build();

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Image cropped successfully."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                "Cropping failed. Status Code: " + response.statusCode()));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An unexpected error occurred."));
            e.printStackTrace();
        }
    }

    private HttpRequest.BodyPublisher createMultipartBody(String boundary) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write(("--" + boundary + "\r\n").getBytes(StandardCharsets.UTF_8));
        outputStream.write(("Content-Disposition: form-data; name=\"file\"; filename=\""
                + originalImageFile.getFileName() + "\"\r\n").getBytes(StandardCharsets.UTF_8));
        outputStream.write(
                ("Content-Type: " + originalImageFile.getContentType() + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
        outputStream.write(originalImageFile.getContent());
        outputStream.write("\r\n".getBytes(StandardCharsets.UTF_8));

        outputStream.write(("--" + boundary + "\r\n").getBytes(StandardCharsets.UTF_8));
        outputStream.write("Content-Disposition: form-data; name=\"x\"\r\n\r\n".getBytes(StandardCharsets.UTF_8));
        outputStream.write(String.valueOf(croppedImage.getLeft()).getBytes(StandardCharsets.UTF_8));
        outputStream.write("\r\n".getBytes(StandardCharsets.UTF_8));

        outputStream.write(("--" + boundary + "\r\n").getBytes(StandardCharsets.UTF_8));
        outputStream.write("Content-Disposition: form-data; name=\"y\"\r\n\r\n".getBytes(StandardCharsets.UTF_8));
        outputStream.write(String.valueOf(croppedImage.getTop()).getBytes(StandardCharsets.UTF_8));
        outputStream.write("\r\n".getBytes(StandardCharsets.UTF_8));

        outputStream.write(("--" + boundary + "\r\n").getBytes(StandardCharsets.UTF_8));
        outputStream.write("Content-Disposition: form-data; name=\"width\"\r\n\r\n".getBytes(StandardCharsets.UTF_8));
        outputStream.write(String.valueOf(croppedImage.getWidth()).getBytes(StandardCharsets.UTF_8));
        outputStream.write("\r\n".getBytes(StandardCharsets.UTF_8));

        outputStream.write(("--" + boundary + "\r\n").getBytes(StandardCharsets.UTF_8));
        outputStream.write("Content-Disposition: form-data; name=\"height\"\r\n\r\n".getBytes(StandardCharsets.UTF_8));
        outputStream.write(String.valueOf(croppedImage.getHeight()).getBytes(StandardCharsets.UTF_8));
        outputStream.write("\r\n".getBytes(StandardCharsets.UTF_8));

        outputStream.write(("--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));

        return HttpRequest.BodyPublishers.ofByteArray(outputStream.toByteArray());
    }
}