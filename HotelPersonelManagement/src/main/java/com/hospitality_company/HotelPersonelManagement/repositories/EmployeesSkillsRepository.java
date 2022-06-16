package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.EmployeesSkills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        return employeesSkills;
    }

    public boolean deleteEmployeesSkills(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call delete_employeesskills(?)}");
        callableStatement.setInt("employee_skill_ID", (int) id);
        callableStatement.executeUpdate();
        return true;
    }

    public List<List> getEmployeeHotelSkills() throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_EmpHotSkill}");
        ResultSet resultSet = callableStatement.executeQuery();

        List<List> list = new ArrayList<>();
        while (resultSet.next()) {

            String nameE = resultSet.getString(1);
            String surname = resultSet.getString(2);
            String nameH = resultSet.getString(3);
            String nameS = resultSet.getString(4);

            list.add(Arrays.asList(nameE, surname, nameH, nameS));
        }
        return list;
    }
}
