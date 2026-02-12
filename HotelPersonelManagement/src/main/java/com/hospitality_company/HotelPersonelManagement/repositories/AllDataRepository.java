package com.hospitality_company.HotelPersonelManagement.repositories;

import com.hospitality_company.HotelPersonelManagement.models.AllData;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for AllData database operations.
 * Provides read-only access to aggregated data across multiple entities.
 * Uses try-with-resources for proper resource management.
 */
@Repository
public class AllDataRepository extends BaseRepository {

    /**
     * Retrieves all aggregated data from the database.
     *
     * @return List of all data combining employees, hotels, and shifts
     * @throws SQLException if database operation fails
     */
    public List<AllData> getAllData() throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call get_AllData}");
             ResultSet resultSet = callableStatement.executeQuery()) {

            List<AllData> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapResultSetToAllData(resultSet));
            }
            return list;
        }
    }

    /**
     * Maps a ResultSet row to an AllData object.
     *
     * @param resultSet the ResultSet containing aggregated data
     * @return AllData object
     * @throws SQLException if column retrieval fails
     */
    private AllData mapResultSetToAllData(ResultSet resultSet) throws SQLException {
        return new AllData(
            resultSet.getLong("work_plan_Employees_ID"),
            resultSet.getString("name"),  // employee name
            resultSet.getString("surname"),
            resultSet.getLong("employee_ID"),
            resultSet.getString("name"),  // hotel name - TODO: verify if this should be a different column
            resultSet.getLong("hotel_ID"),
            resultSet.getLong("shift_ID"),
            (LocalDateTime) resultSet.getObject("starting_date"),
            (LocalDateTime) resultSet.getObject("ending_date")
        );
    }
}

