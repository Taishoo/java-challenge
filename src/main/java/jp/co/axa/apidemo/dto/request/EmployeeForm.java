package jp.co.axa.apidemo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EmployeeForm {

    private String name;

    private Integer salary;

    private String department;

}
