package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.response.EmployeeResponse;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.ApiResponseException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    public Employee getEmployee(Long employeeId) {
        Employee optEmp = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ApiResponseException(
                        HttpStatus.NOT_FOUND,
                        "Employee not Found",
                        "Employee id: " + employeeId + " does not exist.",
                        "EMPLOYEE_001"));
        return optEmp;
    }

    public EmployeeResponse saveEmployee(Employee employee){
        String message = "Employee Saved Successfully";
        try {
            if (validateEmployee(employee)) {
                employeeRepository.save(employee);
                log.info(message);
            }
        } catch (Exception e) {
            throw new ApiResponseException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Failed to save new Employee",
                    e.getLocalizedMessage(),
                    "EMPLOYEE_002");
        }
        return EmployeeResponse.builder()
                .id(employee.getId().toString())
                .message(message)
                .build();
    }

    public EmployeeResponse deleteEmployee(Long employeeId){
        String message = "Employee Deleted Successfully";
        try {
            employeeRepository.deleteById(employeeId);
            log.info(message);
        } catch (Exception e) {
            throw new ApiResponseException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Failed to delete Employee",
                    e.getLocalizedMessage(),
                    "EMPLOYEE_003");
        }
        return EmployeeResponse.builder()
                .id(employeeId.toString())
                .message(message)
                .build();
    }

    public EmployeeResponse updateEmployee(Employee employee) {
        String message = "Employee Updated Successfully";
        try {
            employeeRepository.save(employee);
            log.info(message);
        } catch (Exception e) {
            throw new ApiResponseException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Failed to update Employee",
                    e.getLocalizedMessage(),
                    "EMPLOYEE_004");
        }
        return EmployeeResponse.builder()
                .id(employee.getId().toString())
                .message(message)
                .build();
    }

    private Boolean validateEmployee(Employee employee) {
        if ((!Stream.of(employee.getName(), employee.getDepartment(), employee.getSalary()).anyMatch(Objects::isNull))
                && (employee.getSalary() instanceof Integer ? true : false)) {
            return true;
        } else { throw new RuntimeException("Invalid field data, please try again."); }
    }
}