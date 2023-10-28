package com.example.MySpringTests.api;

import com.example.MySpringTests.Model.Company;
import com.example.MySpringTests.Model.Employee;

import java.io.IOException;
import java.util.List;

public interface X_clients_service {
    List<Company> getAllCompanyList() throws IOException;
    List<Company> getActiveCopmanyList() throws IOException;
    int addCompany() throws IOException;
    void deleteCompany(int companyId) throws IOException;
    int addEmployee(int companyId) throws IOException;
    List<Employee> getEmployeeList();
    List<Employee> getEmployeeByCompanyId(int companyId) throws IOException;

}
