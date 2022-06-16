package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.HotelsEmployees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        callableStatement.executeUpdate();
        callableStatement.close();
        connection.close();
        return hotelsEmployees;
    }

    public boolean deleteHotelsEmployees(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call delete_hotelsemployees(?)}");
        callableStatement.setInt("hotel_employee_ID", (int) id);
        callableStatement.executeUpdate();
        callableStatement.close();
        connection.close();
        return true;
    }

    public List<List> getEmployeeHotelPosition() throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_EmpHotPos}");
        ResultSet resultSet = callableStatement.executeQuery();

        List<List> list = new ArrayList<>();
        while (resultSet.next()) {

            String nameE = resultSet.getString(1);
            String surname = resultSet.getString(2);
            String nameH = resultSet.getString(3);
            String nameP = resultSet.getString(4);

            list.add(Arrays.asList(nameE, surname, nameH, nameP));
        }
        callableStatement.close();
        connection.close();

        return list;
    }
}
