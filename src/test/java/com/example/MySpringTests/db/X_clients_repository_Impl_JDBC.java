package com.example.MySpringTests.db;

import com.example.MySpringTests.Model.Company;
import com.example.MySpringTests.Model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class X_clients_repository_Impl_JDBC implements X_clients_repository{
    @Autowired
    private ConnectionToDB connection;
    private String company_name="AAAAAAA";
    private String company_desc="bbbbbbb";
    private final static String GET_ALL_COMPANY="select*from company where \"deleted_at\" is null";
    private final static String GET_ALL_ACTIVE_COMPANY="select*from company where \"is_active\"='true' and \"deleted_at\" is null";
    private final static String ADD_NEW_COMPANY="insert into company (name, description) values (?,?)";
    private final static String GET_LAST_ADDED_COMPANY="select*from company where \"deleted_at\" is null order by \"id\" desc limit 1";
    private final static String DELETE_COMPANY="delete from company where \"id\"=?";
    private final static String GET_ALL_EMPLOYEES="select*from employee";


    @Override
    public List<Company> getAllCompanyList() throws SQLException {
        ResultSet resultSet=connection.getConnection().createStatement().executeQuery(GET_ALL_COMPANY);
        List<Company> list=new ArrayList<>();
        while(resultSet.next()){
            Company company=new Company();
            company.setId(resultSet.getInt("id"));
            company.setIsActive(resultSet.getBoolean("is_active"));
            company.setName(resultSet.getString("name"));
            company.setDescription(resultSet.getString("description"));
            company.setDeletedAt(resultSet.getString("deleted_at"));
            list.add(company);
        }
        return list;
    }

    @Override
    public List<Company> getActiveCompanyList() throws SQLException {
        ResultSet resultSet=connection.getConnection().createStatement().executeQuery(GET_ALL_ACTIVE_COMPANY);
        List<Company> list=new ArrayList<>();
        while(resultSet.next()){
            Company company=new Company();
            company.setId(resultSet.getInt("id"));
            company.setIsActive(resultSet.getBoolean("is_active"));
            company.setName(resultSet.getString("name"));
            company.setDescription(resultSet.getString("description"));
            company.setDeletedAt(resultSet.getString("deleted_at"));
            list.add(company);
        }
        return list;
    }

    @Override
    public int addCompany() throws SQLException {
        PreparedStatement statement=connection.getConnection().prepareStatement(ADD_NEW_COMPANY);
        statement.setString(1,company_name);
        statement.setString(2,company_desc);
        statement.executeUpdate();
        ResultSet resultSet=connection.getConnection().createStatement().executeQuery(GET_LAST_ADDED_COMPANY);
        resultSet.next();
        Company company=new Company();
        company.setId(resultSet.getInt("id"));
        return company.getId();
    }

    @Override
    public void deleteCompany(int id) throws SQLException {
        PreparedStatement statement=connection.getConnection().prepareStatement(DELETE_COMPANY);
        statement.setInt(1,id);
        statement.executeUpdate();
    }

    @Override
    public List<Employee> getAllEmployees() throws SQLException {
        ResultSet resultSet=connection.getConnection().createStatement().executeQuery(GET_ALL_EMPLOYEES);
        List<Employee> list=new ArrayList<>();
        while(resultSet.next()){
            Employee employee=new Employee();
            employee.setId(resultSet.getInt("id"));
            employee.setFirstName(resultSet.getString("first_name"));
            employee.setLastName(resultSet.getString("last_name"));
            employee.setCompanyId(resultSet.getInt("company_id"));
            employee.setPhone(resultSet.getString("phone"));
            list.add(employee);
        }
        return list;
    }

    @Override
    public Employee getEmployeeById(int id) {
        return null;
    }
}
