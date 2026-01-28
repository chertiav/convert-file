package com.chertiavdev.exceptions;

public class FileNotWrittenException extends RuntimeException {
    public FileNotWrittenException(String message, Throwable cause) {
        super(message, cause);
    }
}
