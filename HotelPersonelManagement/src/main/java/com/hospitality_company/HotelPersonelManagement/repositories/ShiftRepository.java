package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Repository
public class ShiftRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Shift addShift (Shift shift) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call add_shift(?,?,?)}");
        callableStatement.setObject("starting_date", shift.getStarting_date());
        callableStatement.setObject("ending_date", shift.getEnding_date());
        callableStatement.setString("status", shift.getStatus());
        callableStatement.executeUpdate();
        return shift;
    }

    public boolean deleteShift(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call delete_shift(?)}");
        callableStatement.setInt("shift_ID", (int) id);
        callableStatement.executeUpdate();
        return true;
    }

    public List<Shift> getWorkPlan() throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_work_plan}");
        ResultSet resultSet = callableStatement.executeQuery();
        List<Shift> shiftList = new ArrayList<>();
        while (resultSet.next()){
             long shift_ID = resultSet.getLong("shift_ID");
             LocalDateTime starting_date = (LocalDateTime) resultSet.getObject("starting_date");
             LocalDateTime ending_date = (LocalDateTime) resultSet.getObject("ending_date");
             String status = resultSet.getString("status");
             Shift shift = new Shift(shift_ID, starting_date, ending_date, status);
             shiftList.add(shift);
        }
        return shiftList;
    }

    public Shift getById(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_shift_by_id(?)}");
        callableStatement.setInt("shift_ID", (int) id);
        return (Shift) callableStatement.executeQuery();
    }

    public Shift updateShift(long id, Shift shift) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call update_shift(?,?,?,?)}");
        callableStatement.setInt("shift_ID", (int) id);
        callableStatement.setObject("starting_date", shift.getStarting_date());
        callableStatement.setObject("ending_date", shift.getEnding_date());
        callableStatement.setString("status", shift.getStatus());
        callableStatement.executeUpdate();
        return shift;
    }
}
