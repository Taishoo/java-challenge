package jp.co.axa.apidemo.exceptions;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@RequiredArgsConstructor
public class ApiException {
    private final HttpStatus httpStatus;
    private final String message;
    private final String description;
    private final String code;
    private final ZonedDateTime timestamp;
}
