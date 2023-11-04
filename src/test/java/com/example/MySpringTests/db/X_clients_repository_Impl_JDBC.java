package com.example.MySpringTests.db;

import com.example.MySpringTests.Model.Company;
import com.example.MySpringTests.Model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Configuration
@PropertySource("classpath:test-data.properties")
public class X_clients_repository_Impl_JDBC implements X_clients_repository{
    @Autowired
    private DbConnection connection;
    @Value("${company_name}")
    private String company_name;
    @Value("${company_desc}")
    private String company_desc;
    @Value("${first_name}")
    private String first_name;
    @Value("${last_name}")
    private String last_name;
    @Value("${phone}")
    private String phone;
    private final static String GET_ALL_COMPANY="select*from company where \"deleted_at\" is null";
    private final static String GET_ALL_ACTIVE_COMPANY="select*from company where \"is_active\"='true' and \"deleted_at\" is null";
    private final static String ADD_NEW_COMPANY="insert into company (name, description) values (?,?)";
    private final static String GET_LAST_ADDED_COMPANY="select*from company where \"deleted_at\" is null order by \"id\" desc limit 1";
    private final static String DELETE_COMPANY="delete from company where \"id\"=?";
    private final static String GET_ALL_EMPLOYEES="select*from employee";
    private final static String ADD_NEW_EMPLOYEE="insert into employee (company_id, first_name, last_name, phone) values (?,?,?,?)";
    private final static String GET_LAST_ADDED_EMPLOYEE="select*from employee order by \"id\" desc limit 1";
    private final static String CHANGE_IS_ACTIVE_EMPLOYEE_INFO="update employee set is_active='false' where \"id\"=?";
    private final static String GET_COMPANY_BY_ID="select*from company where \"id\"=?";
    private final static String DELETE_EMPLOYEE_BY_NAME="delete from employee where \"first_name\"=?";
    private final static String DELETE_COMPANY_BY_NAME="delete from company where \"name\"=?";


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
            company.setDeletedAt(resultSet.getTimestamp("deleted_at"));
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
            company.setDeletedAt(resultSet.getTimestamp("deleted_at"));
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
    public int addEmployee(int company_id) throws SQLException {
        PreparedStatement statement=connection.getConnection().prepareStatement(ADD_NEW_EMPLOYEE);
        statement.setInt(1,company_id);
        statement.setString(2,first_name);
        statement.setString(3,last_name);
        statement.setString(4,phone);
        statement.executeUpdate();
        ResultSet set=connection.getConnection().createStatement().executeQuery(GET_LAST_ADDED_EMPLOYEE);
        set.next();
        Employee employee=new Employee();
        employee.setId(set.getInt("id"));
        return employee.getId();
    }

    @Override
    public void changeEmployeeIsActiveInfo(int employeeId) throws SQLException {
        PreparedStatement statement=connection.getConnection().prepareStatement(CHANGE_IS_ACTIVE_EMPLOYEE_INFO);
        statement.setInt(1,employeeId);
        statement.executeUpdate();
    }

    @Override
    public Company getCompanyById(int companyId) throws SQLException {
        PreparedStatement statement=connection.getConnection().prepareStatement(GET_COMPANY_BY_ID);
        statement.setInt(1,companyId);
        ResultSet set=statement.executeQuery();
        set.next();
        Company company=new Company();
        company.setDeletedAt(set.getTimestamp("deleted_at"));
        return company;
    }
    private void deleteEmployee() throws SQLException {
        System.out.println("===== cleaning db after tests =====");
        PreparedStatement statement=connection.getConnection().prepareStatement(DELETE_EMPLOYEE_BY_NAME);
        statement.setString(1,first_name);
        int i = statement.executeUpdate();
        System.out.println("deleted employees from employee table:"+i);
    }
    private void deleteCompanyByName() throws SQLException {
        PreparedStatement statement=connection.getConnection().prepareStatement(DELETE_COMPANY_BY_NAME);
        statement.setString(1,company_name);
        int i = statement.executeUpdate();
        System.out.println("deleted companies from company table:"+i);
    }
    @PreDestroy
    private void preDestroy() throws SQLException {
        deleteEmployee();
        deleteCompanyByName();
        connection.close();
    }
}
