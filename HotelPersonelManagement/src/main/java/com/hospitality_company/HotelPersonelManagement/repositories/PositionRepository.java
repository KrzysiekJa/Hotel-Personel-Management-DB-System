package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Repository
public class PositionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Position addPosition(Position position) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call add_position(?,?)}");
        callableStatement.setString("name", position.getName());
        callableStatement.setString("description", position.getDescription());
        callableStatement.executeUpdate();
        callableStatement.close();
        connection.close();
        return position;
    }

    public boolean deletePosition(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call delete_position(?)}");
        callableStatement.setInt("position_ID", (int) id);
        callableStatement.executeUpdate();
        callableStatement.close();
        connection.close();
        return true;
    }

    public List<Position> getAllPositions() throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_positions}");
        ResultSet resultSet = callableStatement.executeQuery();
        List<Position> positionList = new ArrayList<>();
        while (resultSet.next()){
             long position_ID = resultSet.getLong("position_ID");
             String name = resultSet.getString("name");
             String description = resultSet.getString("description");
             Position position = new Position(position_ID, name, description);
             positionList.add(position);
        }
        callableStatement.close();
        connection.close();

        return positionList;
    }

    public Position getById(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_position_by_id(?)}");
        callableStatement.setInt("position_ID", (int) id);
        return (Position) callableStatement.executeQuery();
    }

    public Position updatePosition(long id, Position position) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call update_position(?,?,?)}");
        callableStatement.setInt("position_ID", (int) id);
        callableStatement.setString("name", position.getName());
        callableStatement.setString("description", position.getDescription());
        callableStatement.executeUpdate();
        callableStatement.close();
        connection.close();
        return position;
    }
}
