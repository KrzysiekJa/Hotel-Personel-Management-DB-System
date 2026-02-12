package com.HotelPersonelManagement.repositories;

import com.HotelPersonelManagement.models.Skill;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Repository for Skill database operations.
 * Provides CRUD operations and retrieval methods for skills.
 * Uses try-with-resources for proper resource management.
 */
@Repository
public class SkillRepository extends BaseRepository {

    /**
     * Adds a new skill to the database.
     *
     * @param skill the skill to add
     * @return the added skill
     * @throws SQLException if database operation fails
     * @throws IllegalArgumentException if skill is invalid
     */
    public Skill addSkill(Skill skill) throws SQLException {
        validateSkill(skill);

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call add_skill(?,?)}")) {

            setSkillParameters(callableStatement, skill, false);
            callableStatement.executeUpdate();
            return skill;
        }
    }

    /**
     * Deletes a skill by ID.
     *
     * @param id the skill ID to delete
     * @return true if deletion was successful
     * @throws SQLException if database operation fails
     */
    public boolean deleteSkill(long id) throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call delete_skill(?)}")) {

            callableStatement.setLong(1, id);
            callableStatement.executeUpdate();
            return true;
        }
    }

    /**
     * Retrieves a skill by ID.
     *
     * @param id the skill ID
     * @return Skill object with the given ID
     * @throws SQLException if database operation fails or skill not found
     */
    public Skill getById(long id) throws SQLException {

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call get_skill_by_ID(?)}")) {

            callableStatement.setLong(1, id);
            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToSkill(resultSet);
                }
                throw new SQLException("Skill not found with ID: " + id);
            }
        }
    }

    /**
     * Retrieves all skills from the database.
     *
     * @return List of all skills
     * @throws SQLException if database operation fails
     */
    public List<Skill> getAllSkills() throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call get_skills}");
             ResultSet resultSet = callableStatement.executeQuery()) {

            List<Skill> skillList = new ArrayList<>();
            while (resultSet.next()) {
                skillList.add(mapResultSetToSkill(resultSet));
            }
            return skillList;
        }
    }

    /**
     * Updates an existing skill.
     *
     * @param id the skill ID to update
     * @param skill the updated skill data
     * @return the updated skill
     * @throws SQLException if database operation fails
     * @throws IllegalArgumentException if skill is invalid
     */
    public Skill updateSkill(long id, Skill skill) throws SQLException {
        validateSkill(skill);

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call update_skill(?,?,?)}")) {

            callableStatement.setLong(1, id);
            setSkillParameters(callableStatement, skill, true);
            callableStatement.executeUpdate();
            return skill;
        }
    }

    /**
     * Maps a ResultSet row to a Skill object.
     *
     * @param resultSet the ResultSet containing skill data
     * @return Skill object
     * @throws SQLException if column retrieval fails
     */
    private Skill mapResultSetToSkill(ResultSet resultSet) throws SQLException {
        return new Skill(
            resultSet.getLong("skill_ID"),
            resultSet.getString("name"),
            resultSet.getString("description")
        );
    }

    /**
     * Sets parameters for skill stored procedure.
     *
     * @param stmt the CallableStatement to set parameters for
     * @param skill the skill data
     * @param isUpdate whether this is an update operation
     * @throws SQLException if parameter setting fails
     */
    private void setSkillParameters(CallableStatement stmt, Skill skill, boolean isUpdate) throws SQLException {
        int paramIndex = 1;

        if (isUpdate) {
            // Skip skill_ID for add operation, it's already set in updateSkill
            paramIndex = 2;
        }

        stmt.setString(paramIndex++, skill.getName());
        stmt.setString(paramIndex, skill.getDescription());
    }

    /**
     * Validates skill data.
     *
     * @param skill the skill to validate
     * @throws IllegalArgumentException if skill data is invalid
     */
    private void validateSkill(Skill skill) {
        Objects.requireNonNull(skill, "Skill cannot be null");
        Objects.requireNonNull(skill.getName(), "Skill name cannot be null");

        if (skill.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Skill name cannot be empty");
        }
    }
}

