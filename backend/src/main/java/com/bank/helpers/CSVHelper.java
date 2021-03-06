package com.bank.helpers;

import com.bank.model.Employee;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {


    public static List<Employee> csvToEmployees(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Employee> employees = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Employee employee = new Employee(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("Name"),
                        Long.parseLong(csvRecord.get("DepartmentId")),
                        Double.parseDouble(csvRecord.get("Salary")),
                        csvRecord.get("City"),
                        csvRecord.get("Street"),
                        csvRecord.get("BankName"),
                        csvRecord.get("CardNumber"),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                );

                employees.add(employee);
            }

            return employees;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

}