package com.HotelPersonelManagement.repositories;

import com.HotelPersonelManagement.models.HotelsEmployees;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Repository for HotelsEmployees database operations.
 * Provides CRUD operations for hotel-employee relationships.
 * Uses try-with-resources for proper resource management.
 */
@Repository
public class HotelsEmployeesRepository extends BaseRepository {

    /**
     * Adds a new hotel-employee relationship to the database.
     *
     * @param hotelsEmployees the hotel-employee relationship to add
     * @return the added relationship
     * @throws SQLException if database operation fails
     * @throws IllegalArgumentException if relationship is invalid
     */
    public HotelsEmployees addHotelsEmployees(HotelsEmployees hotelsEmployees) throws SQLException {
        validateHotelsEmployees(hotelsEmployees);

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call add_hotelsemployees(?,?)}")) {

            callableStatement.setLong(1, hotelsEmployees.getHotel_ID());
            callableStatement.setLong(2, hotelsEmployees.getEmployee_ID());
            callableStatement.executeUpdate();
            return hotelsEmployees;
        }
    }

    /**
     * Deletes a hotel-employee relationship by ID.
     *
     * @param id the hotel-employee relationship ID to delete
     * @return true if deletion was successful
     * @throws SQLException if database operation fails
     */
    public boolean deleteHotelsEmployees(long id) throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call delete_hotelsemployees(?)}")) {

            callableStatement.setLong(1, id);
            callableStatement.executeUpdate();
            return true;
        }
    }

    /**
     * Retrieves all employee-hotel-position relationships from the database.
     * WARNING: This returns raw data in a List format. Consider creating a proper DTO.
     *
     * @return List of lists containing employee name, surname, hotel name, and position name
     * @throws SQLException if database operation fails
     */
    public List<List<String>> getEmployeeHotelPosition() throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call get_EmpHotPos}");
             ResultSet resultSet = callableStatement.executeQuery()) {

            List<List<String>> list = new ArrayList<>();
            while (resultSet.next()) {
                List<String> row = new ArrayList<>(4);
                row.add(resultSet.getString(1));  // employee name
                row.add(resultSet.getString(2));  // surname
                row.add(resultSet.getString(3));  // hotel name
                row.add(resultSet.getString(4));  // position name
                list.add(row);
            }
            return list;
        }
    }

    /**
     * Validates hotel-employee relationship data.
     *
     * @param hotelsEmployees the relationship to validate
     * @throws IllegalArgumentException if relationship data is invalid
     */
    private void validateHotelsEmployees(HotelsEmployees hotelsEmployees) {
        Objects.requireNonNull(hotelsEmployees, "HotelsEmployees cannot be null");

        if (hotelsEmployees.getHotel_ID() <= 0) {
            throw new IllegalArgumentException("Hotel ID must be positive");
        }
        if (hotelsEmployees.getEmployee_ID() <= 0) {
            throw new IllegalArgumentException("Employee ID must be positive");
        }
    }
}

