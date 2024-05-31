package jp.co.axa.apidemo.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponse {
    private String id;
    private String message;
}
