package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.EmployeesSkills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

@Repository
public class EmployeesSkillsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public EmployeesSkills addEmployeesSkills(EmployeesSkills employeesSkills) throws SQLException{
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call add_employeesskills(?,?)}");
        callableStatement.setInt("employee_ID", (int) employeesSkills.getEmployee_skill_ID());
        callableStatement.setInt("skill_ID", (int) employeesSkills.getEmployee_skill_ID());
        callableStatement.executeUpdate();
        callableStatement.close();
        connection.close();
        return employeesSkills;
    }

    public boolean deleteEmployeesSkills(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call delete_employeesskills(?)}");
        callableStatement.setInt("employee_skill_ID", (int) id);
        callableStatement.executeUpdate();
        callableStatement.close();
        connection.close();
        return true;
    }
}
