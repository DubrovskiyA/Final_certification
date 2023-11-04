package com.example.MySpringTests.db;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DbConnection {
    private Connection connection;
    @Value("${connection_string}")
    private String connectionString;
    @Value("${user}")
    private String user;
    @Value("${pass}")
    private String pass;
    public Connection getConnection() throws SQLException {
        connection=DriverManager.getConnection(connectionString,user,pass);
        return connection;
    }
    public void close(){
        if (connection!=null){
            connection=null;
        }
        System.out.println("===== connection closed =====");
    }
}
