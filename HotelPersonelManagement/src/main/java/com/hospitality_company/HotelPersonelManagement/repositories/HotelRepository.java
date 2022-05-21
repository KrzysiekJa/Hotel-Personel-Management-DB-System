package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.Hotel;
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
public class HotelRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Hotel> getAllHotels() throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_employees}");
        return (List<Hotel>) callableStatement.executeQuery();
    }

    public Hotel getById(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_hotel_by_id(?)}");
        callableStatement.setInt("hotel_id", (int) id);
        return (Hotel) callableStatement.executeQuery();
    }

    public Hotel addHotel(Hotel hotel) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call add_hotel(?,?,?,?,?,?,?)}");
        callableStatement.setString("name", hotel.getName());
        callableStatement.setString("address", hotel.getAddress());
        callableStatement.setInt("telephone", hotel.getTelephone());
        callableStatement.setString("email", hotel.getEmail());
        callableStatement.setString("standard", hotel.getStandard());
        callableStatement.setInt("rooms_number", hotel.getRooms_number());
        callableStatement.setDate("creation_date", (Date) hotel.getCreation_date());
        return (Hotel) callableStatement.executeQuery();
    }

    public Hotel deleteHotel(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call delete_hotel(?)}");
        callableStatement.setInt("hotel_ID", (int) id);
        return (Hotel) callableStatement.executeQuery();
    }

    public Hotel updateHotel(long id, Hotel hotel) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call update_hotel(?,?,?,?,?,?,?,?)}");
        callableStatement.setInt("hotel_ID", (int) id);
        callableStatement.setString("name", hotel.getName());
        callableStatement.setString("address", hotel.getAddress());
        callableStatement.setInt("telephone", hotel.getTelephone());
        callableStatement.setString("email", hotel.getEmail());
        callableStatement.setString("standard", hotel.getStandard());
        callableStatement.setInt("rooms_number", hotel.getRooms_number());
        callableStatement.setDate("creation_date", (Date) hotel.getCreation_date());
        return (Hotel) callableStatement.executeQuery();
    }
}
