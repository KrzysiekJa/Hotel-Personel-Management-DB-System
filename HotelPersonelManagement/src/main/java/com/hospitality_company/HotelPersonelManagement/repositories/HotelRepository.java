package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Repository
public class HotelRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Hotel> getAllHotels() throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_hotels}");
        ResultSet resultSet = callableStatement.executeQuery();

        List<Hotel> hotelsList = new ArrayList<>();
        while (resultSet.next()) {
            long hotel_ID = resultSet.getLong("hotel_ID");
            String name = resultSet.getString("name");
            String address = resultSet.getString("address");
            int telephone = resultSet.getInt("telephone");
            String email = resultSet.getString("email");
            String standard = resultSet.getString("standard");
            int rooms_number = resultSet.getInt("rooms_number");
            Date creation_date = resultSet.getDate("creation_date");

            Hotel hotel = new Hotel(hotel_ID, name, address, telephone, email,
                    standard, rooms_number, creation_date);
            hotelsList.add(hotel);
        }
        callableStatement.close();
        connection.close();

        return hotelsList;
    }

    public Hotel getById(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_hotel_by_ID(?)}");
        callableStatement.setInt("hotel_ID", (int) id);
        ResultSet resultSet = callableStatement.executeQuery();;
        resultSet.next();

        long hotel_ID = resultSet.getLong("hotel_ID");
        String name = resultSet.getString("name");
        String address = resultSet.getString("address");
        int telephone = resultSet.getInt("telephone");
        String email = resultSet.getString("email");
        String standard = resultSet.getString("standard");
        int rooms_number = resultSet.getInt("rooms_number");
        Date creation_date = resultSet.getDate("creation_date");
        //LocalDateTime last_changed = (LocalDateTime) resultSet.getObject("last_changed");

        Hotel hotel = new Hotel(hotel_ID, name, address, telephone, email,
                    standard, rooms_number, creation_date);

        return hotel;
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
        callableStatement.setDate("creation_date", hotel.getCreation_date());
        callableStatement.executeUpdate();
        callableStatement.close();
        connection.close();
        return hotel;
    }

    public boolean deleteHotel(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call delete_hotel(?)}");
        callableStatement.setInt("hotel_ID", (int) id);
        callableStatement.executeUpdate();
        callableStatement.close();
        connection.close();
        return true;
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
        callableStatement.setDate("creation_date", hotel.getCreation_date());
        callableStatement.executeUpdate();
        callableStatement.close();
        connection.close();
        return hotel;
    }
}
