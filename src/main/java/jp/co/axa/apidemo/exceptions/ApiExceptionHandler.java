package jp.co.axa.apidemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiResponseException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiResponseException e) {
        HttpStatus httpStatus = e.getHttpStatus();
        ApiException ex = new ApiException(
                httpStatus,
                e.getMessage(),
                e.getDescription(),
                e.getCode(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(ex, httpStatus);
    }

    @ExceptionHandler(value = {ClientCredentialException.class})
    public ResponseEntity<Object> handleApiRequestException(ClientCredentialException e) {
        ApiException ex = new ApiException(
                HttpStatus.UNAUTHORIZED,
                "Access Denied",
                e.getMessage(),
                "APP_001",
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(ex, HttpStatus.UNAUTHORIZED);
    }
}