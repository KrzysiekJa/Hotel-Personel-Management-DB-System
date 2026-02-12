package com.HotelPersonelManagement.repositories;

import com.HotelPersonelManagement.models.Shift;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Repository for Shift database operations.
 * Provides CRUD operations and retrieval methods for shifts.
 * Uses try-with-resources for proper resource management.
 */
@Repository
public class ShiftRepository extends BaseRepository {

    /**
     * Adds a new shift to the database.
     *
     * @param shift the shift to add
     * @return the added shift
     * @throws SQLException if database operation fails
     * @throws IllegalArgumentException if shift is invalid
     */
    public Shift addShift(Shift shift) throws SQLException {
        validateShift(shift);

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call add_shift(?,?,?)}")) {

            setShiftParameters(callableStatement, shift, false);
            callableStatement.executeUpdate();
            return shift;
        }
    }

    /**
     * Deletes a shift by ID.
     *
     * @param id the shift ID to delete
     * @return true if deletion was successful
     * @throws SQLException if database operation fails
     */
    public boolean deleteShift(long id) throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call delete_shift(?)}")) {

            callableStatement.setLong(1, id);
            callableStatement.executeUpdate();
            return true;
        }
    }

    /**
     * Retrieves all shifts (work plan) from the database.
     *
     * @return List of all shifts
     * @throws SQLException if database operation fails
     */
    public List<Shift> getWorkPlan() throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call get_work_plan}");
             ResultSet resultSet = callableStatement.executeQuery()) {

            List<Shift> shiftList = new ArrayList<>();
            while (resultSet.next()) {
                shiftList.add(mapResultSetToShift(resultSet));
            }
            return shiftList;
        }
    }

    /**
     * Retrieves a shift by ID.
     *
     * @param id the shift ID
     * @return Shift object with the given ID
     * @throws SQLException if database operation fails or shift not found
     */
    public Shift getById(long id) throws SQLException {

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call get_shift_by_ID(?)}")) {

            callableStatement.setLong(1, id);
            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToShift(resultSet);
                }
                throw new SQLException("Shift not found with ID: " + id);
            }
        }
    }

    /**
     * Updates an existing shift.
     *
     * @param id the shift ID to update
     * @param shift the updated shift data
     * @return the updated shift
     * @throws SQLException if database operation fails
     * @throws IllegalArgumentException if shift is invalid
     */
    public Shift updateShift(long id, Shift shift) throws SQLException {
        validateShift(shift);

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call update_shift(?,?,?,?)}")) {

            callableStatement.setLong(1, id);
            setShiftParameters(callableStatement, shift, true);
            callableStatement.executeUpdate();
            return shift;
        }
    }

    /**
     * Maps a ResultSet row to a Shift object.
     *
     * @param resultSet the ResultSet containing shift data
     * @return Shift object
     * @throws SQLException if column retrieval fails
     */
    private Shift mapResultSetToShift(ResultSet resultSet) throws SQLException {
        return new Shift(
            resultSet.getLong("shift_ID"),
            (LocalDateTime) resultSet.getObject("starting_date"),
            (LocalDateTime) resultSet.getObject("ending_date"),
            resultSet.getString("status")
        );
    }

    /**
     * Sets parameters for shift stored procedure.
     *
     * @param stmt the CallableStatement to set parameters for
     * @param shift the shift data
     * @param isUpdate whether this is an update operation
     * @throws SQLException if parameter setting fails
     */
    private void setShiftParameters(CallableStatement stmt, Shift shift, boolean isUpdate) throws SQLException {
        int paramIndex = 1;

        if (isUpdate) {
            // Skip shift_ID for add operation, it's already set in updateShift
            paramIndex = 2;
        }

        stmt.setObject(paramIndex++, shift.getStarting_date());
        stmt.setObject(paramIndex++, shift.getEnding_date());
        stmt.setString(paramIndex, shift.getStatus());
    }

    /**
     * Validates shift data.
     *
     * @param shift the shift to validate
     * @throws IllegalArgumentException if shift data is invalid
     */
    private void validateShift(Shift shift) {
        Objects.requireNonNull(shift, "Shift cannot be null");
        Objects.requireNonNull(shift.getStarting_date(), "Shift starting date cannot be null");
        Objects.requireNonNull(shift.getEnding_date(), "Shift ending date cannot be null");
        Objects.requireNonNull(shift.getStatus(), "Shift status cannot be null");

        if (shift.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Shift status cannot be empty");
        }

        if (shift.getEnding_date().isBefore(shift.getStarting_date())) {
            throw new IllegalArgumentException("Shift ending date must be after starting date");
        }
    }
}

