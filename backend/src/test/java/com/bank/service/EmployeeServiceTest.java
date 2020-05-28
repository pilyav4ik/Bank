package com.bank.service;

import com.bank.model.Employee;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeServiceTest extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

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
        Employee employee = new Employee();
        employee.setDepartment_id(1L);
        employee.setSalary(200);
        employee.setName("Tomas T");
        String inputJson = super.mapToJson(employee);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(employee.getName(), "Tomas T");
    }

    @Test
    public void updateEmployee() throws Exception{
        String uri = "/api/employees/1";
        Employee employee = new Employee();
        employee.setName("Sam");
        employee.setSalary(1500);
        employee.setDepartment_id(1L);

        String inputJson = super.mapToJson(employee);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(employee.getName(), "Sam");
    }

    @Test
    public void deleteEmployee() throws Exception{
        String uri = "/api/employees/1";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}
