package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.Hotel;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Repository for Hotel database operations.
 * Provides CRUD operations and retrieval methods for hotels.
 * Uses try-with-resources for proper resource management.
 */
@Repository
public class HotelRepository extends BaseRepository {

    /**
     * Retrieves all hotels from the database.
     *
     * @return List of all hotels
     * @throws SQLException if database operation fails
     */
    public List<Hotel> getAllHotels() throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call get_hotels}");
             ResultSet resultSet = callableStatement.executeQuery()) {

            List<Hotel> hotelsList = new ArrayList<>();
            while (resultSet.next()) {
                hotelsList.add(mapResultSetToHotel(resultSet));
            }
            return hotelsList;
        }
    }

    /**
     * Retrieves a hotel by ID.
     *
     * @param id the hotel ID
     * @return Hotel object with the given ID
     * @throws SQLException if database operation fails or hotel not found
     */
    public Hotel getById(long id) throws SQLException {

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call get_hotel_by_ID(?)}")) {

            callableStatement.setLong(1, id);
            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToHotel(resultSet);
                }
                throw new SQLException("Hotel not found with ID: " + id);
            }
        }
    }

    /**
     * Adds a new hotel to the database.
     *
     * @param hotel the hotel to add
     * @return the added hotel
     * @throws SQLException if database operation fails
     * @throws IllegalArgumentException if hotel is invalid
     */
    public Hotel addHotel(Hotel hotel) throws SQLException {
        validateHotel(hotel);

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call add_hotel(?,?,?,?,?,?,?)}")) {

            setHotelParameters(callableStatement, hotel, false);
            callableStatement.executeUpdate();
            return hotel;
        }
    }

    /**
     * Deletes a hotel by ID.
     *
     * @param id the hotel ID to delete
     * @return true if deletion was successful
     * @throws SQLException if database operation fails
     */
    public boolean deleteHotel(long id) throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call delete_hotel(?)}")) {

            callableStatement.setLong(1, id);
            callableStatement.executeUpdate();
            return true;
        }
    }

    /**
     * Updates an existing hotel.
     *
     * @param id the hotel ID to update
     * @param hotel the updated hotel data
     * @return the updated hotel
     * @throws SQLException if database operation fails
     * @throws IllegalArgumentException if hotel is invalid
     */
    public Hotel updateHotel(long id, Hotel hotel) throws SQLException {
        validateHotel(hotel);

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call update_hotel(?,?,?,?,?,?,?,?)}")) {

            callableStatement.setLong(1, id);
            setHotelParameters(callableStatement, hotel, true);
            callableStatement.executeUpdate();
            return hotel;
        }
    }

    /**
     * Maps a ResultSet row to a Hotel object.
     *
     * @param resultSet the ResultSet containing hotel data
     * @return Hotel object
     * @throws SQLException if column retrieval fails
     */
    private Hotel mapResultSetToHotel(ResultSet resultSet) throws SQLException {
        return new Hotel(
            resultSet.getLong("hotel_ID"),
            resultSet.getString("name"),
            resultSet.getString("address"),
            resultSet.getInt("telephone"),
            resultSet.getString("email"),
            resultSet.getString("standard"),
            resultSet.getInt("rooms_number"),
            resultSet.getDate("creation_date")
        );
    }

    /**
     * Sets parameters for hotel stored procedure.
     *
     * @param stmt the CallableStatement to set parameters for
     * @param hotel the hotel data
     * @param isUpdate whether this is an update operation
     * @throws SQLException if parameter setting fails
     */
    private void setHotelParameters(CallableStatement stmt, Hotel hotel, boolean isUpdate) throws SQLException {
        int paramIndex = 1;

        if (isUpdate) {
            // Skip hotel_ID for add operation, it's already set in updateHotel
            paramIndex = 2;
        }

        stmt.setString(paramIndex++, hotel.getName());
        stmt.setString(paramIndex++, hotel.getAddress());
        stmt.setInt(paramIndex++, hotel.getTelephone());
        stmt.setString(paramIndex++, hotel.getEmail());
        stmt.setString(paramIndex++, hotel.getStandard());
        stmt.setInt(paramIndex++, hotel.getRooms_number());
        stmt.setDate(paramIndex, hotel.getCreation_date());
    }

    /**
     * Validates hotel data.
     *
     * @param hotel the hotel to validate
     * @throws IllegalArgumentException if hotel data is invalid
     */
    private void validateHotel(Hotel hotel) {
        Objects.requireNonNull(hotel, "Hotel cannot be null");
        Objects.requireNonNull(hotel.getName(), "Hotel name cannot be null");
        Objects.requireNonNull(hotel.getAddress(), "Hotel address cannot be null");
        Objects.requireNonNull(hotel.getEmail(), "Hotel email cannot be null");

        if (hotel.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Hotel name cannot be empty");
        }
        if (hotel.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Hotel address cannot be empty");
        }
        if (hotel.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Hotel email cannot be empty");
        }

        if (hotel.getTelephone() <= 0) {
            throw new IllegalArgumentException("Hotel telephone must be a positive number");
        }

        if (hotel.getRooms_number() <= 0) {
            throw new IllegalArgumentException("Hotel rooms number must be positive");
        }
    }
}

