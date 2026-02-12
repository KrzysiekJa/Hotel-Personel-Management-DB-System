package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.WorkPlanEmployees;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Repository for WorkPlanEmployees database operations.
 * Provides CRUD operations for work plan employee relationships.
 * Uses try-with-resources for proper resource management.
 */
@Repository
public class WorkPlanEmployeesRepository extends BaseRepository {

    /**
     * Adds a new work plan employee assignment to the database.
     *
     * @param workPlanEmployees the work plan employee assignment to add
     * @return the added assignment
     * @throws SQLException if database operation fails
     * @throws IllegalArgumentException if assignment is invalid
     */
    public WorkPlanEmployees addWorkPlanEmployees(WorkPlanEmployees workPlanEmployees) throws SQLException {
        validateWorkPlanEmployees(workPlanEmployees);

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call add_workplanemployees(?,?)}")) {

            callableStatement.setLong(1, workPlanEmployees.getHotel_employee_ID());
            callableStatement.setLong(2, workPlanEmployees.getShift_ID());
            callableStatement.executeUpdate();
            return workPlanEmployees;
        }
    }

    /**
     * Deletes a work plan employee assignment by ID.
     *
     * @param id the work plan employee assignment ID to delete
     * @return true if deletion was successful
     * @throws SQLException if database operation fails
     */
    public boolean deleteWorkPlanEmployees(long id) throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call delete_workplanemployees(?)}")) {

            callableStatement.setLong(1, id);
            callableStatement.executeUpdate();
            return true;
        }
    }

    /**
     * Retrieves all employee-hotel-shift work plan relationships from the database.
     * WARNING: This returns raw data in a List format. Consider creating a proper DTO.
     *
     * @return List of lists containing employee name, surname, hotel name, starting date, ending date, and status
     * @throws SQLException if database operation fails
     */
    public List<List<?>> getWorkPlanEmployees() throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call get_EmpHotShift}");
             ResultSet resultSet = callableStatement.executeQuery()) {

            List<List<?>> list = new ArrayList<>();
            while (resultSet.next()) {
                List<Object> row = new ArrayList<>(6);
                row.add(resultSet.getString(1));  // employee name
                row.add(resultSet.getString(2));  // surname
                row.add(resultSet.getString(3));  // hotel name
                row.add(resultSet.getObject(4));  // starting date
                row.add(resultSet.getObject(5));  // ending date
                row.add(resultSet.getString(6));  // status
                list.add(row);
            }
            return list;
        }
    }

    /**
     * Validates work plan employee assignment data.
     *
     * @param workPlanEmployees the assignment to validate
     * @throws IllegalArgumentException if assignment data is invalid
     */
    private void validateWorkPlanEmployees(WorkPlanEmployees workPlanEmployees) {
        Objects.requireNonNull(workPlanEmployees, "WorkPlanEmployees cannot be null");

        if (workPlanEmployees.getHotel_employee_ID() <= 0) {
            throw new IllegalArgumentException("Hotel employee ID must be positive");
        }
        if (workPlanEmployees.getShift_ID() <= 0) {
            throw new IllegalArgumentException("Shift ID must be positive");
        }
    }
}

