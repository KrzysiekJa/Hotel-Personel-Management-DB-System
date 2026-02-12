package com.HotelPersonelManagement.repositories;

import com.HotelPersonelManagement.models.AllData;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
     * Assumes the stored procedure returns aliased columns matching the model:
     * work_plan_Employees_ID, employee_name, employee_surname, employee_ID,
     * hotel_name, hotel_ID, shift_ID, starting_date, ending_date
     */
    private AllData mapResultSetToAllData(ResultSet resultSet) throws SQLException {
        long workPlan_Employees_ID = resultSet.getLong("work_plan_Employees_ID");
        String employee_name = resultSet.getString("employee_name");
        String employee_surname = resultSet.getString("employee_surname");
        long employee_ID = resultSet.getLong("employee_ID");
        String hotel_name = resultSet.getString("hotel_name");
        long hotel_ID = resultSet.getLong("hotel_ID");
        long shift_ID = resultSet.getLong("shift_ID");

        Timestamp tsStart = resultSet.getTimestamp("starting_date");
        Timestamp tsEnd = resultSet.getTimestamp("ending_date");
        LocalDateTime starting_date = (tsStart != null) ? tsStart.toLocalDateTime() : null;
        LocalDateTime ending_date = (tsEnd != null) ? tsEnd.toLocalDateTime() : null;

        return new AllData(
            workPlan_Employees_ID,
            employee_name,
            employee_surname,
            employee_ID,
            hotel_name,
            hotel_ID,
            shift_ID,
            starting_date,
            ending_date
        );
    }
}
