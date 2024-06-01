package jp.co.axa.apidemo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.dto.EmployeeForm;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.interceptors.ClientCredentialInterceptor;
import jp.co.axa.apidemo.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerTest {


    @MockBean
    private EmployeeService employeeService;

    @MockBean
    ClientCredentialInterceptor clientCredentialInterceptor;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllEmployees() throws Exception {
        Mockito.when(employeeService.retrieveEmployees()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/employees").contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
    }

    @Test
    public void saveEmployee() throws Exception {
        EmployeeForm form = new EmployeeForm("Albert", 233, "IT");
        Employee response = new Employee(1l,"Albert", 233, "IT");
        Mockito.when(employeeService.saveEmployee(form)).thenReturn(response);

        mockMvc.perform(post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(form)))
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployee() throws Exception {
        Mockito.when(employeeService.getEmployee(1L)).thenReturn(new Employee(1L, "Albert", 233, "IT"));

        mockMvc.perform(get("/api/v1/employees/{employeeId}", 1L).contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
    }

    @Test
    public void updateEmployee() throws Exception {
        EmployeeForm form = new EmployeeForm("Albert", 2332, "Marketing");
        Employee response = new Employee(1l,"Albert", 2332, "Marketing");
        Mockito.when(employeeService.saveEmployee(form)).thenReturn(response);

        mockMvc.perform(put("/api/v1/employees/{employeeId}", 1L).contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(form)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEmployee() throws Exception {
        Mockito.when(employeeService.deleteEmployee(1L)).thenReturn("Employee Deleted Successfully");

        mockMvc.perform(delete("/api/v1/employees/{employeeId}", 1L).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }
}