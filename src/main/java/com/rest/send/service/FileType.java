package com.rest.send.service;

import org.springframework.http.MediaType;

import java.util.stream.Stream;

public enum FileType {
    JPG(MediaType.IMAGE_JPEG_VALUE),
    PNG(MediaType.IMAGE_PNG_VALUE),
    PDF(MediaType.APPLICATION_PDF_VALUE),
    GIF(MediaType.IMAGE_GIF_VALUE),
    TXT(MediaType.TEXT_HTML_VALUE),
    NONE(MediaType.APPLICATION_OCTET_STREAM_VALUE);

    private String mediaType;

    FileType(String mediaType) {
        this.mediaType = mediaType;
    }

    public static String getMediaType(String extension) {
        return Stream.of(FileType.values())
                .filter(i -> i.name().equalsIgnoreCase(extension))
                .map(i -> i.mediaType)
                .findFirst()
                .orElse(NONE.mediaType);
    }
}
