package jp.co.axa.apidemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EmployeeResultResponse {
    private String id;
    private String message;
}
