package com.rest.send.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class FileContent {
    private String name;
    private MultipartFile file;
}
