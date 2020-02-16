package com.rest.send.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileContentDto {
    private String status;
    private String name;
    private String fileType;
    private Long fileSize;
}
