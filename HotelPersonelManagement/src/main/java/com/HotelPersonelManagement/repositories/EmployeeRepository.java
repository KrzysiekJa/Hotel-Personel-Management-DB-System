package com.HotelPersonelManagement.repositories;

import com.HotelPersonelManagement.models.Employee;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Repository for Employee database operations.
 * Provides CRUD operations and retrieval methods for employees.
 * Uses try-with-resources for proper resource management.
 */
@Repository
public class EmployeeRepository extends BaseRepository {

    /**
     * Retrieves all employees from the database.
     *
     * @return List of all employees
     * @throws SQLException if database operation fails
     */
    public List<Employee> getAllEmployees() throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call get_employees}");
             ResultSet resultSet = callableStatement.executeQuery()) {

            List<Employee> employeesList = new ArrayList<>();
            while (resultSet.next()) {
                employeesList.add(mapResultSetToEmployee(resultSet));
            }
            return employeesList;
        }
    }

    /**
     * Retrieves an employee by ID.
     *
     * @param id the employee ID
     * @return Employee object with the given ID
     * @throws SQLException if database operation fails or employee not found
     */
    public Employee getById(long id) throws SQLException {

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call get_employee_by_id(?)}")) {

            callableStatement.setLong(1, id);
            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToEmployee(resultSet);
                }
                throw new SQLException("Employee not found with ID: " + id);
            }
        }
    }

    /**
     * Adds a new employee to the database.
     *
     * @param employee the employee to add
     * @return the added employee
     * @throws SQLException if database operation fails
     * @throws IllegalArgumentException if employee is invalid
     */
    public Employee addEmployee(Employee employee) throws SQLException {
        validateEmployee(employee);

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call add_employee(?,?,?,?,?,?,?,?,?,?)}")) {

            setEmployeeParameters(callableStatement, employee, false);
            callableStatement.executeUpdate();
            return employee;
        }
    }

    /**
     * Deletes an employee by ID.
     *
     * @param id the employee ID to delete
     * @return true if deletion was successful
     * @throws SQLException if database operation fails
     */
    public boolean deleteEmployee(long id) throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call delete_employee(?)}")) {

            callableStatement.setLong(1, id);
            callableStatement.executeUpdate();
            return true;
        }
    }

    /**
     * Updates an existing employee.
     *
     * @param id the employee ID to update
     * @param employee the updated employee data
     * @return the updated employee
     * @throws SQLException if database operation fails
     * @throws IllegalArgumentException if employee is invalid
     */
    public Employee updateEmployee(long id, Employee employee) throws SQLException {
        validateEmployee(employee);

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call update_employee(?,?,?,?,?,?,?,?,?,?,?)}")) {

            callableStatement.setLong(1, id);
            setEmployeeParameters(callableStatement, employee, true);
            callableStatement.executeUpdate();
            return employee;
        }
    }

    /**
     * Maps a ResultSet row to an Employee object.
     *
     * @param resultSet the ResultSet containing employee data
     * @return Employee object
     * @throws SQLException if column retrieval fails
     */
    private Employee mapResultSetToEmployee(ResultSet resultSet) throws SQLException {
        return new Employee(
            resultSet.getLong("employee_ID"),
            resultSet.getLong("position_ID"),
            resultSet.getString("name"),
            resultSet.getString("surname"),
            resultSet.getString("address"),
            resultSet.getString("sex"),
            resultSet.getDate("date_of_birth"),
            resultSet.getInt("telephone"),
            resultSet.getString("email"),
            resultSet.getInt("number_of_vacation_days"),
            resultSet.getDate("date_of_employment")
        );
    }

    /**
     * Sets parameters for employee stored procedure.
     *
     * @param stmt the CallableStatement to set parameters for
     * @param employee the employee data
     * @param isUpdate whether this is an update operation
     * @throws SQLException if parameter setting fails
     */
    private void setEmployeeParameters(CallableStatement stmt, Employee employee, boolean isUpdate) throws SQLException {
        int paramIndex = 1;

        if (isUpdate) {
            // Skip employee_ID for add operation, it's already set in updateEmployee
            paramIndex = 2;
        }

        stmt.setLong(paramIndex++, employee.getPosition_ID());
        stmt.setString(paramIndex++, employee.getName());
        stmt.setString(paramIndex++, employee.getSurname());
        stmt.setString(paramIndex++, employee.getAddress());
        stmt.setString(paramIndex++, employee.getSex());
        stmt.setDate(paramIndex++, employee.getDate_of_birth());
        stmt.setInt(paramIndex++, employee.getTelephone());
        stmt.setString(paramIndex++, employee.getEmail());
        stmt.setInt(paramIndex++, employee.getNumber_of_vacation_days());
        stmt.setDate(paramIndex, employee.getDate_of_employment());
    }

    /**
     * Validates employee data.
     *
     * @param employee the employee to validate
     * @throws IllegalArgumentException if employee data is invalid
     */
    private void validateEmployee(Employee employee) {
        Objects.requireNonNull(employee, "Employee cannot be null");
        Objects.requireNonNull(employee.getName(), "Employee name cannot be null");
        Objects.requireNonNull(employee.getSurname(), "Employee surname cannot be null");
        Objects.requireNonNull(employee.getEmail(), "Employee email cannot be null");

        if (employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }
        if (employee.getSurname().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee surname cannot be empty");
        }
        if (employee.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee email cannot be empty");
        }
    }
}

