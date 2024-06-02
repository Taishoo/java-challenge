package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.EmployeeForm;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.ApiResponseException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
        return employeeEntities;
    }

    @Override
    @Cacheable(cacheNames = "employees", key = "#employeeId")
    public Employee getEmployee(Long employeeId) {

        // Find entity by id
        Employee optEmp = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ApiResponseException(
                        HttpStatus.NOT_FOUND,
                        "Employee not Found",
                        "Employee id: " + employeeId + " does not exist.",
                        "EMPLOYEE_001"
                ));

        return optEmp;
    }

    @Override
    public Employee saveEmployee(EmployeeForm employeeForm) {

        final String message = "Employee Saved Successfully";

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
                    "EMPLOYEE_002"
            );
        }

        return employee;
    }

    @Override
    @CacheEvict(cacheNames = "employees", key = "#employeeId")
    public String deleteEmployee(Long employeeId){

        final String message = "Employee Deleted Successfully";

        // Perform field validation
        try {
            employeeRepository.deleteById(employeeId);
            log.info(message);
        } catch (Exception e) {
            throw new ApiResponseException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Failed to delete Employee",
                    "Employee with id: " + employeeId + " does not exist.",
                    "EMPLOYEE_003"
            );
        }

        return message;
    }

    @Override
    @CachePut(cacheNames = "employees", key = "#employeeId")
    public Employee updateEmployee(EmployeeForm employeeForm, Long employeeId) {
        final String message = "Employee Updated Successfully";
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
                    "EMPLOYEE_004"
            );
        }

        return employee;
    }

    private Boolean validateEmployee(Employee employee) throws RuntimeException {

        final String prefix = "Invalid field data, ";

        // Perform a field validation for employee
        if (Stream.of(employee.getName(), employee.getDepartment(), employee.getSalary()).anyMatch(Objects::isNull)) {
            throw new RuntimeException(prefix + "null values are not allowed.");
        }
        else if (employee.getDepartment().isEmpty() || employee.getName().isEmpty()) {
            throw new RuntimeException(prefix + "empty fields are not allowed.");
        }
        else if (employee.getSalary() < 0) {
            throw new RuntimeException(prefix + "negative values are not allowed.");
        }
        return true;
    }
}