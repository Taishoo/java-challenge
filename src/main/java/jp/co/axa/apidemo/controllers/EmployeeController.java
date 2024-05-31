package jp.co.axa.apidemo.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jp.co.axa.apidemo.dto.request.EmployeeForm;
import jp.co.axa.apidemo.dto.response.EmployeeResponse;
import jp.co.axa.apidemo.dto.response.EmployeeResultResponse;
import jp.co.axa.apidemo.entities.EmployeeEntity;
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
    @ApiOperation(value = "Retrieve all employee", response = EmployeeEntity.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-client-id", paramType = "header"),
            @ApiImplicitParam(name = "x-client-secret", paramType = "header")
    })
    public List<EmployeeResponse> getEmployees() {
        return employeeService.retrieveEmployees();
    }

    @GetMapping("/employees/{employeeId}")
    @ApiOperation(value = "Retrieve specific employee", response = EmployeeEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-client-id", paramType = "header"),
            @ApiImplicitParam(name = "x-client-secret", paramType = "header")
    })
    public EmployeeResponse getEmployee(@PathVariable("employeeId") Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Save a new employee", response = EmployeeResultResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-client-id", paramType = "header"),
            @ApiImplicitParam(name = "x-client-secret", paramType = "header")
    })
    public EmployeeResultResponse saveEmployee(@RequestBody EmployeeForm employeeForm) {
        return employeeService.saveEmployee(employeeForm);
    }

    @DeleteMapping("/employees/{employeeId}")
    @ResponseStatus(HttpStatus.GONE)
    @ApiOperation(value = "Delete existing employee", response = EmployeeResultResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-client-id", paramType = "header"),
            @ApiImplicitParam(name = "x-client-secret", paramType = "header")
    })
    public EmployeeResultResponse deleteEmployee(@PathVariable("employeeId")Long employeeId){
        return employeeService.deleteEmployee(employeeId);
    }

    @PutMapping("/employees/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Edit exiting employee", response = EmployeeResultResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-client-id", paramType = "header"),
            @ApiImplicitParam(name = "x-client-secret", paramType = "header")
    })
    public EmployeeResultResponse updateEmployee(@RequestBody EmployeeForm employeeForm, @PathVariable("employeeId")Long employeeId){
        return employeeService.updateEmployee(employeeForm, employeeId);
    }

}
