package com.rest.send.controller;

import java.io.IOException;

public class FileNotSavedException extends IOException {
    public FileNotSavedException(String message) {
        super(message);
    }
}
