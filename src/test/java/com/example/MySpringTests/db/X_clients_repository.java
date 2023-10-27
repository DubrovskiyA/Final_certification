package com.example.MySpringTests.db;

import com.example.MySpringTests.Model.Company;
import com.example.MySpringTests.Model.Employee;

import java.sql.SQLException;
import java.util.List;

public interface X_clients_repository {
    List<Company> getAllCompanyList() throws SQLException;
    List<Company> getActiveCompanyList() throws SQLException;
    int addCompany() throws SQLException;
    void deleteCompany(int id) throws SQLException;
    List<Employee> getAllEmployees() throws SQLException;
    Employee getEmployeeById(int id);
}
