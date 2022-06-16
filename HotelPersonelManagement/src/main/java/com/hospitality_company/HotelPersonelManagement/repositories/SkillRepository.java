package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.Skill;
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
public class SkillRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Skill addSkill(Skill skill) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call add_skill(?,?)}");
        callableStatement.setString("name", skill.getName());
        callableStatement.setString("description", skill.getDescription());
        callableStatement.executeUpdate();
        callableStatement.close();
        connection.close();
        return skill;
    }

    public boolean deleteSkill(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call delete_skill(?)}");
        callableStatement.setInt("skill_ID", (int) id);
        callableStatement.executeUpdate();
        callableStatement.close();
        connection.close();
        return true;
    }

    public Skill getById(long id) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_skill_by_ID(?)}");
        callableStatement.setInt("skill_ID", (int) id);
        return (Skill) callableStatement.executeQuery();
    }

    public List<Skill> getAllSkills() throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call get_skills}");
        ResultSet resultSet = callableStatement.executeQuery();
        List<Skill> skillList = new ArrayList<>();
        while (resultSet.next()){
            long skill_ID = resultSet.getLong("skill_ID");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            Skill skill = new Skill(skill_ID, name, description);
            skillList.add(skill);
        }
        callableStatement.close();
        connection.close();

        return skillList;
    }

    public Skill updateSkill(long id, Skill skill) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call update_skill(?,?,?)}");
        callableStatement.setInt("skill_ID", (int) id);
        callableStatement.setString("name", skill.getName());
        callableStatement.setString("description", skill.getDescription());
        callableStatement.executeUpdate();
        callableStatement.close();
        connection.close();
        return skill;
    }
}
