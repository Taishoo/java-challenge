package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.request.EmployeeForm;
import jp.co.axa.apidemo.dto.response.EmployeeResultResponse;
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

    @Override
    public List<Employee> retrieveEmployees() {

        // Get all employee data
        List<Employee> employeeEntities = employeeRepository.findAll();

        // Map to response
        return employeeEntities;
    }

    @Override
    public Employee getEmployee(Long employeeId) {

        // Find entity by id
        Employee optEmp = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ApiResponseException(
                        HttpStatus.NOT_FOUND,
                        "Employee not Found",
                        "Employee id: " + employeeId + " does not exist.",
                        "EMPLOYEE_001"));

        // Map to response
        return optEmp;
    }

    @Override
    public EmployeeResultResponse saveEmployee(EmployeeForm employeeForm) {

        String message = "Employee Saved Successfully";
        Employee employee = Employee.builder()
                .department(employeeForm.getDepartment())
                .name(employeeForm.getName())
                .salary(employeeForm.getSalary())
                .build();

        // Perform field validation
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

        // Set success message
        return EmployeeResultResponse.builder()
                .id(employee.getId().toString())
                .message(message)
                .build();
    }

    @Override
    public EmployeeResultResponse deleteEmployee(Long employeeId){

        String message = "Employee Deleted Successfully";

        // Perform field validation
        try {
            employeeRepository.deleteById(employeeId);
            log.info(message);
        } catch (Exception e) {
            throw new ApiResponseException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Failed to delete Employee",
                    "Employee with id: " + employeeId + " does not exist.",
                    "EMPLOYEE_003");
        }

        // Set success message
        return EmployeeResultResponse.builder()
                .id(employeeId.toString())
                .message(message)
                .build();
    }

    @Override
    public EmployeeResultResponse updateEmployee(EmployeeForm employeeForm, Long employeeId) {

        String message = "Employee Updated Successfully";
        Employee employee = Employee.builder()
                .salary(employeeForm.getSalary())
                .name(employeeForm.getName())
                .department(employeeForm.getDepartment())
                .id(employeeId)
                .build();

        // Check if employee entity exist first
        getEmployee(employeeId);

        // Perform Validations
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

        // Set success message
        return EmployeeResultResponse.builder()
                .id(employee.getId().toString())
                .message(message)
                .build();
    }

    private Boolean validateEmployee(Employee employee) {
        if ((!Stream.of(
                employee.getName(),
                employee.getDepartment(),
                employee.getSalary()).anyMatch(Objects::isNull))
                && (employee.getSalary() instanceof Integer ? true : false)) {
            return true;
        } else { throw new RuntimeException("Invalid field data, please try again."); }
    }
}