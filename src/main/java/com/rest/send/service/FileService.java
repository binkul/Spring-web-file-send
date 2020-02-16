package com.rest.send.service;

import com.rest.send.domain.FileContent;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private static final String DIRECTORY = "D:/Jacek/Programy/Upload_files";

    public void saveFile(FileContent fileContent) throws IOException {
        MultipartFile file = fileContent.getFile();
        Path path = Paths.get(DIRECTORY);
        if (file != null && !file.isEmpty()) {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            Path destination = getPath(fileContent.getName());
            file.transferTo(destination);
        }
    }

    public String getMediaType(Path path) {
        String extension = getFileExtension(path);

        switch (extension) {
            case "jpg":
                return MediaType.IMAGE_JPEG_VALUE;
            case "png":
                return MediaType.IMAGE_PNG_VALUE;
            case "gif":
                return MediaType.IMAGE_GIF_VALUE;
            case "txt":
                return MediaType.TEXT_HTML_VALUE;
            case "pdf":
                return MediaType.APPLICATION_PDF_VALUE;
            default:
                return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
    }

    private String getFileExtension(Path path) {
        return FilenameUtils.getExtension(String.valueOf(path)).toLowerCase();
    }

    public Resource getResourceFromPath(Path path) throws MalformedURLException, FileNotFoundException {
        checkFile(path);
        return new UrlResource(path.toUri());
    }

    public Path getPath(String fileName) {
        return Paths.get(DIRECTORY + "/" + fileName);
    }

    private void checkFile(Path path) throws FileNotFoundException {
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File not found: '" + path.getFileName() + "'");
        }
    }
}
