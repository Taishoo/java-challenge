package jp.co.axa.apidemo.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Data
@NoArgsConstructor
public class ApiResponseException extends RuntimeException {

    private String description;
    private String code;
    private HttpStatus httpStatus;

    public ApiResponseException(HttpStatus httpStatus, String message, String description, String code) {
        super(message);
        this.httpStatus = httpStatus;
        this.code = code;
        this.description = description;
        log.error(description);
    }


}