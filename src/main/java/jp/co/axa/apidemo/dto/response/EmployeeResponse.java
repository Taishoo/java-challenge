package jp.co.axa.apidemo.dto.response;

import lombok.Data;

@Data
public class EmployeeResponse {

    private Long id;

    private String name;

    private Integer salary;

    private String department;
}
