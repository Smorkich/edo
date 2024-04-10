package com.education.service;

import org.springframework.http.ResponseEntity;

public interface AppealXLSXFileGeneration {

    /**
     * Generates an appeal file in xlsx format
     */
    ResponseEntity<byte[]> generateXLSXFile(Long appealId);
}
