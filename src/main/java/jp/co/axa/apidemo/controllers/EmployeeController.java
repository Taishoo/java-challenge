package jp.co.axa.apidemo.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jp.co.axa.apidemo.dto.EmployeeForm;
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-client-id", paramType = "header"),
            @ApiImplicitParam(name = "x-client-secret", paramType = "header")
    })
    public List<Employee> getEmployees() {
        return employeeService.retrieveEmployees();
    }

    @GetMapping("/employees/{employeeId}")
    @ApiOperation(value = "Retrieve specific employee", response = Employee.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-client-id", paramType = "header"),
            @ApiImplicitParam(name = "x-client-secret", paramType = "header")
    })
    public Employee getEmployee(@PathVariable("employeeId") Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Save a new employee", response = Employee.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-client-id", paramType = "header"),
            @ApiImplicitParam(name = "x-client-secret", paramType = "header")
    })
    public Employee saveEmployee(@RequestBody EmployeeForm employeeForm) {
        return employeeService.saveEmployee(employeeForm);
    }

    @DeleteMapping("/employees/{employeeId}")
    @ResponseStatus(HttpStatus.GONE)
    @ApiOperation(value = "Delete existing employee", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-client-id", paramType = "header"),
            @ApiImplicitParam(name = "x-client-secret", paramType = "header")
    })
    public String deleteEmployee(@PathVariable("employeeId")Long employeeId){
        return employeeService.deleteEmployee(employeeId);
    }

    @PutMapping("/employees/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Edit exiting employee", response = Employee.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-client-id", paramType = "header"),
            @ApiImplicitParam(name = "x-client-secret", paramType = "header")
    })
    public Employee updateEmployee(@RequestBody EmployeeForm employeeForm, @PathVariable("employeeId")Long employeeId){
        return employeeService.updateEmployee(employeeForm, employeeId);
    }

}
