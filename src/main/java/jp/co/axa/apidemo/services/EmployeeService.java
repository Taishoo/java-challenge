package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.EmployeeForm;
import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> retrieveEmployees();

    Employee getEmployee(Long employeeId);

    Employee saveEmployee(EmployeeForm employee);

    String deleteEmployee(Long employeeId);

    Employee updateEmployee(EmployeeForm employee, Long employeeId);

}