package com.example.MySpringTests.api;

import com.example.MySpringTests.Model.Company;
import com.example.MySpringTests.Model.Employee;

import java.io.IOException;
import java.util.List;

public interface X_clients {
    List<Company> getActiveCopmanyList() throws IOException;
    void createEmployee();
    List<Employee> getEmployeeList();
    void deleteCompany();

}
