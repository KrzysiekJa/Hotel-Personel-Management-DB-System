package com.HotelPersonelManagement.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Base repository class providing common database connection handling.
 * Implements best practices for resource management using try-with-resources.
 */
public abstract class BaseRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * Gets a database connection from the JdbcTemplate's DataSource.
     *
     * @return a database Connection
     * @throws SQLException if connection cannot be obtained
     */
    protected Connection getConnection() throws SQLException {
        return Objects.requireNonNull(
                jdbcTemplate.getDataSource(),
                "DataSource is not configured"
        ).getConnection();
    }
}

