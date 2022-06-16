package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.WorkPlanEmployees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Repository
public class WorkPlanEmployeesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public WorkPlanEmployees addWorkPlanEmployees(WorkPlanEmployees workPlanEmployees) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call add_workplanemployees(?,?)}");
        callableStatement.setInt("hotel_employee_ID", (int) workPlanEmployees.getHotel_employee_ID());
        callableStatement.setInt("shift_ID", (int) workPlanEmployees.getShift_ID());
        callableStatement.executeUpdate();
        callableStatement.close();
        connection.close();
        return workPlanEmployees;
    }

    public boolean deleteWorkPlanEmployees(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call delete_workplanemployees(?)}");
        callableStatement.setInt("work_plan_Employees_ID", (int) id);
        callableStatement.executeUpdate();
        callableStatement.close();
        connection.close();
        return true;
    }

    public List<List> getWorkPlanEmployees() throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_EmpHotShift}");
        ResultSet resultSet = callableStatement.executeQuery();

        List<List> list = new ArrayList<>();
        while (resultSet.next()) {

            String nameE = resultSet.getString(1);
            String surname = resultSet.getString(2);
            String nameH = resultSet.getString(3);
            LocalDateTime starting_date = (LocalDateTime) resultSet.getObject(4);
            LocalDateTime ending_date = (LocalDateTime) resultSet.getObject(5);
            String status = resultSet.getString(6);

            list.add(Arrays.asList(nameE, surname, nameH, starting_date, ending_date, status));
        }
        callableStatement.close();
        connection.close();

        return list;
    }
}
