package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.request.EmployeeForm;
import jp.co.axa.apidemo.dto.response.EmployeeResponse;
import jp.co.axa.apidemo.dto.response.EmployeeResultResponse;
import jp.co.axa.apidemo.entities.EmployeeEntity;

import java.util.List;

public interface EmployeeService {

    List<EmployeeResponse> retrieveEmployees();

    EmployeeResponse getEmployee(Long employeeId);

    EmployeeResultResponse saveEmployee(EmployeeForm employee);

    EmployeeResultResponse deleteEmployee(Long employeeId);

    EmployeeResultResponse updateEmployee(EmployeeForm employee, Long employeeId);

}