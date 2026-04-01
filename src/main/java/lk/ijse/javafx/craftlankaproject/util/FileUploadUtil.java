package lk.ijse.javafx.craftlankaproject.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUploadUtil {

    private static final String UPLOAD_DIR = "C:\\Users\\User\\GDSE74\\AAD\\JavaEE\\CraftLanka-Project\\CraftLanka-FrontEnd\\assets";

    public static String saveFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            return "C:\\Users\\User\\GDSE74\\AAD\\JavaEE\\CraftLanka-Project\\CraftLanka-FrontEnd\\assets" + uniqueFileName;
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + originalFileName, ioe);
        }
    }
}