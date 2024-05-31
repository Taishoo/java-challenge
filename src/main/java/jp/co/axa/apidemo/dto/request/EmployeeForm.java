package jp.co.axa.apidemo.dto.request;

import lombok.Data;

@Data
public class EmployeeForm {

    private String name;

    private Integer salary;

    private String department;

}
