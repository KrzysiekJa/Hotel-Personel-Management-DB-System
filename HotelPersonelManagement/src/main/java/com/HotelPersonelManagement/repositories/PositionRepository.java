package com.HotelPersonelManagement.repositories;

import com.HotelPersonelManagement.models.Position;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Repository for Position database operations.
 * Provides CRUD operations and retrieval methods for positions.
 * Uses try-with-resources for proper resource management.
 */
@Repository
public class PositionRepository extends BaseRepository {

    /**
     * Adds a new position to the database.
     *
     * @param position the position to add
     * @return the added position
     * @throws SQLException if database operation fails
     * @throws IllegalArgumentException if position is invalid
     */
    public Position addPosition(Position position) throws SQLException {
        validatePosition(position);

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call add_position(?,?)}")) {

            setPositionParameters(callableStatement, position, false);
            callableStatement.executeUpdate();
            return position;
        }
    }

    /**
     * Deletes a position by ID.
     *
     * @param id the position ID to delete
     * @return true if deletion was successful
     * @throws SQLException if database operation fails
     */
    public boolean deletePosition(long id) throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call delete_position(?)}")) {

            callableStatement.setLong(1, id);
            callableStatement.executeUpdate();
            return true;
        }
    }

    /**
     * Retrieves all positions from the database.
     *
     * @return List of all positions
     * @throws SQLException if database operation fails
     */
    public List<Position> getAllPositions() throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call get_positions}");
             ResultSet resultSet = callableStatement.executeQuery()) {

            List<Position> positionList = new ArrayList<>();
            while (resultSet.next()) {
                positionList.add(mapResultSetToPosition(resultSet));
            }
            return positionList;
        }
    }

    /**
     * Retrieves a position by ID.
     *
     * @param id the position ID
     * @return Position object with the given ID
     * @throws SQLException if database operation fails or position not found
     */
    public Position getById(long id) throws SQLException {

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call get_position_by_id(?)}")) {

            callableStatement.setLong(1, id);
            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToPosition(resultSet);
                }
                throw new SQLException("Position not found with ID: " + id);
            }
        }
    }

    /**
     * Updates an existing position.
     *
     * @param id the position ID to update
     * @param position the updated position data
     * @return the updated position
     * @throws SQLException if database operation fails
     * @throws IllegalArgumentException if position is invalid
     */
    public Position updatePosition(long id, Position position) throws SQLException {
        validatePosition(position);

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call update_position(?,?,?)}")) {

            callableStatement.setLong(1, id);
            setPositionParameters(callableStatement, position, true);
            callableStatement.executeUpdate();
            return position;
        }
    }

    /**
     * Maps a ResultSet row to a Position object.
     *
     * @param resultSet the ResultSet containing position data
     * @return Position object
     * @throws SQLException if column retrieval fails
     */
    private Position mapResultSetToPosition(ResultSet resultSet) throws SQLException {
        return new Position(
            resultSet.getLong("position_ID"),
            resultSet.getString("name"),
            resultSet.getString("description")
        );
    }

    /**
     * Sets parameters for position stored procedure.
     *
     * @param stmt the CallableStatement to set parameters for
     * @param position the position data
     * @param isUpdate whether this is an update operation
     * @throws SQLException if parameter setting fails
     */
    private void setPositionParameters(CallableStatement stmt, Position position, boolean isUpdate) throws SQLException {
        int paramIndex = 1;

        if (isUpdate) {
            // Skip position_ID for add operation, it's already set in updatePosition
            paramIndex = 2;
        }

        stmt.setString(paramIndex++, position.getName());
        stmt.setString(paramIndex, position.getDescription());
    }

    /**
     * Validates position data.
     *
     * @param position the position to validate
     * @throws IllegalArgumentException if position data is invalid
     */
    private void validatePosition(Position position) {
        Objects.requireNonNull(position, "Position cannot be null");
        Objects.requireNonNull(position.getName(), "Position name cannot be null");

        if (position.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Position name cannot be empty");
        }
    }
}

