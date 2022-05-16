package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


@Repository
public class PositionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Position addPosition(Position position) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call add_hotel(?,?)}");
        callableStatement.setString("name", position.getName());
        callableStatement.setString("description", position.getDescription());
        return (Position) callableStatement.executeQuery();
    }

    public Position deletePosition(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call delete_hotel(?)}");
        callableStatement.setInt("position_ID", (int) id);
        return (Position) callableStatement.executeQuery();
    }

    public List<Position> getAllPositions() throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_positions}");
        return (List<Position>) callableStatement.executeQuery();
    }

    public Position getById(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_position_by_id(?)}");
        callableStatement.setInt("position_ID", (int) id);
        return (Position) callableStatement.executeQuery();
    }
}
