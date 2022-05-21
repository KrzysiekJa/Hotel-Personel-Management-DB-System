package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


@Repository
public class EmployeeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Employee> getAllEmployees() throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_employees}");
        return (List<Employee>) callableStatement.executeQuery();
    }

    public Employee getById(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_employee_by_id(?)}");
        callableStatement.setInt("employee_id", (int) id);
        return (Employee) callableStatement.executeQuery();
    }

    public Employee addEmployee(Employee employee) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call add_employee(?,?,?,?,?,?,?,?,?,?)}");
        callableStatement.setInt("position_name", (int) employee.getPosition_ID());
        callableStatement.setString("name", employee.getName());
        callableStatement.setString("surname", employee.getSurname());
        callableStatement.setString("address", employee.getAddress());
        callableStatement.setString("sex", employee.getSex());
        callableStatement.setDate("date_of_birth", (Date) employee.getDate_of_birth());
        callableStatement.setInt("telephone", employee.getTelephone());
        callableStatement.setString("email", employee.getEmail());
        callableStatement.setInt("number_of_vacation_days", employee.getNumber_of_vacation_days());
        callableStatement.setDate("date_of_employment", (Date) employee.getDate_of_employment());
        return (Employee) callableStatement.executeQuery();
    }

    public Employee deleteEmployee(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call delete_employee(?)}");
        callableStatement.setInt("employee_ID", (int) id);
        return (Employee) callableStatement.executeQuery();
    }

    public Employee updateEmployee(long id, Employee employee) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call update_employee(?,?,?,?,?,?,?,?,?,?,?)}");
        callableStatement.setInt("employee_ID", (int) id);
        callableStatement.setInt("position_name", (int) employee.getPosition_ID());
        callableStatement.setString("name", employee.getName());
        callableStatement.setString("surname", employee.getSurname());
        callableStatement.setString("address", employee.getAddress());
        callableStatement.setString("sex", employee.getSex());
        callableStatement.setDate("date_of_birth", (Date) employee.getDate_of_birth());
        callableStatement.setInt("telephone", employee.getTelephone());
        callableStatement.setString("email", employee.getEmail());
        callableStatement.setInt("number_of_vacation_days", employee.getNumber_of_vacation_days());
        callableStatement.setDate("date_of_employment", (Date) employee.getDate_of_employment());
        return (Employee) callableStatement.executeQuery();
    }
}
