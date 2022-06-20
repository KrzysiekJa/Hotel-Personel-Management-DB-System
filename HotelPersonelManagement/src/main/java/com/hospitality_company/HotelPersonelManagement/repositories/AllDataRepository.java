package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.AllData;
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
public class AllDataRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AllData> getAllData() throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_AllData}");
        ResultSet resultSet = callableStatement.executeQuery();

        List<AllData> list = new ArrayList<>();
        while (resultSet.next()) {

            long work_plan_Employees_ID = resultSet.getLong("work_plan_Employees_ID");
            String employee_name = resultSet.getString("name");
            String employee_surname = resultSet.getString("surname");
            long employee_ID = resultSet.getLong("employee_ID");
            String name = resultSet.getString(5);
            long hotel_ID = resultSet.getLong("hotel_ID");
            long shift_ID = resultSet.getLong("shift_ID");
            LocalDateTime starting_date = (LocalDateTime) resultSet.getObject("starting_date");
            LocalDateTime ending_date = (LocalDateTime) resultSet.getObject("ending_date");

            AllData allData = new AllData(work_plan_Employees_ID, employee_name,
                    employee_surname, employee_ID, name, hotel_ID, shift_ID, starting_date, ending_date);
            list.add(allData);
        }
        callableStatement.close();
        connection.close();

        return list;
    }
}
