package com.rest.send.controller;

import com.rest.send.domain.FileContentDto;
import com.rest.send.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/file")
public class FileController {
    @Autowired
    FileService service;

    @GetMapping(value = "/download/{name}")
    ResponseEntity<Resource> download(@PathVariable String name) throws FileNotFoundException, MalformedURLException {
        Path path = service.getPath(name);
        String mediaType = service.getMediaType(path);
        Resource resource = service.getResourceFromPath(path);

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mediaType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    FileContentDto upload(@RequestParam(value = "name") String name, @RequestParam(value = "file") MultipartFile file) throws FileNotSavedException {

        try {
            service.saveFile(name, file);
            return new FileContentDto("File saved successfully.", file.getOriginalFilename(), file.getContentType(), file.getSize());
        } catch (IOException ex) {
            throw new FileNotSavedException("File not saved. Error: " + ex.getMessage());
        }
    }

    @PostMapping(value = "uploadFiles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<FileContentDto> uploadFiles(@RequestParam(value = "files") MultipartFile[] files) throws FileNotSavedException {
        List<FileContentDto> result = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                service.saveFile(file.getOriginalFilename(), file);
                result.add(new FileContentDto("File saved successfully.", file.getOriginalFilename(), file.getContentType(), file.getSize()));
            } catch (IOException ex) {
                throw new FileNotSavedException("File not saved. Error: " + ex.getMessage());
            }
        }

        return result;
    }
}
