package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Repository
public class EmployeeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Employee> getAllEmployees() throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_employees}");
        ResultSet resultSet = callableStatement.executeQuery();

        List<Employee> employeesList = new ArrayList<>();
        while (resultSet.next()) {
            long employee_ID = resultSet.getLong("employee_ID");
            long position_ID = resultSet.getLong("position_ID");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            String address = resultSet.getString("address");
            String sex = resultSet.getString("sex");
            Date date_of_birth = resultSet.getDate("date_of_birth");
            int telephone = resultSet.getInt("telephone");
            String email = resultSet.getString("email");
            int number_of_vacation_days = resultSet.getInt("number_of_vacation_days");
            Date date_of_employment = resultSet.getDate("date_of_employment");

            Employee employee = new Employee(employee_ID, position_ID, name, surname,
                    address, sex, date_of_birth, telephone, email, number_of_vacation_days,
                    date_of_employment);
            employeesList.add(employee);
        }
        return employeesList;
    }

    // TODO: probably going to be deleted
    public Employee getById(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_employee_by_id(?)}");
        callableStatement.setInt("employee_ID", (int) id);
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
        callableStatement.setDate("date_of_birth", employee.getDate_of_birth());
        callableStatement.setInt("telephone", employee.getTelephone());
        callableStatement.setString("email", employee.getEmail());
        callableStatement.setInt("number_of_vacation_days", employee.getNumber_of_vacation_days());
        callableStatement.setDate("date_of_employment", employee.getDate_of_employment());
        callableStatement.executeUpdate();
        return employee;
    }

    public boolean deleteEmployee(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call delete_employee(?)}");
        callableStatement.setInt("employee_ID", (int) id);
        callableStatement.executeUpdate();
        return true;
    }

    public Employee updateEmployee(long id, Employee employee) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call update_employee(?,?,?,?,?,?,?,?,?,?,?)}");
        callableStatement.setInt("employee_ID", (int) id);
        callableStatement.setInt("position_ID", (int) employee.getPosition_ID());
        callableStatement.setString("name", employee.getName());
        callableStatement.setString("surname", employee.getSurname());
        callableStatement.setString("address", employee.getAddress());
        callableStatement.setString("sex", employee.getSex());
        callableStatement.setDate("date_of_birth", employee.getDate_of_birth());
        callableStatement.setInt("telephone", employee.getTelephone());
        callableStatement.setString("email", employee.getEmail());
        callableStatement.setInt("number_of_vacation_days", employee.getNumber_of_vacation_days());
        callableStatement.setDate("date_of_employment", employee.getDate_of_employment());
        callableStatement.executeUpdate();
        return employee;
    }
}
