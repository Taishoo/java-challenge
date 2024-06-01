package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class EmployeeServiceTest {

    @MockBean
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;


    @Test
    public void saveEmployee() {
        Employee entity = new Employee(1L,"Albert", 233, "IT");
        Mockito.when(employeeRepository.save(entity)).thenReturn(entity);
        assertEquals(entity, employeeRepository.save(entity));
    }

    @Test
    public void getEmployee() {
        Employee entity = new Employee(1L,"Albert", 233, "IT");
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(entity));
        assertEquals(Optional.of(1L).get(), employeeService.getEmployee(1L).getId());
        assertEquals(Optional.of("Albert").get(), employeeService.getEmployee(1L).getName());
        assertEquals(Optional.of(233).get(), employeeService.getEmployee(1L).getSalary());
        assertEquals(Optional.of("IT").get(), employeeService.getEmployee(1L).getDepartment());
    }

    @Test
    public void retrieveEmployees() {
        List<Employee> employeeList = Arrays.asList(
                new Employee(1L,"Albert", 111, "IT"),
                new Employee(2L,"De Leon", 222, "Marketing"),
                new Employee(3L,"Junior", 333, "Finance")
        );
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        assertEquals(3, employeeService.retrieveEmployees().size());
    }

    @Test
    public void deleteEmployee() {
        employeeService.deleteEmployee(1L);
        verify(employeeRepository).deleteById(1L);
    }
}
