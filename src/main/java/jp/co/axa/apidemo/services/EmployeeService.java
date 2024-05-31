package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.response.EmployeeResponse;
import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> retrieveEmployees();

    Employee getEmployee(Long employeeId);

    EmployeeResponse saveEmployee(Employee employee);

    EmployeeResponse deleteEmployee(Long employeeId);

    EmployeeResponse updateEmployee(Employee employee);

}