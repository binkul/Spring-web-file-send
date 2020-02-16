package com.rest.send.mapper;

import com.rest.send.domain.FileContent;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileMapper {
    public FileContent mapToFile(String name, MultipartFile file) {
        return new FileContent(name, file);
    }
}
