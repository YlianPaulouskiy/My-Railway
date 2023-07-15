package by.itacademy.railway.service;

import jakarta.validation.constraints.NotBlank;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Service
@Validated
public class ImageService {

    @Value("${avatar.image.bucket}")
    private String bucket;

    public void upload(@NotBlank(message = "Image path can not be empty.") String imagePath, InputStream content) {
        Path fullImagePath = Path.of(bucket, imagePath);
        try (content) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, content.readAllBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @SneakyThrows
    public Optional<byte[]> get(@NotBlank(message = "Image path can not be empty.") String imagePath) {
        Path fullImagePath = Path.of(bucket, imagePath);
        return Files.exists(fullImagePath)
                ? Optional.of(Files.readAllBytes(fullImagePath))
                : Optional.empty();
    }

    public void removeImage(@NotBlank(message = "Image path can not be empty.") String imagePath) {
        Path fullImagePath = Path.of(bucket, imagePath);
        try {
            Files.delete(fullImagePath);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
