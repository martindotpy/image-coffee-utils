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

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@SessionScoped
public class InvertColorsBean implements Serializable {

    private UploadedFile uploadedFile;
    private StreamedContent originalImage;
    private StreamedContent invertedImage;
    private String apiUrl = "https://image-coffee-utils.cupscoffee.xyz/api/v0/invert-colors";

    public void handleFileUpload(FileUploadEvent event) {
        this.uploadedFile = event.getFile();
        this.originalImage = DefaultStreamedContent.builder()
                .stream(() -> new ByteArrayInputStream(uploadedFile.getContent()))
                .contentType(uploadedFile.getContentType())
                .build();

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", uploadedFile.getFileName()));
    }

    public void invertColors() {
        if (uploadedFile == null || uploadedFile.getContent() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please upload an image first."));
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
                this.invertedImage = DefaultStreamedContent.builder()
                        .stream(() -> new ByteArrayInputStream(response.body()))
                        .contentType("image/jpg")
                        .build();

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Image inverted successfully."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to invert colors."));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An unexpected error occurred."));
            e.printStackTrace();
        }
    }

    private HttpRequest.BodyPublisher createMultipartBody(String boundary) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(
                ("Content-Disposition: form-data; name=\"image\"; filename=\"" + uploadedFile.getFileName() + "\"\r\n")
                        .getBytes());
        outputStream.write(("Content-Type: " + uploadedFile.getContentType() + "\r\n\r\n").getBytes());
        outputStream.write(uploadedFile.getContent());
        outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());

        return HttpRequest.BodyPublishers.ofByteArray(outputStream.toByteArray());
    }

    public StreamedContent getOriginalImage() {
        return originalImage;
    }

    public StreamedContent getInvertedImage() {
        return invertedImage;
    }

    public String getDownloadUrl() {
        if (invertedImage != null) {
            return "data:" + invertedImage.getContentType() + ";base64," + java.util.Base64.getEncoder()
                    .encodeToString(((ByteArrayInputStream) invertedImage.getStream()).readAllBytes());
        }
        return null;
    }
}