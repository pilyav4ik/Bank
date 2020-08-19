package com.bank.service;

import com.bank.dto.EmployeeDto;
import com.bank.model.Employee;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @MockBean
    private EmployeeService service;

    @Test
    public void getEmployeesList() throws Exception {
        String uri = "/api/employees/";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Employee[] employees = super.mapFromJson(content, Employee[].class);
        assertTrue(employees.length > 0);
    }

    @Test
    public void saveEmployee() throws Exception {
        String uri = "/api/employees/";
        EmployeeDto employeeDto = new EmployeeDto(
                null,
                "Test Name",
                1L,
                2000.00,
                "Berlin", "Berlinerstr. 21",
                "Sparkasse","DE123456789023");
        Employee savedEmployeeEntity = new Employee(
                76L,
                "Test Name",
                1L,
                2000.00,
                "Berlin", "Berlinerstr. 21",
                "Sparkasse","DE123456789023");
        when(service.createEmployee(employeeDto)).thenReturn(savedEmployeeEntity);


        /*Employee employee = new Employee();
        employee.setDepartmentId(1L);
        employee.setSalary(200);
        employee.setName("Tomas T");
        String inputJson = super.mapToJson(employee);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(employee.getName(), "Tomas T");*/
    }

    @Test
    public void updateEmployee() throws Exception{
        String uri = "/api/employees/1";
        Employee employee = new Employee();
        employee.setName("Sam");
        employee.setSalary(1500);
        employee.setDepartmentId(1L);

        String inputJson = super.mapToJson(employee);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(employee.getName(), "Sam");
    }

    @Test
    public void addEmployeeInfo() throws Exception{
        String uri = "/api/employees/3";
        Employee employee = new Employee();
        employee.setName("Sam");
        employee.setSalary(1500);
        employee.setDepartmentId(1L);

        employee.setCity("Berlin");
        employee.setStreet("Alexander Platz");
        employee.setBankName("Sparkasse");
        employee.setCardNumber("DE12345678912");
        String inputJson = super.mapToJson(employee);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(employee.getBankName(), "Sparkasse");
    }

    @Test
    public void deleteEmployee() throws Exception{
        String uri = "/api/employees/1";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
    @Test
    public void sortBySalaryAsc() throws Exception {
        String uri = "/api/employees=by-salary-asc";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Employee[] employees = super.mapFromJson(content, Employee[].class);
        assertEquals(employees[0].getId(), 3L);

    }

    @Test
    public void sortBySalaryDesc() throws Exception {
        String uri = "/api/employees=by-salary-desc";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Employee[] employees = super.mapFromJson(content, Employee[].class);
        assertEquals(employees[0].getId(), 2L);

    }

    @Test
    public void saveListEmployees() throws Exception {
        String uri = "/api/employees/save-all";
        Employee employee1 = new Employee();
        employee1.setDepartmentId(1L);
        employee1.setSalary(200);
        employee1.setName("Tomas First");
        employee1.setCity("Berlin");
        employee1.setStreet("Street first");
        Employee employee2 = new Employee();
        employee2.setDepartmentId(1L);
        employee2.setSalary(200);
        employee2.setName("Max Second");
        employee2.setCity("Berlin");
        employee2.setStreet("Street second");

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);

        String inputJson = super.mapToJson(employeeList);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(employee1.getName(), "Tomas First");
        assertEquals(employee2.getName(), "Max Second");
    }
}
