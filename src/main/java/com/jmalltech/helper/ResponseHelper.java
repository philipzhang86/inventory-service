package com.jmalltech.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

public class ResponseHelper {
    public static ResponseEntity<?> notFoundResponse(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", message));
    }

    public static ResponseEntity<?> badRequestResponse(String message) {
        return ResponseEntity.badRequest().body(Collections.singletonMap("error", message));
    }


}
