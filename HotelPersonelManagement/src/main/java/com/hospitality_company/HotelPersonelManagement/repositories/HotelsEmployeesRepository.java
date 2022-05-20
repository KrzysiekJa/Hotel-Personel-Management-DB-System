package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.HotelsEmployees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

@Repository
public class HotelsEmployeesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public HotelsEmployees addHotelsEmployees(HotelsEmployees hotelsEmployees) throws SQLException{
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call add_hotelsemployees(?,?)}");
        callableStatement.setInt("hotel_ID", (int) hotelsEmployees.getHotel_ID());
        callableStatement.setInt("employee_ID", (int) hotelsEmployees.getEmployee_ID());
        return (HotelsEmployees) callableStatement.executeQuery();
    }

    public HotelsEmployees deleteHotelsEmployees(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call delete_hotelsemployees(?)}");
        callableStatement.setInt("hotel_employee_ID", (int) id);
        return (HotelsEmployees) callableStatement.executeQuery();
    }
}
