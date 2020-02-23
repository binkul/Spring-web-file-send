package com.rest.send.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
    @Value("${upload.file.directory}")
    private String directory;

    public void saveFile(String name, MultipartFile file) throws IOException {
        Path path = Paths.get(directory);
        if (file != null && !file.isEmpty()) {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            Path destination = getPath(name);
            file.transferTo(destination);
        }
    }

    public String getMediaType(Path path) {
        return FileType.getMediaType(getFileExtension(path));
    }

    private String getFileExtension(Path path) {
        return FilenameUtils.getExtension(String.valueOf(path)).toLowerCase();
    }

    public Resource getResourceFromPath(Path path) throws MalformedURLException, FileNotFoundException {
        checkFile(path);
        return new UrlResource(path.toUri());
    }

    public Path getPath(String fileName) {
        return Paths.get(directory + "/" + fileName);
    }

    private void checkFile(Path path) throws FileNotFoundException {
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File not found: '" + path.getFileName() + "'");
        }
    }
}
