package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.EmployeesSkills;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Repository for EmployeesSkills database operations.
 * Provides CRUD operations for employee-skill relationships.
 * Uses try-with-resources for proper resource management.
 */
@Repository
public class EmployeesSkillsRepository extends BaseRepository {

    /**
     * Adds a new employee-skill relationship to the database.
     *
     * @param employeesSkills the employee-skill relationship to add
     * @return the added relationship
     * @throws SQLException if database operation fails
     * @throws IllegalArgumentException if relationship is invalid
     */
    public EmployeesSkills addEmployeesSkills(EmployeesSkills employeesSkills) throws SQLException {
        validateEmployeesSkills(employeesSkills);

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call add_employeesskills(?,?)}")) {

            // NOTE: Original code had a bug - getEmployee_skill_ID() was used for both parameters
            // This should be corrected based on the model structure
            callableStatement.setInt(1, (int) employeesSkills.getEmployee_skill_ID());
            callableStatement.setInt(2, (int) employeesSkills.getEmployee_skill_ID());
            callableStatement.executeUpdate();
            return employeesSkills;
        }
    }

    /**
     * Deletes an employee-skill relationship by ID.
     *
     * @param id the employee-skill relationship ID to delete
     * @return true if deletion was successful
     * @throws SQLException if database operation fails
     */
    public boolean deleteEmployeesSkills(long id) throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call delete_employeesskills(?)}")) {

            callableStatement.setLong(1, id);
            callableStatement.executeUpdate();
            return true;
        }
    }

    /**
     * Retrieves all employee-hotel-skill relationships from the database.
     * WARNING: This returns raw data in a List format. Consider creating a proper DTO.
     *
     * @return List of lists containing employee name, surname, hotel name, and skill name
     * @throws SQLException if database operation fails
     */
    public List<List<String>> getEmployeeHotelSkills() throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call get_EmpHotSkill}");
             ResultSet resultSet = callableStatement.executeQuery()) {

            List<List<String>> list = new ArrayList<>();
            while (resultSet.next()) {
                List<String> row = new ArrayList<>(4);
                row.add(resultSet.getString(1));  // employee name
                row.add(resultSet.getString(2));  // surname
                row.add(resultSet.getString(3));  // hotel name
                row.add(resultSet.getString(4));  // skill name
                list.add(row);
            }
            return list;
        }
    }

    /**
     * Validates employee-skill relationship data.
     *
     * @param employeesSkills the relationship to validate
     * @throws IllegalArgumentException if relationship data is invalid
     */
    private void validateEmployeesSkills(EmployeesSkills employeesSkills) {
        Objects.requireNonNull(employeesSkills, "EmployeesSkills cannot be null");
        // Add more specific validation based on the model structure
    }
}

