package com.bank.service;

import com.bank.model.Employee;
import com.bank.model.EmployeeInformation;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeInformationServiceTest extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getInfo() throws Exception {
        String uri = "/api/employees/info";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Employee[] employees = super.mapFromJson(content, Employee[].class);
        assertTrue(employees.length > 0);
    }

    @Test
    public void saveEmployeeInfo() throws Exception {
        String uri = "/api/employees/info";
        EmployeeInformation employeeInformation = new EmployeeInformation();
        employeeInformation.setCity("Bonn");
        employeeInformation.setBankName("Bonn Bank");
        employeeInformation.setCardNumber("DE1234567890");
        employeeInformation.setStreet("New Street");
        employeeInformation.setId(1L);
        String inputJson = super.mapToJson(employeeInformation);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(employeeInformation.getCardNumber(), "DE1234567890");
    }

    @Test
    public void updateEmployeeInfo() throws Exception {
        String uri = "/api/employees/info/3";
        EmployeeInformation employeeInformation = new EmployeeInformation();
        employeeInformation.setCity("New Bonn");
        employeeInformation.setBankName("New Bonn Bank");
        employeeInformation.setCardNumber("DE0987654321");
        employeeInformation.setStreet("New Street");
        employeeInformation.setId(3L);
        String inputJson = super.mapToJson(employeeInformation);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(employeeInformation.getCardNumber(), "DE0987654321");
    }
}
