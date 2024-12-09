package xyz.cupscoffee.imagecoffeeutils.ui.shared.application.port.in.controller;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import xyz.cupscoffee.imagecoffeeutils.ui.shared.domain.model.CommonColor;
import xyz.cupscoffee.imagecoffeeutils.ui.shared.domain.model.CommonColorResponse;

import org.primefaces.event.FileUploadEvent;
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
public class ColorsBean implements Serializable {

    private UploadedFile uploadedFile;
    private List<CommonColor> colors;
    private String apiUrl = "http://localhost:8080/api/v0/colors";
    private int n = 5;

    @Autowired
    private ObjectMapper objectMapper;

    public void handleFileUpload(FileUploadEvent event) {
        this.uploadedFile = event.getFile();

        if (uploadedFile != null && uploadedFile.getContent() != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully",
                            uploadedFile.getFileName()));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No file uploaded."));
        }

    }

    public void processColors() {
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

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                CommonColorResponse colorsResponse = objectMapper.readValue(response.body(), CommonColorResponse.class);
                this.colors = colorsResponse.getContent(); // Extracted colors
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Colors extracted successfully."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                "Failed to process the image. Status Code: " + response.statusCode()));
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
        outputStream.write(
                ("Content-Disposition: form-data; name=\"image\"; filename=\"" + uploadedFile.getFileName() + "\"\r\n")
                        .getBytes(StandardCharsets.UTF_8));
        outputStream.write(
                ("Content-Type: " + uploadedFile.getContentType() + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
        outputStream.write(uploadedFile.getContent());
        outputStream.write("\r\n".getBytes(StandardCharsets.UTF_8));

        outputStream.write(("--" + boundary + "\r\n").getBytes(StandardCharsets.UTF_8));
        outputStream.write("Content-Disposition: form-data; name=\"n\"\r\n\r\n".getBytes(StandardCharsets.UTF_8));
        outputStream.write(String.valueOf(n).getBytes(StandardCharsets.UTF_8));
        outputStream.write("\r\n".getBytes(StandardCharsets.UTF_8));

        outputStream.write(("--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));

        return HttpRequest.BodyPublishers.ofByteArray(outputStream.toByteArray());
    }

}
