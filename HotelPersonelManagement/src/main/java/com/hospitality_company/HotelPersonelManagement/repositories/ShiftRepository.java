package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


@Repository
public class ShiftRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Shift addShift (Shift shift) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call add_hotel(?,?,?)}");
        callableStatement.setObject("starting_date", shift.getStarting_date());
        callableStatement.setObject("ending_date", shift.getEnding_date());
        callableStatement.setString("status", shift.getStatus());
        return (Shift) callableStatement.executeQuery();
    }

    public Shift deleteShift(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call delete_shift(?)}");
        callableStatement.setInt("shift_ID", (int) id);
        return (Shift) callableStatement.executeQuery();
    }

    public List<Shift> getWorkPlan() throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_work_plan}");
        return (List<Shift>) callableStatement.executeQuery();
    }

    public Shift getById(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_shift_by_id(?)}");
        callableStatement.setInt("shift_ID", (int) id);
        return (Shift) callableStatement.executeQuery();
    }
}
