package com.bank.service;

import com.bank.model.Department;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DepartmentServiceTest extends AbstractTest{
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getDepartmentsList() throws Exception {
        String uri = "/api/departments";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Department[] departments = super.mapFromJson(content, Department[].class);
        assertTrue(departments.length > 0);
    }

    @Test
    public void saveDepartment() throws Exception {
        String uri = "/api/departments";
        Department department = new Department();
        department.setDepartmentName("New department");
        String inputJson = super.mapToJson(department);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(department.getDepartmentName(), "New department");
    }

    @Test
    public void updateDepartment() throws Exception{
        String uri = "/api/departments/1";
        Department department = new Department();
        department.setDepartmentName("Development");
        String inputJson = super.mapToJson(department);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals(department.getDepartmentName(), "Development");
    }

    @Test
    public void deleteDepartment() throws Exception{
        String uri = "/api/departments/1";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}