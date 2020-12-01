package com.bank.service;

import com.bank.dto.EmployeeDto;
import com.bank.mappers.EmployeeMapper;
import com.bank.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeServiceTest extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Autowired
    private EmployeeMapper employeeMapper;
    private MvcResult mvcResult;

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
    public void getEmployeeById() throws Exception {
        String uri = "/api/employees/58";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).param("employee_id", "58"))
                .andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        EmployeeDto employee = new ObjectMapper().readValue(json, EmployeeDto.class);
        EmployeeDto newEmployee = new EmployeeDto();
        newEmployee.setId(58L);
        newEmployee.setName("Test Name");
        newEmployee.setDepartmentId(1L);
        newEmployee.setSalary(2000.00);
        newEmployee.setCity("Berlin");
        newEmployee.setStreet("Berlinerstr. 21");
        newEmployee.setBankName("Sparkasse");
        newEmployee.setCardNumber("DE123456789023");
        assertEquals(employee, newEmployee);
    }
    @Test
    public void saveEmployee() throws Exception {
        String uri = "/api/employees/";
        EmployeeDto employeeDto = new EmployeeDto(
                null,
                "Test Name",
                1L,
                2000.00,
                null,
                null,
                null,
                null,
                null,
                null);

        String inputJson = super.mapToJson(employeeDto);

        mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(employeeMapper.dtoToEntity(employeeDto).getName(), "Test Name");
    }

    @Test
    public void updateEmployee() throws Exception {
        String uri = "/api/employees/76";
        EmployeeDto employeeDto = new EmployeeDto(
                null,
                "New Test Name",
                1L,
                2000.00,
                null,
                null,
                null,
                null,
                null,
                null);

        String inputJson = super.mapToJson(employeeDto);

        mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(employeeMapper.dtoToEntity(employeeDto).getName(), "New Test Name");
    }

    @Test
    public void addEmployeeInfo() throws Exception{
        String uri = "/api/employees/76";
        EmployeeDto employeeDto = new EmployeeDto(
                76L,
                "Test Name",
                1L,
                2000.00,
                "Berlin",
                "Berlinerstr. 21",
                "Sparkasse",
                "DE123456789023",
                null,
                null);

        String inputJson = super.mapToJson(employeeDto);

        mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(employeeMapper.dtoToEntity(employeeDto).getName(), "Test Name");
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
        EmployeeDto employee1 = new EmployeeDto(
                null,
                "Test Name First",
                1L,
                2000.00,
                "Berlin",
                "Berlinerstr. 21",
                "Sparkasse",
                "DE123456789023",
                null,
                null);

        EmployeeDto employee2 = new EmployeeDto(
                null,
                "Test Name Second",
                1L,
                2000.00,
                "Berlin",
                "Berlinerstr. 21",
                "Sparkasse",
                "DE123456789023",
                null,
                null);

        List<EmployeeDto> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);

        String inputJson = super.mapToJson(employeeList);

        MvcResult mvcResult = mvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    @Test
    public void saveEmployeesFromCSV() throws Exception {
        String url = "/api/employees/save-from-csv";


        String csvBuilder = "name,departmentId,salary,city,street,bankName,cardNumber\n" +
                "Maxim,1,3855,Madrid,Street,Bank York,NY98675432100\n";
        InputStream is = new ByteArrayInputStream(csvBuilder.getBytes());

        MockMultipartFile mockFile = new MockMultipartFile("file", "employees.csv", "text/csv", is);

        MockHttpServletResponse responseMessage = mvc.perform(MockMvcRequestBuilders.multipart(url)
                .file(mockFile)
                .param("file", "employees2.csv"))
                .andReturn()
                .getResponse();
        assertEquals(responseMessage.getStatus(), 200);
    }
}
