package org.example.project3.Advice;
import org.example.project3.Api.ApiException;
import org.example.project3.Api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e) {

        return ResponseEntity.status(400).body(new ApiResponse(e.getMessage()));
    }
}
