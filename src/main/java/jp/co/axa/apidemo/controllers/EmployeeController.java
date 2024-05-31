package jp.co.axa.apidemo.controllers;

import io.swagger.annotations.ApiOperation;
import jp.co.axa.apidemo.dto.response.EmployeeResponse;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employees")
    @ApiOperation(value = "Retrieve all employee", response = Employee.class, responseContainer = "List")
    public List<Employee> getEmployees() {
        return employeeService.retrieveEmployees();
    }

    @GetMapping("/employees/{employeeId}")
    @ApiOperation(value = "Retrieve specific employee", response = Employee.class)
    public Employee getEmployee(@PathVariable("employeeId") Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Save a new employee", response = EmployeeResponse.class)
    public EmployeeResponse saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @DeleteMapping("/employees/{employeeId}")
    @ApiOperation(value = "Delete existing employee", response = EmployeeResponse.class)
    public EmployeeResponse deleteEmployee(@PathVariable("employeeId")Long employeeId){
        return employeeService.deleteEmployee(employeeId);
    }

    @PutMapping("/employees/{employeeId}")
    @ApiOperation(value = "Edit exiting employee", response = EmployeeResponse.class)
    public EmployeeResponse updateEmployee
            (@RequestBody Employee employee,
             @PathVariable("employeeId")Long employeeId){
//        Employee emp = employeeService.getEmployee(employeeId);
//        if(emp != null){
//            employeeService.updateEmployee(employee);
//        }
        employee.setId(employeeId);
        return employeeService.updateEmployee(employee);
    }

}
