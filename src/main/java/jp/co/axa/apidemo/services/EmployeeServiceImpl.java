package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.request.EmployeeForm;
import jp.co.axa.apidemo.dto.response.EmployeeResponse;
import jp.co.axa.apidemo.dto.response.EmployeeResultResponse;
import jp.co.axa.apidemo.entities.EmployeeEntity;
import jp.co.axa.apidemo.exceptions.ApiResponseException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private ModelMapper mapper = new ModelMapper();

    public List<EmployeeResponse> retrieveEmployees() {

        // Get all employee data
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();

        // Map to response
        return employeeEntities.stream()
                .map(entity -> mapper.map(entity, EmployeeResponse.class))
                .collect(Collectors.toList());
    }

    public EmployeeResponse getEmployee(Long employeeId) {

        // Find entity by id
        EmployeeEntity optEmp = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ApiResponseException(
                        HttpStatus.NOT_FOUND,
                        "Employee not Found",
                        "Employee id: " + employeeId + " does not exist.",
                        "EMPLOYEE_001"));

        // Map to response
        return mapper.map(optEmp, EmployeeResponse.class);
    }

    public EmployeeResultResponse saveEmployee(EmployeeForm employeeForm) {

        String message = "Employee Saved Successfully";
        EmployeeEntity employeeEntity = mapper.map(employeeForm, EmployeeEntity.class);

        // Perform field validation
        try {
            if (validateEmployee(employeeEntity)) {
                employeeRepository.save(employeeEntity);
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
                .id(employeeEntity.getId().toString())
                .message(message)
                .build();
    }

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

    public EmployeeResultResponse updateEmployee(EmployeeForm employeeForm, Long employeeId) {

        String message = "Employee Updated Successfully";
        EmployeeEntity employeeEntity = mapper.map(employeeForm, EmployeeEntity.class);

        // Check if employee entity exist first
        getEmployee(employeeId);

        // Perform Validations
        try {
            employeeEntity.setId(employeeId);
            employeeRepository.save(employeeEntity);
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
                .id(employeeEntity.getId().toString())
                .message(message)
                .build();
    }

    private Boolean validateEmployee(EmployeeEntity employeeEntity) {
        if ((!Stream.of(
                employeeEntity.getName(),
                employeeEntity.getDepartment(),
                employeeEntity.getSalary()).anyMatch(Objects::isNull))
                && (employeeEntity.getSalary() instanceof Integer ? true : false)) {
            return true;
        } else { throw new RuntimeException("Invalid field data, please try again."); }
    }
}