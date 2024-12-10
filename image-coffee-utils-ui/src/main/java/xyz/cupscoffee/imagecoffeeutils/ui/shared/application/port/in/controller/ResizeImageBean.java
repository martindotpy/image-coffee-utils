package xyz.cupscoffee.imagecoffeeutils.ui.shared.application.port.in.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.primefaces.event.FileUploadEvent;
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
public class ResizeImageBean implements Serializable {

    private UploadedFile originalImageFile;
    private StreamedContent resizedImage;
    private int width;
    private int height;
    private final String apiUrl = "https://image-coffee-utils.cupscoffee.xyz/api/v0/resize";

    @Autowired
    private ObjectMapper objectMapper;

    public void handleFileUpload(FileUploadEvent event) {
        this.originalImageFile = event.getFile();
        this.resizedImage = null;

        if (originalImageFile != null && originalImageFile.getContent() != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully",
                            originalImageFile.getFileName()));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No file uploaded."));
        }
    }

    public StreamedContent getOriginalImage() {
        if (originalImageFile == null) {
            return null;
        }
        return DefaultStreamedContent.builder()
                .stream(() -> new ByteArrayInputStream(originalImageFile.getContent()))
                .contentType(originalImageFile.getContentType())
                .build();
    }

    public void resize() {
        if (originalImageFile == null || width <= 0 || height <= 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                            "Please provide a valid image and dimensions."));
            return;
        }

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl + "?width=" + width + "&height=" + height))
                    .header("Content-Type", "multipart/form-data")
                    .POST(createMultipartBody())
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if (response.statusCode() == 200) {
                resizedImage = DefaultStreamedContent.builder()
                        .stream(() -> new ByteArrayInputStream(response.body()))
                        .contentType(originalImageFile.getContentType())
                        .build();

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Image resized successfully."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                "Resizing failed. Status Code: " + response.statusCode()));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An unexpected error occurred."));
            e.printStackTrace();
        }
    }

    private HttpRequest.BodyPublisher createMultipartBody() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write(("--boundary\r\n").getBytes());
        outputStream.write(("Content-Disposition: form-data; name=\"image\"; filename=\""
                + originalImageFile.getFileName() + "\"\r\n").getBytes());
        outputStream.write(("Content-Type: " + originalImageFile.getContentType() + "\r\n\r\n").getBytes());
        outputStream.write(originalImageFile.getContent());
        outputStream.write("\r\n--boundary--\r\n".getBytes());

        return HttpRequest.BodyPublishers.ofByteArray(outputStream.toByteArray());
    }

    public String getDownloadLink() {
        return resizedImage != null
                ? FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/download?image="
                        + resizedImage
                : null;
    }
}