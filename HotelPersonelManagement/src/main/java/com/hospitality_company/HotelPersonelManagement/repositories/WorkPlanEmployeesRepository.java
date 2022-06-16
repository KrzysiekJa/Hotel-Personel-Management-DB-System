package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.WorkPlanEmployees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
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
}
